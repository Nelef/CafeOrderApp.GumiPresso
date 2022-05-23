package com.ssafy.gumipresso.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.databinding.DialogOrderCheckBinding
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

class OrderCheckDialog : DialogFragment() {
    private lateinit var binding: DialogOrderCheckBinding
    private val cartViewModel: CartViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    lateinit var onClickConfirm: OnClickConfirm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogOrderCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding.apply {
            cartVM = cartViewModel
            userVM = userViewModel
            btnPay.setOnClickListener {
                cartViewModel.setUsePayState(true)
            }
            btnStore.setOnClickListener {
                cartViewModel.setUsePayState(false)
            }
            btnDialogOrder.setOnClickListener {
                onClickConfirm.onClick(cartViewModel.usePay.value!!)
                dismiss()
            }
            btnDialogCancel.setOnClickListener {
                dismiss()
            }
        }

    }

    private fun initViewModel(){
        cartViewModel.usePay.observe(viewLifecycleOwner){
            binding.cartVM = cartViewModel
        }
    }

    interface OnClickConfirm{
        fun onClick(usePay: Boolean)
    }
}

