package com.ssafy.gumipresso.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.BannerAdapter
import com.ssafy.gumipresso.adapter.RecentOrderAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentHomeBinding
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.util.FCMTokenUtil
import com.ssafy.gumipresso.util.UriPathUtil
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.ImageViewModel
import com.ssafy.gumipresso.viewmodel.RecentOrderViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel


private const val TAG ="HomeFragment"
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val orderViewModel: RecentOrderViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private val imageViewModel: ImageViewModel by viewModels()

    private lateinit var orderList: List<RecentOrder>
    private lateinit var recentOrderAdapter: RecentOrderAdapter

    // 배너
    private var bannerList = mutableListOf(R.drawable.banner1, R.drawable.banner2)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getUserFromPreferences()
        checkPermission()

        binding.ivNotification.setOnClickListener {
            (activity as MainActivity).movePage(CONST.FRAG_NOTI, null)
        }

        binding.btnFcmPush.setOnClickListener {
            userViewModel.sendFCMPushMessage(FCMTokenUtil().getFcmToken(), "gd", "doiododo")
        }

        binding.btnTestImg.setOnClickListener {
            openGalleryForImages()
        }

        // 배너
        binding.viewPager2.adapter = BannerAdapter(bannerList) // 어댑터 생성
        binding.viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL // 방향을 가로로

        // 배너 끝
    }

    fun openGalleryForImages() {
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            resultLauncher.launch(intent);
    }

    val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val data = it.data!!
            val imageUri = data.data!!
            val realUri = UriPathUtil().getPath(requireContext(), imageUri).toString()
            imageViewModel.uploadImage(realUri)
        }
    }

    private fun checkPermission(){
        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
            }
            override fun onPermissionDenied(deniedPermissions: List<String>) {
                Toast.makeText(context,
                    "스토리지에 접근 권한을 허가해주세요",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionlistener)
            .setDeniedMessage("권한을 허용해주세요. [설정] > [앱 및 알림] > [고급] > [앱 권한]")
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    private fun initViewModel(){
        userViewModel.user.observe(viewLifecycleOwner){
            if(userViewModel.user.value != null){
                binding.homeUserViewModel = userViewModel
                orderViewModel.getOrderList(userViewModel.user.value!!.id)
            }
        }
        orderViewModel.recentOrderList.observe(viewLifecycleOwner){
            if(orderViewModel.recentOrderList.value != null){
                binding.homeRecentOrderViewModel = orderViewModel
                orderList = orderViewModel.recentOrderList.value as List<RecentOrder>
                initOrderAdapter()
            }
        }
    }

    private fun initOrderAdapter(){
        recentOrderAdapter = RecentOrderAdapter(orderList, "home")
        recentOrderAdapter.apply {
            onCartIconClick = object : RecentOrderAdapter.OnCartIconClick {
                override fun onClick(view: View, position: Int) {
                    val recentOrder = (orderViewModel.recentOrderList.value as List<RecentOrder>)[position]
                    val recentOrderDetailList = recentOrder.recentOrderDetail
                    cartViewModel.clearCart()

                    for(i in recentOrderDetailList.indices){
                        val cart = Cart(recentOrderDetailList[i].productId, recentOrderDetailList[i].img, recentOrderDetailList[i].name, recentOrderDetailList[i].quantity, recentOrderDetailList[i].price, recentOrderDetailList[i].quantity * recentOrderDetailList[i].price, recentOrderDetailList[i].type)
                        cartViewModel.addCart(cart)
                    }
                    (activity as MainActivity).movePage(CONST.FRAG_CART_FROM_HOME, null)
                }
            }
            onOrderItemClick = object : RecentOrderAdapter.OnOrderItemClick {
                override fun onClick(view: View, position: Int) {
                    (activity as MainActivity).movePage(CONST.FRAG_RECENT_ORDER_DETAIL_FROM_HOME, orderList[position].oId.toString())
                }
            }
        }
        binding.apply {
            recyclerRecentOrder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            recyclerRecentOrder.adapter = recentOrderAdapter
        }
    }

    private fun getUserFromPreferences(){
        userViewModel.getUserInfo()
    }
}