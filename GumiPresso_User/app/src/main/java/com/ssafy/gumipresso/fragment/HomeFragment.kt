package com.ssafy.gumipresso.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.BannerAdapter
import com.ssafy.gumipresso.adapter.CartItemAdapter
import com.ssafy.gumipresso.adapter.TableAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentHomeBinding
import com.ssafy.gumipresso.model.dto.Banner
import com.ssafy.gumipresso.model.dto.Table
import com.ssafy.gumipresso.util.PushMessageUtil
import com.ssafy.gumipresso.viewmodel.*

private const val TAG ="HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val orderViewModel: RecentOrderViewModel by viewModels()
    private val noticeViewModel: NoticeViewModel by viewModels()
    private val tableViewModel: TableViewModel by activityViewModels()
    private val bannerViewModel: BannerViewModel by activityViewModels()

    private lateinit var tableAdapter: TableAdapter
    private var tableList = listOf<Table>()
    private lateinit var bannerAdapter: BannerAdapter
    private var bannerList = listOf<Banner>()

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
        userViewModel.getUserInfo()

        binding.ivNotification.setOnClickListener {
            (activity as MainActivity).movePage(CONST.FRAG_NOTI, null)
        }
    }

    fun initViewModel(){
        noticeViewModel.getNoticeList()
        noticeViewModel.notice.observe(viewLifecycleOwner){
            binding.noticeVM = noticeViewModel
        }
        userViewModel.user.observe(viewLifecycleOwner){
            if(userViewModel.user.value != null){
                binding.homeUserViewModel = userViewModel
                orderViewModel.getOrderList(userViewModel.user.value!!.id)
            }
        }
        tableViewModel.getOrdertable()
        tableViewModel.tableList.observe(viewLifecycleOwner){
            if(it != null){
                tableList = tableViewModel.tableList.value as List<Table>
                initTableAdapter()
            }
        }
        tableViewModel.flagTableChange.observe(viewLifecycleOwner){
            Log.d(TAG, "initViewModel: flageTableChange")
            if(tableList.isNotEmpty()){
                initTableAdapter()
            }
        }
        bannerViewModel.getBanner()
        bannerViewModel.bannerList.observe(viewLifecycleOwner){
            if(it != null){
                bannerList = bannerViewModel.bannerList.value as List<Banner>
                initBannerAdapter()
            }
        }
    }

    private fun initTableAdapter(){
        tableAdapter = TableAdapter(tableList)
        binding.recyclerTableList.apply {
            layoutManager = GridLayoutManager (context, 5)
            adapter = tableAdapter
        }
    }

    private fun initBannerAdapter(){
        Log.d(TAG, "initBannerAdapter: $bannerList")
        bannerAdapter = BannerAdapter(bannerList)
        binding.viewPager2.adapter = bannerAdapter
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        bannerAdapter.onBannerItemClick = object : BannerAdapter.OnBannerItemClick {
            override fun onClick(view: View, position: Int) {
                var address = bannerViewModel.clickBannerItem(position)
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
                    startActivity(intent)
                }catch (e:Exception){
                    Toast.makeText(requireContext(), "BannerLinkError", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}