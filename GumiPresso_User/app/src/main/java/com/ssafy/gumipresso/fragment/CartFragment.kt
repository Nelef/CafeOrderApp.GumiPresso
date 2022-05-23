package com.ssafy.gumipresso.fragment

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cartViewModel.totalCartPrice.observe(viewLifecycleOwner) {
            initAdapter()
        }
        cartViewModel.isTakeOut.observe(viewLifecycleOwner) {
            binding.viewmodel = cartViewModel
            userTable = if (cartViewModel.isTakeOut.value as Boolean) "TakeOut" else "Here"
        }
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gpsViewModel.location?.observe(viewLifecycleOwner){

            binding.gpsVM = gpsViewModel
        }

        userTable = "T/O"
        binding.viewmodel = cartViewModel

        binding.apply {
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
        if(gpsViewModel.location?.value != null){
            order.arrival_time = gpsViewModel.arrivalTime.value
            order.remain_time = gpsViewModel.remainTime.value
        }
        cartViewModel.orderCart(order)
        userViewModel.getUserInfo()
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