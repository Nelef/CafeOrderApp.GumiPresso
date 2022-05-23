package com.ssafy.gumipresso.fragment

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.databinding.FragmentPayBinding
import com.ssafy.gumipresso.viewmodel.UserViewModel

private const val TAG = "PayFragment"

class PayFragment : Fragment() {
    private val userViewModel: UserViewModel by activityViewModels()
    private lateinit var binding: FragmentPayBinding

    private var status: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userViewModel.getUserInfo()

        //생체인증
        status = BiometricManager.from(requireContext()).canAuthenticate()

        binding.btnAddPay.setOnClickListener(View.OnClickListener {
            createBiometricPrompt().authenticate(createPromptInfo())
        })
    }

    private fun qrCreate() {
        val qrImage = generateQRCode(userViewModel.user.value?.id)
        binding.ivCard.setImageResource(R.drawable.pay_background_qr)
        binding.ivQr.setImageBitmap(qrImage)
        binding.tvMoney.visibility = View.GONE
    }

    fun generateQRCode(contents: String?): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val qrCodeWriter = QRCodeWriter()
            bitmap = toBitmap(qrCodeWriter.encode(contents, BarcodeFormat.QR_CODE, 400, 400))
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }

    private fun toBitmap(matrix: BitMatrix): Bitmap? {
        val height = matrix.height
        val width = matrix.width
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        return bmp
    }

    private fun createPromptInfo(): BiometricPrompt.PromptInfo {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("생체정보로 결제")
            .setSubtitle("구매상품: 아메리카노 외 2잔")
            .setDescription("결제 진행을 위해 생체 정보를 입력시켜주세요.")
            .setConfirmationRequired(false)
            .setNegativeButtonText("취소")
            .build()
        return promptInfo
    }

    private fun createBiometricPrompt(): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(requireContext())

        val callback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.d(TAG, "$errorCode :: $errString")

                if (errorCode != 13 && errorCode != 10) { // 10, 13 은 취소시 발생하므로 제외
                    Toast.makeText(
                        requireContext(),
                        "생체인증이 불가능한 단말기입니다. 단계를 건너뜁니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    qrCreate()
                }
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.d(TAG, "인증 실패")
            }

            @RequiresApi(Build.VERSION_CODES.M)
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.d(TAG, "인증 성공")
                //인증 완료 시 작업
                qrCreate()
            }
        }

        //The API requires the client/Activity context for displaying the prompt view
        val biometricPrompt = BiometricPrompt(this, executor, callback)
        return biometricPrompt
    }
}