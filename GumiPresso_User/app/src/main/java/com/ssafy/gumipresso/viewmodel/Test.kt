//package com.ssafy.smartstore.viewmodel
//
//import android.Manifest
//import android.app.Application
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothManager
//import android.content.Context
//import android.content.Intent
//import android.content.ServiceConnection
//import android.content.pm.PackageManager
//import android.util.Log
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import androidx.core.content.ContextCompat.getSystemService
//import androidx.lifecycle.ViewModel
//import org.altbeacon.beacon.*
//import kotlin.concurrent.thread
//
//private const val TAG ="test"
//
//class Test : ViewModel() , BeaconConsumer{
//
//    // 비콘 변수
//    private lateinit var beaconManager: BeaconManager
//    private val BEACON_UUID = "fda50693-a4e2-4fb1-afcf-c6eb07647825"
//    private val BEACON_MAJOR = "10004"
//    private val BEACON_MINOR = "54480"
//    private var STORE_INFO_POPUP = false
//    private var storeDistanceTV = "주변 카페를 찾는 중입니다."
//    private val region = Region(
//        "estimote",
//        Identifier.parse(BEACON_UUID),
//        Identifier.parse(BEACON_MAJOR),
//        Identifier.parse(BEACON_MINOR)
//    )
//    private var bluetoothAdapter: BluetoothAdapter? = null
//    // 비콘 변수 끝
//
//
//    fun startBeacon(bluetoothManager: BluetoothManager, context: Context){
//        // 비콘
//
//        bluetoothAdapter = bluetoothManager.adapter
//
//        beaconManager = BeaconManager.getInstanceForApplication(context)
//        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
//
//        checkPermission(context)
//        thread {
//            startScan()
//        }
//    }
//
//    // 권한 체크
//    private fun checkPermission(context: Context): Boolean {
//        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        )
//        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
//                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
//    }
//
//    // Beacon Scan 시작
//    private fun startScan() {
//        // 블루투스 Enable 확인
//        if (!isEnableBLEService()) {
//            requestEnableBLE()
//            Log.d(com.ssafy.smartstore.activity.TAG, "startScan: 블루투스가 켜지지 않았습니다.")
//            return
//        }
//
//        // 위치 정보 권한 허용 및 GPS Enable 여부 확인
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                ,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                100
//            )
//        }
//        Log.d(com.ssafy.smartstore.activity.TAG, "startScan: beacon Scan start")
//
//        // Beacon Service bind
//        beaconManager.bind(this)
//    }
//
//    // 블루투스 켰는지 확인
//    private fun isEnableBLEService(): Boolean {
//        if (!bluetoothAdapter!!.isEnabled) {
//            return false
//        }
//        return true
//    }
//
//
//
//
//
//    override fun onBeaconServiceConnect() {
//        TODO("Not yet implemented")
//    }
//
//    override fun getApplicationContext(): Context {
//
//    }
//
//    override fun unbindService(connection: ServiceConnection?) {
//        if (connection != null) {
//            applicationContext.unbindService(connection)
//        }
//    }
//
//    override fun bindService(intent: Intent?, connection: ServiceConnection?, mode: Int): Boolean {
//        if (connection != null) {
//            return applicationContext.bindService(intent, connection, mode)
//        }
//        return false
//    }
//}