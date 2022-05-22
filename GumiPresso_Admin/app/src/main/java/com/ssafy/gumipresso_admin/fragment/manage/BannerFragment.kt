package com.ssafy.gumipresso_admin.fragment.manage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.activity.MainActivity
import com.ssafy.gumipresso_admin.adapter.BannerAdapter
import com.ssafy.gumipresso_admin.databinding.FragmentBannerBinding
import com.ssafy.gumipresso_admin.model.dto.Banner
import com.ssafy.gumipresso_admin.viewmodel.BannerViewModel
import com.ssafy.gumipresso_amdin.util.UriPathUtil

class BannerFragment : Fragment() {
    private lateinit var binding: FragmentBannerBinding
    private val bannerViewModel by viewModels<BannerViewModel>()

    private lateinit var bannerAdapter: BannerAdapter
    private lateinit var bannerList: MutableList<Banner>
    private var imageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding.apply {
            bannerVM = bannerViewModel

            btnInsertImage.setOnClickListener {
                checkPermission()
            }

            btnRegist.setOnClickListener {
                bannerViewModel.insertBanner(Banner("", binding.etBannerUrl.text.toString()), imageUrl)
                Toast.makeText(context, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).navController.popBackStack()
            }



            tabLayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab!!.position){
                        0 -> {
                            bannerViewModel.setIsRegistTabState(true)
                            bannerVM = bannerViewModel
                        }
                        1 -> {
                            bannerViewModel.setIsRegistTabState(false)
                            bannerVM = bannerViewModel
                        }
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    when(tab!!.position){
                        0 -> {
                            bannerViewModel.setIsRegistTabState(true)
                            bannerVM = bannerViewModel
                        }
                        1 -> {
                            bannerViewModel.setIsRegistTabState(false)
                            bannerVM = bannerViewModel
                        }
                    }
                }
            })
            tabLayout.getTabAt(0)!!.select()
        }
    }

    private fun initViewModel(){
        bannerViewModel.bannerList.observe(viewLifecycleOwner){
            bannerList = it
            initAdapter()
        }
        bannerViewModel.getBannerListItems()
    }

    private fun initAdapter(){
        bannerAdapter = BannerAdapter(bannerList)
        bannerAdapter.onClickBannerListener = object : BannerAdapter.OnClickBannerListener{
            override fun onClick(view: View, position: Int) {
                (activity as MainActivity).navController.navigate(R.id.action_bannerFragment_to_bannerEditFragment, bundleOf("banner" to bannerList[position]))
            }
        }
        binding.recyclerBannerEdit.apply {
            adapter = bannerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun checkPermission(){
        val permissionlistener = object : PermissionListener {
            override fun onPermissionGranted() {
                openGalleryForImages()
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

    fun openGalleryForImages() {
        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        resultLauncher.launch(intent);
    }

    val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data!!
                val imageUri = data.data!!
                imageUrl = UriPathUtil().getPath(requireContext(), imageUri).toString()
                binding.ivReviewImage.setImageURI(imageUri)
            }
        }

}