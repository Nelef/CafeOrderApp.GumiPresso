package com.ssafy.gumipresso.fragment

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
import com.ssafy.gumipresso.adapter.NoticeAdapter
import com.ssafy.gumipresso.adapter.RecentOrderAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentHomeBinding
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.util.FCMTokenUtil
import com.ssafy.gumipresso.util.NoticeMessageUtil
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.NoticeViewModel
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

private const val TAG ="HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val orderViewModel: RecentOrderViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var orderList: List<RecentOrder>
    private lateinit var recentOrderAdapter: RecentOrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getUserFromPreferences()

        binding.ivNotification.setOnClickListener {
            (activity as MainActivity).movePage(CONST.FRAG_NOTI, null)
        }

        binding.btnFcmPush.setOnClickListener {
            userViewModel.sendFCMPushMessage(FCMTokenUtil().getFcmToken(), "gd", "doiododo")
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
                binding.homeRecentOrderViewModel = orderViewModel
                orderList = orderViewModel.recentOrderList.value as List<RecentOrder>
                initOrderAdapter()
            }
        }
    }

    private fun initOrderAdapter(){
        recentOrderAdapter = RecentOrderAdapter(orderList, "home")
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
                    (activity as MainActivity).movePage(CONST.FRAG_CART_FROM_HOME, null)
                }
            }
            onOrderItemClick = object : RecentOrderAdapter.OnOrderItemClick {
                override fun onClick(view: View, position: Int) {
                    (activity as MainActivity).movePage(CONST.FRAG_RECENT_ORDER_DETAIL_FROM_HOME, orderList[position].oId.toString())
                }
            }
        }
        binding.apply {
            recyclerRecentOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerRecentOrder.adapter = recentOrderAdapter
        }
    }

    private fun getUserFromPreferences(){
        userViewModel.getUserInfo()
    }

}