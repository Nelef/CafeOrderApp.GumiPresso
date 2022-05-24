package com.ssafy.gumipresso.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.RecentOrderAdapter
import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentMyPageBinding
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.GradeViewModel
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

private const val TAG ="MyPageFragment"
class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private val gradeViewModel: GradeViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val orderViewModel: RecentOrderViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var orderList: List<RecentOrder>
    private lateinit var recentOrderAdapter: RecentOrderAdapter
    private lateinit var user: User
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        user = userViewModel.user.value as User
        gradeViewModel.getUserGrade(user.stamps)
        orderViewModel.getOrderList(user.id)
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }    

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@")

        binding.apply {
            ivSettings.setOnClickListener { 
                mainActivity.movePage(CONST.FRAG_SETTING, null)
            }
            ivLogout.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("알림")
                builder.setMessage("로그아웃 하시겠습니까?")
                builder.setNegativeButton("취소") { dialog, _ ->
                    dialog.cancel()
                }
                builder.setPositiveButton("확인") { dialog, _ ->
                    ApplicationClass.userPrefs.edit().clear().commit()
                    mainActivity.movePage(CONST.LOGOUT, null)
                }.show()
            }
            membershipLayout.setOnClickListener {
                mainActivity.movePage(CONST.FRAG_GRADE_FROM_MYPAGE, null)
            }
        }
    }

    private fun initViewModel(){
        userViewModel.user.observe(viewLifecycleOwner){
            if(userViewModel.user.value != null){
                binding.homeUserViewModel = userViewModel
                orderViewModel.getOrderList(userViewModel.user.value!!.id)
            }
        }
        orderViewModel.recentOrderList.observe(viewLifecycleOwner){
            if(orderViewModel.recentOrderList.value != null){
                binding.recentOrderVM = orderViewModel
                orderList = orderViewModel.recentOrderList.value as List<RecentOrder>
                initOrderAdapter()
            }
        }
        gradeViewModel.grade.observe(viewLifecycleOwner){
            if(gradeViewModel.grade.value != null) {
                binding.gradeVM = gradeViewModel
            }
        }
    }

    private fun initOrderAdapter(){
        recentOrderAdapter = RecentOrderAdapter(orderList)
        recentOrderAdapter.apply {
            onCartIconClick = object : RecentOrderAdapter.OnCartIconClick {
                override fun onClick(view: View, position: Int) {
                    val recentOrder = (orderViewModel.recentOrderList.value as List<RecentOrder>)[position]
                    val recentOrderDetailList = recentOrder.recentOrderDetail
                    cartViewModel.clearCart()

                    for(i in recentOrderDetailList.indices){
                        val cart = Cart(recentOrderDetailList[i].productId, recentOrderDetailList[i].img, recentOrderDetailList[i].name, recentOrderDetailList[i].quantity, recentOrderDetailList[i].price, recentOrderDetailList[i].quantity * recentOrderDetailList[i].price, recentOrderDetailList[i].type)
                        cartViewModel.addCart(cart)
                    }
                    mainActivity.movePage(CONST.FRAG_CART_FROM_MYPAGE, null)
                }
            }
            onOrderItemClick = object : RecentOrderAdapter.OnOrderItemClick {
                override fun onClick(view: View, position: Int) {
                    mainActivity.movePage(CONST.FRAG_RECENT_ORDER_DETAIL_FROM_MYPAGE, orderList[position].oId.toString())
                }
            }
        }
        binding.apply {
            recyclerRecentOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerRecentOrder.adapter = recentOrderAdapter
        }
    }
}