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
import com.ssafy.gumipresso.adapter.RecentOrderAdapter
import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentGradeBinding
import com.ssafy.gumipresso.databinding.FragmentMyPageBinding
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.GradeViewModel
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

class GradeFragment : Fragment() {
    private lateinit var binding: FragmentGradeBinding
    private val gradeViewModel: GradeViewModel by viewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initViewModel()
        user = userViewModel.user.value as User
        gradeViewModel.getUserGrade(user.stamps)
        binding = FragmentGradeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).visibilityBottomNavBar(false)
            (activity as MainActivity).navController.popBackStack()
        }
    }

    private fun initViewModel() {
        gradeViewModel.grade?.observe(viewLifecycleOwner) {
            if (gradeViewModel.grade!!.value != null) {
                binding.gradeVM = gradeViewModel
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).visibilityBottomNavBar(false)
    }
}