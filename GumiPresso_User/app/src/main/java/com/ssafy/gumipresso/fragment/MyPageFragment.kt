package com.ssafy.gumipresso.fragment

import android.os.Bundle
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
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.viewmodel.GradeViewModel
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

private const val TAG ="MyPageFragment"
class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding
    private val gradeViewModel: GradeViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val orderViewModel: RecentOrderViewModel by viewModels()

    private lateinit var orderList: List<RecentOrder>
    private lateinit var recentOrderAdapter: RecentOrderAdapter
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.ivLogout.setOnClickListener {
            ApplicationClass.userPrefs.edit().clear().commit()
            (activity as MainActivity).movePage(CONST.LOGOUT, null)
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
        gradeViewModel.grade?.observe(viewLifecycleOwner){
            if(gradeViewModel.grade!!.value != null) {
                binding.gradeVM = gradeViewModel
            }
        }
    }

    private fun initOrderAdapter(){
        recentOrderAdapter = RecentOrderAdapter(orderList, "page")
        recentOrderAdapter.apply {
            onOrderItemClick = object : RecentOrderAdapter.OnOrderItemClick {
                override fun onClick(view: View, position: Int) {
                    (activity as MainActivity).movePage(CONST.FRAG_RECENT_ORDER_DETAIL_FROM_MYPAGE, orderList[position].oId.toString())
                }
            }
        }
        binding.apply {
            recyclerRecentOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerRecentOrder.adapter = recentOrderAdapter
        }

    }
}