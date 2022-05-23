package com.ssafy.gumipresso.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.databinding.FragmentSettingsBinding
import com.ssafy.gumipresso.viewmodel.SettingViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel


class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val settingViewModel : SettingViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        settingViewModel.getPushState()
        settingViewModel.getAutoLogin()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        settingViewModel.getAutoLogin()
        settingViewModel.getPushState()
        settingViewModel.getShakeToPayState()
        binding.apply {
            btnAutologin.setOnClickListener{
                settingViewModel.setAutoLogin()
                Toast.makeText(context, "자동 로그인 설정이 변경 되었습니다", Toast.LENGTH_SHORT).show()
            }
            btnPushAll.setOnClickListener {
                settingViewModel.setPushAll()
                Toast.makeText(context, "전체 알림 설정이 변경 되었습니다", Toast.LENGTH_SHORT).show()
            }
            btnPushPersonal.setOnClickListener {
                settingVM!!.setPushPersonal()
                Toast.makeText(context, "알림 설정이 변경 되었습니다", Toast.LENGTH_SHORT).show()
            }
            ivBack.setOnClickListener {
                (activity as MainActivity).navController.popBackStack()
            }
            btnShake.setOnClickListener {
                settingViewModel.setShakeToPayState()
                Toast.makeText(context, "Pay 설정이 변경 되었습니다", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViewModel(){
        settingViewModel.autoLoginState.observe(viewLifecycleOwner){
            binding.settingVM = settingViewModel
        }
        settingViewModel.pushStateAll.observe(viewLifecycleOwner){
            binding.settingVM = settingViewModel
        }
        settingViewModel.pushStatePersonal.observe(viewLifecycleOwner){
            binding.settingVM = settingViewModel
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).visibilityBottomNavBar(false)
    }

}
