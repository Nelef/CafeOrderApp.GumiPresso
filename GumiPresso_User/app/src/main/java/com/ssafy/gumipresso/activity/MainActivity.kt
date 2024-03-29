package com.ssafy.gumipresso.activity

import android.Manifest
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.os.RemoteException
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.ActivityMainBinding
import com.ssafy.gumipresso.fragment.OrderFragment
import com.ssafy.gumipresso.fragment.PayFragment
import com.ssafy.gumipresso.fragment.SettingsFragment
import com.ssafy.gumipresso.model.dto.Table
import com.ssafy.gumipresso.util.PushMessageUtil
import com.ssafy.gumipresso.util.SettingsUtil
import com.ssafy.gumipresso.viewmodel.SettingViewModel
import com.ssafy.gumipresso.viewmodel.TableViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.altbeacon.beacon.*
import kotlin.concurrent.thread
import kotlin.math.sqrt


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), BeaconConsumer, SensorEventListener {
    private lateinit var binding: ActivityMainBinding
    lateinit var navHostFragment: NavHostFragment
    lateinit var navController: NavController
    private val settingViewModel: SettingViewModel by viewModels()
    private val tableViewModel: TableViewModel by viewModels()
    private lateinit var tableList: List<Table>
    var payFragment: PayFragment? = null

    // 비콘 변수
    private lateinit var beaconManager: BeaconManager
    private val BEACON_UUID = "fda50693-a4e2-4fb1-afcf-c6eb07647825"
    private val BEACON_MAJOR = "10004"
    private val BEACON_MINOR = "54480"
    private var STORE_INFO_POPUP = false
    private val region = Region(
        "estimote",
        Identifier.parse(BEACON_UUID),
        Identifier.parse(BEACON_MAJOR),
        Identifier.parse(BEACON_MINOR)
    )
    private var bluetoothAdapter: BluetoothAdapter? = null

    // 태그 변수
    private var nfcAdapter: NfcAdapter? = null
    private lateinit var pIntent: PendingIntent
    private lateinit var filters: Array<IntentFilter>
    private var tag: String? = null

    // 기기 흔들기
    private lateinit var sensorManager: SensorManager
    private var accel: Float = 0.0f //초기
    private var accelCurrent: Float = 0.0f //이동하는 치수
    private var accelLast: Float = 0.0f

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "onCreate: ")
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        getFirebaseToken()

//        // 비콘
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        beaconManager = BeaconManager.getInstanceForApplication(this)
        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))

        checkPermission()
        thread {
            startScan()
        }
//        // 비콘 끝

        // 태그
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "이 기기는 NFC를 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
        } else {
            NFC_Tag()
        }

        // 테이블 정보 받기
        tableViewModel.getOrdertable()
        tableViewModel.tableList.observe(this) {
            if (it != null) {
                tableList = tableViewModel.tableList.value as List<Table>
            }
        }

        //센서 매니저를 설정한다.
        this.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        accel = 10f
        accelCurrent = SensorManager.GRAVITY_EARTH
        accelLast = SensorManager.GRAVITY_EARTH
    }

    fun visibilityBottomNavBar(hide: Boolean) {
        if (hide) {
            binding.bottomNavigationView.visibility = View.GONE
        } else {
            binding.bottomNavigationView.visibility = View.VISIBLE
        }
    }

    fun movePage(page: CONST, param: String?) {
        when (page) {
            CONST.FRAG_ORDER_DETAIL -> {
                navController.navigate(
                    R.id.action_orderFragment_to_orderDetailFragment,
                    bundleOf("product_id" to param)
                )
            }
            CONST.FRAG_CART_FROM_ORDER -> {
                visibilityBottomNavBar(true)
                navController.navigate(R.id.action_orderFragment_to_cartFragment)
            }
            CONST.FRAG_RECENT_ORDER_DETAIL_FROM_HOME -> {
                navController.navigate(
                    R.id.action_homeFragment_to_recentOrderDetailFragment,
                    bundleOf("order_id" to param)
                )
            }
            CONST.FRAG_RECENT_ORDER_DETAIL_FROM_MYPAGE -> {
                navController.navigate(
                    R.id.action_myPageFragment_to_recentOrderDetailFragment,
                    bundleOf("order_id" to param)
                )
            }
            CONST.FRAG_MAPS -> {
                navController.navigate(R.id.action_orderFragment_to_mapsFragment)
            }
            CONST.FRAG_CART_FROM_HOME -> {
                visibilityBottomNavBar(true)
                navController.navigate(R.id.action_homeFragment_to_cartFragment)
            }
            CONST.FRAG_CART_FROM_MYPAGE -> {
                visibilityBottomNavBar(true)
                navController.navigate(R.id.action_myPageFragment_to_cartFragment)
            }
            CONST.FRAG_CART_FROM_RECENT_ORDER_DETAIL -> {
                navController.navigate(R.id.action_recentOrderDetailFragment_to_cartFragment)
            }
            CONST.FRAG_NOTI -> {
                navController.navigate(R.id.action_homeFragment_to_notiFragment)
            }
            CONST.FRAG_GRADE_FROM_MYPAGE -> {
                visibilityBottomNavBar(true)
                navController.navigate(R.id.action_myPageFragment_to_gradeFragment)
            }
            CONST.FRAG_REVIEW_WRITE -> {
                navController.navigate(
                    R.id.action_orderDetailFragment_to_reviewWriteFragment,
                    bundleOf("product_id" to param)
                )
            }
            CONST.LOGOUT -> {
                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            CONST.FRAG_SETTING -> {
                visibilityBottomNavBar(true)
                navController.navigate(R.id.action_myPageFragment_to_settingsFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            if (!it.isSuccessful) {
                Log.d(TAG, "onCreate: FCM 토큰 얻기 실패", it.exception)
                return@OnCompleteListener
            }
            if (SettingsUtil().getFirstRunCheck()) {
                Toast.makeText(this, "앱을 처음 실행 하셨습니다.", Toast.LENGTH_SHORT).show()
                PushMessageUtil().setFcmToken(it.result)
                SettingsUtil().setFirstRunCheck(false)
                settingViewModel.insertFCMToken()
                val builder = AlertDialog.Builder(this)
                builder.setTitle("앱 처음 실행")
                builder.setMessage("알림과 자동로그인이 활성화 되어 있습니다. 설정을 하시겠습니까?")
                builder.setNegativeButton("취소") { dialog, _ ->
                    dialog.cancel()
                }
                builder.setPositiveButton("확인") { dialog, _ ->
                    navHostFragment.childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_main, SettingsFragment()).addToBackStack(null)
                        .commit()
                }.show()
            } else {
                settingViewModel.updateFCMToken()
            }

        })
        createNotiChannel("ssafy_id", "ssafy")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotiChannel(channelId: String, channelName: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, channelName, importance)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // 비콘
    override fun onBeaconServiceConnect() {
        beaconManager.addMonitorNotifier(object : MonitorNotifier {
            override fun didEnterRegion(region: Region?) {
                try {
                    Log.d(TAG, "비콘을 발견하였습니다.")
                    beaconManager.startRangingBeaconsInRegion(region!!)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }

            override fun didExitRegion(region: Region?) {
                try {
                    Log.d(TAG, "비콘을 찾을 수 없습니다.")
                    beaconManager.stopRangingBeaconsInRegion(region!!)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }

            override fun didDetermineStateForRegion(i: Int, region: Region?) {}
        })

        beaconManager.addRangeNotifier { beacons, region ->
            for (beacon in beacons) {

                if (!STORE_INFO_POPUP) {
                    CoroutineScope(Dispatchers.Main).launch {
                        showStoreEventDialog()
                    }
                }

                Log.d(
                    TAG,
                    "distance: " + beacon.distance + " id:" + beacon.id1 + "/" + beacon.id2 + "/" + beacon.id3
                )
            }
        }

        try {
            beaconManager.startMonitoringBeaconsInRegion(region)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    // 다이얼로그 생성
    private fun showStoreEventDialog() {
        STORE_INFO_POPUP = true

        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_store_info, null)

        builder.setView(dialogView).setPositiveButton("확인") { dialog, _ ->
            dialog.cancel()
        }.show()
    }

    // 권한 체크
    private fun checkPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    // Beacon Scan 시작
    private fun startScan() {
        // 블루투스 Enable 확인
        if (!isEnableBLEService()) {
            requestEnableBLE()
            Log.d(TAG, "startScan: 블루투스가 켜지지 않았습니다.")
            return
        }

        // 위치 정보 권한 허용 및 GPS Enable 여부 확인
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                100
            )
        }
        Log.d(TAG, "startScan: beacon Scan start")

        // Beacon Service bind
        beaconManager.bind(this)
    }

    // 블루투스 켰는지 확인
    private fun isEnableBLEService(): Boolean {
        if (!bluetoothAdapter!!.isEnabled) {
            return false
        }
        return true
    }

    private val requestBLEActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // 사용자의 블루투스 사용이 가능한지 확인
        if (isEnableBLEService())
            startScan()
    }

    // 블루투스 ON/OFF 여부 확인 및 키도록 하는 함수
    private fun requestEnableBLE() {
        val callBLEEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestBLEActivity.launch(callBLEEnableIntent)
        Log.d(TAG, "requestEnableBLE: ")
    }
    // 비콘 끝

    // 태그 시작
    private fun NFC_Tag() {
        //태그 정보가 포함된 인텐트를 처리할 액티비티 지정
        val intent = Intent(this, MainActivity::class.java)
        //SingleTop 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        pIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //Text Record 수신하기 위한 필터
        val ndef_filter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        ndef_filter.addDataType("text/plain")

        filters = arrayOf(ndef_filter)
    }

    // 태그 포그라운드 모드 활성화
    override fun onResume() {
        super.onResume()
        nfcAdapter?.enableForegroundDispatch(this, pIntent, filters, null)
        // 가속도 센서 On
        sensorManager.registerListener(
            this, sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    // 포그라운드 모드 비활성화
    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
        // 가속도 센서 Off
        sensorManager.unregisterListener(this)
    }


    fun processIntent(intent: Intent) {
        // 1. 인텐트에서 NdefMessage 배열 데이터를 가져온다.
        val rawMsg = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)

        // 2. Data를 전환
        if (rawMsg != null) {
            val msgArr = arrayOfNulls<NdefMessage>(rawMsg.size)

            for (i in rawMsg.indices) {
                msgArr[i] = rawMsg[i] as NdefMessage?
            }

            // 3. NdefMessage에서 NdefRecode -> payload
            val recInfo = msgArr[0]!!.records[0]
            val payload = recInfo.payload

            //Record type check : text, uri
            val data = recInfo.type
            val recType = String(data)

            if (recType == "T") {
                // 사용중인 테이블이라면
                val newTag = String(payload, 3, payload.size - 3)

                if (!newTag.isDigitsOnly() || newTag!!.toInt() > 10 || newTag!!.toInt() < 1) {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("알림")
                    builder.setMessage("잘못된 Tag입니다. \n 1 ~ 10 숫자 Tag만 입력 가능 합니다. \n\n" + "인식된 Tag: ${newTag}")
                    builder.setPositiveButton("확인") { dialog, _ -> dialog.cancel() }.show()
                } else if (tableList[newTag!!.toInt() - 1].state) {
                    tag = newTag
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("태그 발견")
                    builder.setMessage("$newTag 번 자리를 사용중입니다. 체크아웃할까요?")
                    builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
                    builder.setPositiveButton("확인") { dialog, _ ->
                        Log.d(TAG, "processIntent: ${newTag!!.toInt()}")
                        tableViewModel.setOrdertable(newTag!!.toInt())
                        Toast.makeText(this, "이용해주셔서 감사합니다.", Toast.LENGTH_SHORT).show()
                        tag = null
                    }.show()
                } // 사용가능한 테이블이라면
                else {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("태그 발견")
                    builder.setMessage("$newTag 번 테이블 태그를 발견했습니다. 체크인할까요?")
                    builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
                    builder.setPositiveButton("확인") { dialog, _ ->
                        Log.d(TAG, "processIntent: ${newTag!!.toInt()}")
                        tableViewModel.setOrdertable(newTag!!.toInt())
                        Log.d(TAG, "processIntent: ${tableList}")
                        Toast.makeText(this, "환영합니다! 이제부터 테이블 주문을 할 수 있습니다.", Toast.LENGTH_SHORT)
                            .show()
                        tag = newTag
                    }.show()
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e("INFO", "onNewIntent called...")
        val action = intent!!.action
        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED) || action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            processIntent(intent)
        }
    }

    fun TagMethod(): String? {
        return tag
    }
    // 태그 끝

    // 기기 흔들기
    //흔들었을때 센서 감지
    override fun onSensorChanged(event: SensorEvent?) {
        if (SettingsUtil().getShakeToPayState()) {
            // x,y,z 축의 값을 받아온다
            val x = event!!.values[0]
            val y = event.values[1]
            val z = event.values[2]
            // 중력 가속도값으로 나눈 값으로 만든다
            val gX = x / SensorManager.GRAVITY_EARTH
            val gY = y / SensorManager.GRAVITY_EARTH
            val gZ = z / SensorManager.GRAVITY_EARTH


            var gForce = sqrt(gX * gX + gY * gY + gZ * gZ).toFloat()
            // 진동을 감지했을 때
            // gforce가 기준치 이상일 경우
            if (gForce > 2.5) {
                Log.d(TAG, "기기 흔들림.")
                // 기기 진동
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(200) // 200 ms

                // Pay로 이동
                if (payFragment == null) {
                    payFragment = PayFragment()
                    navHostFragment.childFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_main, PayFragment()).addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // 그냥 생성되어야만 함.
    }
}