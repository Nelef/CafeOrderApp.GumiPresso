package com.ssafy.gumipresso_admin.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ssafy.gumipresso_admin.activity.LoginActivity
import com.ssafy.gumipresso_admin.common.CONST
import com.ssafy.gumipresso_admin.databinding.FragmentJoinBinding
import com.ssafy.gumipresso_admin.model.dto.User
import com.ssafy.gumipresso_admin.viewmodel.UserViewModel

class JoinFragment : Fragment() {
    private lateinit var binding: FragmentJoinBinding
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnJoin.setOnClickListener { join() }
            etId.addTextChangedListener(textChangeListener)
            lifecycleOwner = this@JoinFragment
            joinViewModel = userViewModel
        }
    }

    private fun join(){
        binding.apply {
            val id = etId.text.toString().trim()
            val pass = etPw.text.toString().trim()
            val name = etNickname.text.toString().trim()
            if(id.isEmpty() || id.isEmpty() || id.isEmpty()){
                Toast.makeText(context, "빈 칸을 모두 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                userViewModel.user.observe(viewLifecycleOwner) {
                    if(it != null){
                        val user = userViewModel.user.value as User
                        Toast.makeText(context, "${user.name}님 회원가입 되었습니다.", Toast.LENGTH_SHORT).show()
                        (activity as LoginActivity).movePage(CONST.FRAG_LOGIN)
                    }
                }
                userViewModel.join(User(id, pass, name))
            }
        }
    }

    private val textChangeListener = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
        override fun afterTextChanged(s: Editable?) {
            userViewModel.getUsedId(binding.etId.text.toString())
        }
    }

}