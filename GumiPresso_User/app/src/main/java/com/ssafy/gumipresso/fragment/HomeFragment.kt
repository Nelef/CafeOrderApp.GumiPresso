package com.ssafy.gumipresso.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
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

private const val TAG = "HomeFragment"

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
    private var bannerPosition = 0
    private var myHandler = MyHandler()

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

    fun initViewModel() {
        noticeViewModel.getNoticeList()
        noticeViewModel.notice.observe(viewLifecycleOwner) {
            binding.noticeVM = noticeViewModel
        }
        userViewModel.user.observe(viewLifecycleOwner) {
            if (userViewModel.user.value != null) {
                binding.homeUserViewModel = userViewModel
                orderViewModel.getOrderList(userViewModel.user.value!!.id)
            }
        }
        tableViewModel.getOrdertable()
        tableViewModel.tableList.observe(viewLifecycleOwner) {
            if (it != null) {
                tableList = tableViewModel.tableList.value as List<Table>
                initTableAdapter()
            }
        }
        tableViewModel.flagTableChange.observe(viewLifecycleOwner) {
            if (tableList.isNotEmpty()) {
                initTableAdapter()
            }
        }
        bannerViewModel.getBanner()
        bannerViewModel.bannerList.observe(viewLifecycleOwner) {
            if (it != null) {
                bannerList = bannerViewModel.bannerList.value as List<Banner>
                initBannerAdapter()
            }
        }
    }

    private fun initTableAdapter() {
        tableAdapter = TableAdapter(tableList)
        binding.recyclerTableList.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = tableAdapter
        }
    }

    private fun initBannerAdapter() {
        bannerAdapter = BannerAdapter(bannerList)
        binding.viewPager2.adapter = bannerAdapter
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        bannerAdapter.onBannerItemClick = object : BannerAdapter.OnBannerItemClick {
            override fun onClick(view: View, position: Int) {
                var address = bannerViewModel.clickBannerItem(position)
                try {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "BannerLinkError", Toast.LENGTH_SHORT).show()
                }
            }
        }
        // indicator(. . .) 설정
        binding.dotsIndicator.setViewPager2(binding.viewPager2)
        // AutoScroll 설정
        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerPosition = position
            }
        })

    }

    // 자동 스크롤
    private fun autoScrollStart(intervalTime: Long) {
        myHandler.removeMessages(0)
        myHandler.sendEmptyMessageDelayed(0, intervalTime)
    }

    private fun autoScrollStop() {
        myHandler.removeMessages(0) // 핸들러를 중지시킴
    }

    private inner class MyHandler : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (msg.what == 0) {
                // 끝에 도달시
                if (bannerPosition == bannerList.size - 1) {
                    bannerPosition = 0
                } else {
                    bannerPosition++
                }
                binding.viewPager2.setCurrentItem(bannerPosition, true)
                autoScrollStart(5000) // 5초
            }
        }
    }

    // 다른 페이지 갔다가 돌아오면 다시 스크롤 시작
    override fun onResume() {
        super.onResume()
        autoScrollStart(5000) // 5초
    }

    // 다른 페이지로 떠나있는 동안 스크롤이 동작할 필요는 없음. 정지
    override fun onPause() {
        super.onPause()
        autoScrollStop()
    }
}