package com.ssafy.gumipresso_admin.fragment.manage

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso_admin.activity.MainActivity
import com.ssafy.gumipresso_admin.databinding.FragmentPayManageBinding
import com.ssafy.gumipresso_admin.model.dto.User
import com.ssafy.gumipresso_admin.viewmodel.PayViewModel


private const val TAG = "PayManageFragment"

class PayManageFragment : Fragment() {
    private lateinit var binding: FragmentPayManageBinding
    private val payViewModel: PayViewModel by viewModels()

    private var user: User? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayManageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        checkPermission()
        binding.apply {

            btn1000.setOnClickListener { payViewModel.addMoney(1000) }
            btn5000.setOnClickListener { payViewModel.addMoney(5000) }
            btn10000.setOnClickListener { payViewModel.addMoney(10000) }
            ivClearMoney.setOnClickListener { payViewModel.clearMoney() }
            btnMoneySave.setOnClickListener {
                if(user != null){
                    user!!.money += payViewModel.money.value!!
                    payViewModel.updatePayMoney(user!!)
                    Toast.makeText(context, "충전이 완료 되었습니다.", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).navController.popBackStack()
                }else{
                    Toast.makeText(context, "QR코드를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun initViewModel(){
        payViewModel.user.observe(viewLifecycleOwner){
            user = it
            binding.payVM = payViewModel
        }
        payViewModel.money.observe(viewLifecycleOwner){
            binding.payVM = payViewModel
        }
    }

    private fun checkPermission() {
        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
                IntentIntegrator.forSupportFragment(this@PayManageFragment).initiateScan()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(
                    context,
                    "카메라에 접근 권한을 허가해주세요",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
            .setPermissions(Manifest.permission.CAMERA)
            .check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "취소 되었습니다.", Toast.LENGTH_SHORT).show()
            } else {
                 payViewModel.getUserByQRCode(result.contents)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}