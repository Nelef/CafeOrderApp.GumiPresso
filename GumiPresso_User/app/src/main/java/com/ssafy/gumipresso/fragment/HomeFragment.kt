package com.ssafy.gumipresso.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.BannerAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentHomeBinding
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.util.PushMessageUtil
import com.ssafy.gumipresso.util.SettingsUtil
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel
import com.ssafy.gumipresso.viewmodel.SettingViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

private const val TAG ="HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val orderViewModel: RecentOrderViewModel by viewModels()
    private val settingViewModel: SettingViewModel by viewModels()

    // 배너
    private var bannerList = mutableListOf(R.drawable.banner1, R.drawable.banner2)

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

        binding.btnFcmPush.setOnClickListener {
            userViewModel.sendFCMPushMessage(PushMessageUtil().getFcmToken(), "gd", "doiododo")
        }

        // 배너
        binding.viewPager2.adapter = BannerAdapter(bannerList) // 어댑터 생성
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로
        // 배너 끝

    }

    private fun initViewModel(){
        userViewModel.user.observe(viewLifecycleOwner){
            if(userViewModel.user.value != null){
                binding.homeUserViewModel = userViewModel
                orderViewModel.getOrderList(userViewModel.user.value!!.id)


            }
        }
    }
}