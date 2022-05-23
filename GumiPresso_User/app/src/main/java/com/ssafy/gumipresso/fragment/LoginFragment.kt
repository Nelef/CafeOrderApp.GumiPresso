package com.ssafy.gumipresso.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.LoginActivity
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.common.ApplicationClass
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentLoginBinding
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.util.SettingsUtil
import com.ssafy.gumipresso.viewmodel.UserViewModel

private const val TAG ="LoginFragment"
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val userViewModel : UserViewModel by activityViewModels()
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = ApplicationClass.userPrefs.getString("User","")
        if(!user.equals("") && SettingsUtil().getAutoLoginState()){
            userViewModel.getUserInfo()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener { login() }
            btnJoin.setOnClickListener { join() }
            loginViewModel = userViewModel
            lifecycleOwner = this@LoginFragment
        }
        initKakaoLogin()
        initNaverLogin()
        initViewModel()
        initGoogleLogin()
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

    private fun join(){
        (activity as LoginActivity).movePage(CONST.FRAG_JOIN)
    }

    private  fun initNaverLogin(){
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                Log.d(TAG, "onSuccess: ${NaverIdLoginSDK.getAccessToken()}")
                userViewModel.sendNaverToken(NaverIdLoginSDK.getAccessToken()!!)
            }
            override fun onFailure(httpStatus: Int, message: String) {
                val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                Toast.makeText(context,"errorCode:$errorCode, errorDesc:$errorDescription",Toast.LENGTH_SHORT).show()
            }
            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        binding.btnNaverLogin.setOnClickListener {
            NaverIdLoginSDK.authenticate(requireContext(), oauthLoginCallback)
        }

    }

    private fun initKakaoLogin(){

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(context, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(context, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(context, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(context, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(context, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(context, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(context, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(context, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: ")
                    }
                    else -> { // Unknown
                        Toast.makeText(context, "기타 에러", Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "kakaoLogin: $error")
                    }
                }
            }
            else if (token != null) {                
                Log.d(TAG, "kakaoLogin: 로그인에 성공하였습니다 ${token}")
                userViewModel.sendKakaoToken(token.accessToken!!)
            }
        }
        binding.btnKakaoLogin.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(requireContext())){
                UserApiClient.instance.loginWithKakaoTalk(requireContext(), callback = callback)
            }else{
                UserApiClient.instance.loginWithKakaoAccount(requireContext(), callback = callback)
            }
        }
    }

    private var mAuth: FirebaseAuth? = null
    var mGoogleSignInClient: GoogleSignInClient? = null

    private fun initGoogleLogin(){
        binding.btnGoogleLogin.setOnClickListener {
            initAuth()
        }
    }
    // 인증 초기화
    private fun initAuth() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            // default_web_client_id 값은 build 타임에 values.xml 파일에 자동 생성
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail() // 인증 방식: gmail
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        mAuth = FirebaseAuth.getInstance()

        // Google에서 제공되는 signInIntent를 이용해서 인증 시도
        val signInIntent = mGoogleSignInClient!!.signInIntent

        //콜백함수 부르며 launch
        requestActivity.launch(signInIntent)
    }

    // 구글 인증 결과 획득 후 동작 처리
    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        Log.d(TAG, "firebaseAuthWithGoogle: Activity.RESULT_OK): ${AppCompatActivity.RESULT_OK}, activityResult.resultCode:${activityResult.resultCode}")
        if (activityResult.resultCode == Activity.RESULT_OK) {

            // 인증 결과 획득
            val task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)
            try {
                val account = task.getResult(ApiException::class.java)
                Log.d(TAG, "firebaseAuthWithGoogle: account: ${account.displayName}  /// ${account.email}")
                firebaseAuthWithGoogle(account!!.idToken)
            }
            catch (e: ApiException) {
                Log.w(TAG, "google sign in failed: " ,e)
            }
        }
    }

    // 구글 인증 결과 성공 여부에 따른 처리
    private fun firebaseAuthWithGoogle(idToken: String?) {
        Log.d(TAG, "firebaseAuthWithGoogle: idToken: ${idToken}")
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = mAuth!!.currentUser
                    val newUser = User(user!!.email!!, "", user.displayName!!, 0, 0)
                    userViewModel.join(newUser)
                    userViewModel.login(newUser)
                }

            }
    }


}