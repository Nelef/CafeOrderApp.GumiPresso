package com.ssafy.gumipresso_admin.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.google.android.gms.auth.CookieUtil
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.adapter.OrderAdapter
import com.ssafy.gumipresso_admin.adapter.layoutmanager.LinearLayoutManagerWithScroller
import com.ssafy.gumipresso_admin.databinding.FragmentHomeBinding
import com.ssafy.gumipresso_admin.model.dto.RecentOrder
import com.ssafy.gumipresso_admin.viewmodel.OrderViewModel
import com.ssafy.gumipresso_admin.viewmodel.UserViewModel
import kotlin.math.log

private const val TAG ="HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val orderViewModel: OrderViewModel by viewModels()

    private lateinit var orderList: MutableList<RecentOrder>
    private lateinit var orderAdapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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
    }

    private fun initViewModel(){
        userViewModel.user.observe(viewLifecycleOwner){
            binding.userVM = userViewModel
        }
        orderViewModel.orderList.observe(viewLifecycleOwner){
            binding.orderVM = orderViewModel
            initAdapter()
        }
        userViewModel.getAdminUser()
        orderViewModel.getOrderListByCompleted()
    }

    private fun initAdapter(){
        orderAdapter = OrderAdapter(orderViewModel.orderList.value as MutableList<RecentOrder>)
        binding.recyclerRecentOrder.apply {
            layoutManager = LinearLayoutManagerWithScroller(context, LinearLayoutManager.VERTICAL, false)
            adapter = orderAdapter
        }
    }
}
