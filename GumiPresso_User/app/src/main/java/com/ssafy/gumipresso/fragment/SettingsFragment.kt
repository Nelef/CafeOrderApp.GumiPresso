package com.ssafy.gumipresso.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.databinding.FragmentSettingsBinding
import com.ssafy.gumipresso.viewmodel.UserViewModel

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val userViewModel: UserViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        userViewModel.getPushState()
        userViewModel.getAutoLogin()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        userViewModel.getAutoLogin()
        userViewModel.getPushState()

        binding.apply {
            btnAutologin.setOnClickListener{
                userViewModel.setAutoLogin()
            }
            btnPushAll.setOnClickListener {
                userViewModel.setPushAll()
            }
            btnPushPersonal.setOnClickListener {
                userVM!!.setPushPersonal()
            }
            ivBack.setOnClickListener {
                (activity as MainActivity).navController.popBackStack()
            }
        }
    }

    private fun initViewModel(){
        userViewModel.autoLoginState.observe(viewLifecycleOwner){
            binding.userVM = userViewModel
        }
        userViewModel.pushStateAll.observe(viewLifecycleOwner){
            binding.userVM = userViewModel
        }
        userViewModel.pushStatePersonal.observe(viewLifecycleOwner){
            binding.userVM = userViewModel
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).visibilityBottomNavBar(false)
    }

}
