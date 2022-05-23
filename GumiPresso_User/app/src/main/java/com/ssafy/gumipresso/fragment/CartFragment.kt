package com.ssafy.gumipresso.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.CartItemAdapter
import com.ssafy.gumipresso.databinding.FragmentCartBinding
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.model.dto.Order
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.util.PushMessageUtil
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.GPSViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

private const val TAG = "CartFragment"

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    private val cartViewModel: CartViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val gpsViewModel: GPSViewModel by activityViewModels()

    private lateinit var cartAdapter: CartItemAdapter
    private lateinit var cartList: List<Cart>
    private lateinit var userTable: String
    private lateinit var orderType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userViewModel.getUserInfo()
        cartViewModel.totalCartPrice.observe(viewLifecycleOwner) {
            initAdapter()
        }
        cartViewModel.isTakeOut.observe(viewLifecycleOwner) {
            binding.viewmodel = cartViewModel
            userTable = if (cartViewModel.isTakeOut.value as Boolean) "TakeOut" else "Here"
        }
        cartViewModel.usePay.observe(viewLifecycleOwner) {
            binding.viewmodel = cartViewModel
            orderType = if (cartViewModel.usePay.value as Boolean) "P" else "N"
        }
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gpsViewModel.location?.observe(viewLifecycleOwner) {

            binding.gpsVM = gpsViewModel
        }

        userTable = "T/O"
        binding.viewmodel = cartViewModel

        binding.apply {
            // 처음 금액 나오도록
            tvPayMoney.text = userViewModel.user.value?.money.toString() + " 원"
            tvResultTotalprice.text = (userViewModel.user.value?.money?.minus(cartViewModel.totalCartPrice.value!!)).toString() + " 원"

            btnOrder.setOnClickListener {
                if (cartViewModel.totalCartPrice.value == 0) {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("알림")
                    builder.setMessage("커피를 먼저 담아주세요.")
                    builder.setPositiveButton("확인") { dialog, _ ->
                        dialog.cancel()
                    }.show()
                } else {
                    var tag = (activity as MainActivity).TagMethod()
                    if (userTable == "TakeOut") {
                        makeOrder()
                    } else if (tag == null) {
                        requestNFC()
                    } else {
                        userTable = "Table $tag"
                        makeOrder()
                    }
                }
            }
            btnTout.setOnClickListener {
                cartViewModel.setHereOrTogo(true)
            }
            btnTstore.setOnClickListener {
                cartViewModel.setHereOrTogo(false)
            }
            btnPrePay.setOnClickListener {
                cartViewModel.setUsePayState(true)
                tvPayMoney.text = userViewModel.user.value?.money.toString() + " 원"
                tvResultTotalprice.text = (userViewModel.user.value?.money?.minus(cartViewModel.totalCartPrice.value!!)).toString() + " 원"
            }
            btnPostPay.setOnClickListener {
                cartViewModel.setUsePayState(false)
                tvPayMoney.text = "동결"
                tvResultTotalprice.text = "동결"
            }
            ivBack.setOnClickListener {
                (activity as MainActivity).visibilityBottomNavBar(false)
                (activity as MainActivity).navController.popBackStack()
            }
        }

        initAdapter()
    }

    private fun initAdapter() {
        cartList = cartViewModel.cartList.value as List<Cart>
        cartAdapter = CartItemAdapter(cartList)
        cartAdapter.onDeleteButtonClick = object : CartItemAdapter.OnDeleteButtonClick {
            override fun onClick(view: View, position: Int) {
                cartViewModel.removeCartItem(position)
                binding.viewmodel = cartViewModel
            }
        }
        binding.recyclerProductList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = cartAdapter
        }
    }

    private fun makeOrder() {
        var order = Order((userViewModel.user.value as User).id, userTable)

        order.completed = if (cartViewModel.usePay.value == true) "P" else "N"
        if(order.completed == "P") {
            userViewModel.setUserMoney(cartViewModel.totalCartPrice.value!!)// 페이 결제시 유저 머니 변경
        }
        userViewModel.updateMoney()
        if (gpsViewModel.arrivalTime.value != null) {
            order.arrivalTime = gpsViewModel.arrivalTime.value
            order.remainTime = gpsViewModel.remainTime.value
        }
        cartViewModel.orderCart(order)
        cartViewModel.clearCart()
        userViewModel.sendFCMPushMessage(
            PushMessageUtil().getFcmToken(),
            "GumiPresso",
            "주문이 완료 되었습니다. - ${userTable}"
        )
        Toast.makeText(context, "주문이 완료 되었습니다. $userTable", Toast.LENGTH_SHORT).show()
        (activity as MainActivity).visibilityBottomNavBar(false)
        (activity as MainActivity).navController.popBackStack()
    }

    private fun requestNFC() {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("알림")
        builder.setMessage("Table NFC를 먼저 찍어주세요.")
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.cancel()
        }.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).visibilityBottomNavBar(false)
    }
}