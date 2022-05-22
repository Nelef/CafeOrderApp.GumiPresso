package com.ssafy.gumipresso.fragment

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.ProductAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentOrderBinding
import com.ssafy.gumipresso.model.dto.Product
import com.ssafy.gumipresso.viewmodel.FavoriteViewModel
import com.ssafy.gumipresso.viewmodel.GPSViewModel
import com.ssafy.gumipresso.viewmodel.ProductViewModel

private const val TAG = "OrderFragment"

class OrderFragment : Fragment() {
    private lateinit var binding: FragmentOrderBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()
    private val gpsViewModel : GPSViewModel by activityViewModels()

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: List<Product>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFavoriteList()

        binding.apply {
            tvMenuDistance.text = (activity as MainActivity).distanceMethod()
            ivMap.setOnClickListener {
                (activity as MainActivity).movePage(CONST.FRAG_MAPS, null)
            }
            fab.setOnClickListener {
                (activity as MainActivity).movePage(CONST.FRAG_CART_FROM_ORDER, null)
            }
        }

        checkGPSPermission()
        gpsViewModel.distanceToStore.observe(viewLifecycleOwner){
            binding.gpsVM =  gpsViewModel
        }
        gpsViewModel.enableLocationServices()

        // 전체 메뉴
        productViewModel.productList.observe(viewLifecycleOwner) {
            if (it != null) {
                productList = productViewModel.productList.value as List<Product>
                initProductAdapter()
            }
        }
        productViewModel.getProductList()

        // 탭(전체메뉴, 선호메뉴)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        // 전체 메뉴
                        productViewModel.productList.observe(viewLifecycleOwner) {
                            if (it != null) {
                                productList = productViewModel.productList.value as List<Product>
                                initProductAdapter()
                            }
                        }
                        productViewModel.getProductList()
                    }
                    1 -> {
                        // 즐겨찾기 메뉴
                        productViewModel.productList.observe(viewLifecycleOwner) {
                            if (it != null) {
                                productList = productViewModel.productList.value as List<Product>
                                initProductAdapter()
                            }
                        }
                        productList = productList.filter{
                            favoriteViewModel.favoriteList.value?.contains(it.name) ?: false
                        }
                        initProductAdapter()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun tvMenuDistanceChange() {
        binding.tvMenuDistance.text = (activity as MainActivity).distanceMethod()
    }

    private fun initProductAdapter() {
        productAdapter = ProductAdapter(productList)
        productAdapter.onProductItemClick = object : ProductAdapter.OnProductItemClick {
            override fun onClick(view: View, position: Int) {
                (activity as MainActivity).movePage(
                    CONST.FRAG_ORDER_DETAIL,
                    productList[position].id.toString()
                )
            }
        }
        binding.recyclerProductList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = productAdapter
        }
    }

    private fun getFavoriteList() {
        favoriteViewModel.getFavoriteList()
    }

    private fun checkGPSPermission(){
        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
                gpsViewModel.setLocationRepository(requireContext())
                gpsViewModel.enableLocationServices()
                gpsViewModel.locationRepository?.let { it ->
                    it.observe(viewLifecycleOwner) { location ->
                        location?.let {
                            gpsViewModel.setLocationItem(it)
                        }
                    }
                }

                gpsViewModel.location?.observe(viewLifecycleOwner){
                    Log.d(TAG, "onViewCreated: $it")
                    gpsViewModel.getLocationInfo()
                }
            }
            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(context,
                    "도착시간 계산을 위한 위치 정보 사용에 동의해 주세요",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
            .check()
    }
}