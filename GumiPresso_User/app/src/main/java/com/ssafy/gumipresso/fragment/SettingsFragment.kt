package com.ssafy.gumipresso.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.databinding.FragmentSettingsBinding
import com.ssafy.gumipresso.viewmodel.SettingViewModel


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

        binding.apply {
            btnAutologin.setOnClickListener{
                settingViewModel.setAutoLogin()
            }
            btnPushAll.setOnClickListener {
                settingViewModel.setPushAll()
            }
            btnPushPersonal.setOnClickListener {
                settingVM!!.setPushPersonal()
            }
            ivBack.setOnClickListener {
                (activity as MainActivity).navController.popBackStack()
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
