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
import com.ssafy.gumipresso.adapter.RecentOrderDetailAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentRecentOrderDetailBinding
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.model.dto.RecentOrderDetail
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.RecentOrderDetailViewModel
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel

private const val TAG ="RecentOrderDetailFrag"
class RecentOrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentRecentOrderDetailBinding
    private val orderDetailViewModel: RecentOrderDetailViewModel by viewModels()
    private val orderViewModel: RecentOrderViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()

    private lateinit var oid: String
    private lateinit var recentOrderDetailAdapter: RecentOrderDetailAdapter
    private lateinit var orderDetailList: List<RecentOrderDetail>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).visibilityBottomNavBar(true)

        initViewModel()
        orderDetailViewModel.getRecentOrderDetail(arguments?.getString("order_id","").toString())

        binding.ivBack.setOnClickListener {
            (activity as MainActivity).visibilityBottomNavBar(false)
            (activity as MainActivity).navController.popBackStack()
        }
    }

    private fun initViewModel(){
        orderDetailViewModel.recentOrder.observe(viewLifecycleOwner){
            if(orderDetailViewModel.recentOrder.value != null){
                orderDetailViewModel.getTotalPriceOrderDetail()
                orderDetailViewModel.setOrderTimeToString()
                orderDetailList = (orderDetailViewModel.recentOrder.value as RecentOrder).recentOrderDetail
                binding.orderDetailViewModel = orderDetailViewModel
                binding.btnReorder.setOnClickListener {
                    for(item in orderDetailList){
                        val cart = Cart(item.productId, item.img, item.name, item.quantity, item.price, item.quantity * item.price, item.type)
                        Log.d(TAG, "initViewModel: $cart")
                        cartViewModel.addCart(cart)
                    }
                    (activity as MainActivity).movePage(CONST.FRAG_CART_FROM_RECENT_ORDER_DETAIL, null)
                }
                initOrderDetailAdapter()
            }
        }
    }

    private fun initOrderDetailAdapter(){
        recentOrderDetailAdapter = RecentOrderDetailAdapter(orderDetailList)
        binding.recyclerProductList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = recentOrderDetailAdapter
        }
    }

    override fun onResume() {
        (activity as MainActivity).visibilityBottomNavBar(true)
        super.onResume()
    }

    override fun onDestroy() {
        (activity as MainActivity).visibilityBottomNavBar(false)
        super.onDestroy()
    }
}