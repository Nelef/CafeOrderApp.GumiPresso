package com.ssafy.gumipresso_admin.fragment.manage

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import com.ssafy.gumipresso_admin.databinding.FragmentPushMessageBinding
import com.ssafy.gumipresso_admin.model.Retrofit
import com.ssafy.gumipresso_admin.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class PushMessageFragment : Fragment() {
    private lateinit var binding: FragmentPushMessageBinding
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPushMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSend.setOnClickListener {
                val builder = AlertDialog.Builder(requireContext())
                builder.apply {
                    setTitle("푸시 메시지")
                    setMessage("정말로 보내시겠습니까?\n모두에게 전송됩니다.")
                    setPositiveButton("전송"){ dialog, _ ->
                        userViewModel.sendFCMPushMessage("GumiPresso", etMassage.text.toString())
                        Toast.makeText(context, "전송 되었습니다", Toast.LENGTH_SHORT).show()
                    }
                    setNegativeButton("취소", null)
                }.show()
            }
            btnClear.setOnClickListener {
                etMassage.setText("")
            }
        }
    }



}