package com.ssafy.gumipresso.fragment

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.databinding.FragmentMapsBinding
import android.provider.Settings
import com.ssafy.gumipresso.activity.MainActivity


class MapsFragment : Fragment(), OnMapReadyCallback {
    companion object {
        private const val UPDATE_INTERVAL = 1000 // 1초
        private const val FASTEST_UPDATE_INTERVAL = 500 // 0.5초
    }

    private lateinit var binding: FragmentMapsBinding
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var mylocation: Location
    private var needRequest = false // 위치 서비스 요청

    // 정보창 클릭 리스너
    var infoWindowClickListener =
        GoogleMap.OnInfoWindowClickListener {
            // Toast.makeText(this, "정보창 클릭 Marker ID : ${it.position}", Toast.LENGTH_SHORT).show()
            val builder = AlertDialog.Builder(requireContext())
            val dialogView = layoutInflater.inflate(R.layout.dialog_map, null)

            builder.setView(dialogView)
                .setPositiveButton("전화걸기") { dialogInterface, i ->
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse("tel:12345678")
                    startActivity(intent)
                }
                .setNegativeButton("길찾기") { dialogInterface, i ->
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(
                            "http://maps.google.com/maps?" +
                                    "saddr=${mylocation.latitude},${mylocation.longitude}&" +
                                    "daddr=${it.position.latitude},${it.position.longitude}"
                        )
                    )
                    startActivity(intent)
                }
                .show()
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).visibilityBottomNavBar(true)

        init()

        binding.ivBack.setOnClickListener {
            (activity as MainActivity).visibilityBottomNavBar(false)
            (activity as MainActivity).navController.popBackStack()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).visibilityBottomNavBar(false)
    }

    private fun init() {
        locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = UPDATE_INTERVAL.toLong()
            smallestDisplacement = 10.0f
            fastestInterval = FASTEST_UPDATE_INTERVAL.toLong()
        }

        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(locationRequest)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        val DEFAULT_LOCATION = LatLng(36.10830144233874, 128.41827450414362)
        mMap.addMarker(MarkerOptions().position(DEFAULT_LOCATION).title("구미프레소"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15f))

        // 구미프레소 클릭시 다이어로그 띄우기
        mMap.setOnInfoWindowClickListener(infoWindowClickListener);

        setMyLocation()
    }

    private fun setMyLocation() {
        if (checkPermission()) {
            // 위치 퍼미션 가지고 있다면 내 위치 가져오기
            startLocationUpdates()
        } else {
            // 퍼미션 없으면 권한 요청
            requestPermission {
                // 권한 승인 했을때 내 위치 가져오기
                startLocationUpdates()
            }
        }
    }

    // 권한 체크
    private fun checkPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    // 내 위치 가져오기
    private fun startLocationUpdates() {
        // 위치서비스 활성화 여부 check
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting()
        } else {
            if (checkPermission()) {
                mFusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()!!
                )

                mMap?.let {
                    it.isMyLocationEnabled = true   // 내 위치 버튼
                    it.uiSettings.isZoomControlsEnabled = true  // +, -버튼
                }
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            locationResult.locations.let {
                // 위치 정보가 있다면
                if (it.size > 0) {
                    mylocation = it[it.size - 1]
                    var currentPosition = LatLng(mylocation.latitude, mylocation.longitude)

                    mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentPosition, 15f))
                }
            }
        }
    }

    // 위치 서비스 활성화 여부
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager =
            requireActivity().getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    // 위치 서비스 황성화 요청 다이얼로그
    private fun showDialogForLocationServiceSetting() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("위치 서비스 비활성화")
            setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n")
            setCancelable(true)
            setPositiveButton("설정") { _, _ ->
                val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                gpsSettingIntentLauncher.launch(callGPSSettingIntent)
            }
            setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
            create()
            show()
        }
    }

    // 위치 서비스 활성화
    private val gpsSettingIntentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (checkLocationServicesStatus()) {
                needRequest = true
                startLocationUpdates()
            } else {
                Toast.makeText(
                    requireContext(),
                    "위치 서비스가 꺼져 있어, 현재 위치를 확인할 수 없습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun requestPermission(logic: () -> Unit) {
        TedPermission.create()
            .setPermissionListener(object : PermissionListener {
                override fun onPermissionGranted() {
                    logic()
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(requireContext(), "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
                }
            })
            .setDeniedMessage("[설정] 에서 위치 접근 권한을 부여해야만 사용이 가능합니다.")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
    }
}