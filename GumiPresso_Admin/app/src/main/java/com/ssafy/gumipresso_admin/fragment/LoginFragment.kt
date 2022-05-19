package com.ssafy.gumipresso_admin.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.ssafy.gumipresso_admin.activity.LoginActivity
import com.ssafy.gumipresso_admin.common.ApplicationClass
import com.ssafy.gumipresso_admin.common.CONST
import com.ssafy.gumipresso_admin.databinding.FragmentLoginBinding
import com.ssafy.gumipresso_admin.model.dto.User
import com.ssafy.gumipresso_admin.viewmodel.UserViewModel
import kotlin.math.log

private const val TAG ="LoginFragment"
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val userViewModel : UserViewModel by activityViewModels()
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding.apply {
            btnLogin.setOnClickListener {
                login()
            }
            btnJoin.setOnClickListener {
                (activity as LoginActivity).movePage(CONST.FRAG_JOIN)
            }
        }
    }

    private fun initViewModel(){
        userViewModel.user.observe(viewLifecycleOwner){
            user = userViewModel.user.value as User
            Toast.makeText(context, "${user.name}님 반갑습니다.", Toast.LENGTH_SHORT).show()
            ApplicationClass.userPrefs.edit().putString("User", user.id).commit()
            Log.d(TAG, "initViewModel: ${user.id}")
            (activity as LoginActivity).movePage(CONST.ACTIVITY_MAIN)
        }
    }

    private fun login(){
        userViewModel.logdinSuccess.observe(viewLifecycleOwner) {
            if(it == false){
                Toast.makeText(context, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
        binding.apply {
            val id = etId.text.toString().trim()
            val pass = etPw.text.toString().trim()
            if(id.isEmpty() || pass.isEmpty()){
                Toast.makeText(context, "아이디와 비밀번호를 확인해 주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                userViewModel.login(User(id, pass))
            }
        }
    }

}