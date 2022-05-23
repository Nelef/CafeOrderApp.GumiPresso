package com.ssafy.gumipresso_admin.fragment.manage

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.zxing.integration.android.IntentIntegrator
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.journeyapps.barcodescanner.CaptureActivity
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.databinding.FragmentPayManageBinding
import com.ssafy.gumipresso_admin.databinding.FragmentPushMessageBinding
import com.ssafy.gumipresso_admin.viewmodel.PayViewModel
import org.json.JSONException

private const val TAG = "PayManageFragment"

class PayManageFragment : Fragment() {
    private lateinit var binding: FragmentPayManageBinding
    private val payViewModel: PayViewModel by viewModels()
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
        checkPermission()
    }

    private fun checkPermission() {
        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
                cameraTask()
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


    private fun cameraTask() {
        var qrScanner = IntentIntegrator.forSupportFragment(this).initiateScan()
//        qrScanner.setPrompt("QR코드를 인증해주세요.") // 원하는 문구 기입
//        qrScanner.setCameraId(0)
//        qrScanner.setOrientationLocked(false) // 세로,가로 모드를 고정 시켜주는 역할
//        qrScanner.setBeepEnabled(true) // QR코드 스캔시 소리 나게 하려면 true 아니면 false로 지정
//        qrScanner.captureActivity = CaptureActivity::class.java
//        qrScanner.initiateScan() //QR코드 스캔의 결과 값은 onActivityResult 함수로 전달
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(context, "결과를 찾을 수 없습니다", Toast.LENGTH_SHORT).show()

            } else {
                try {
                    Log.d(TAG, "onActivityResult: ${result.contents}")
                    Toast.makeText(context, result.contents.toString(), Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    Log.d(TAG, "onActivityResult: ${e.message}")
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }
}