package com.ssafy.gumipresso_admin.fragment.manage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.activity.LoginActivity
import com.ssafy.gumipresso_admin.activity.MainActivity
import com.ssafy.gumipresso_admin.common.ApplicationClass
import com.ssafy.gumipresso_admin.databinding.FragmentBannerEditBinding
import com.ssafy.gumipresso_admin.model.dto.Banner
import com.ssafy.gumipresso_admin.viewmodel.BannerViewModel
import com.ssafy.gumipresso_amdin.util.UriPathUtil

class BannerEditFragment : Fragment() {
    private lateinit var binding: FragmentBannerEditBinding
    private val bannerViewModel by viewModels<BannerViewModel>()

    lateinit var banner: Banner
    private var imageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        banner = arguments?.getSerializable("banner") as Banner
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        binding.apply {
            ivLogout.setOnClickListener{
                ApplicationClass.userPrefs.edit().clear().commit()
                Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                activity?.startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
            btnInsertImage.setOnClickListener {
                checkPermission()
            }
            btnRegist.setOnClickListener {
                val url = etBannerUrl.text.toString()
                if(url.trim().isNotEmpty()){
                    banner.url = if(!url.contains("https://") || !url.contains("http://"))"https://"+url else url
                    if(imageUrl == ""){
                        bannerViewModel.updateBanner(banner)
                    }else{
                        bannerViewModel.updateBannerImage(banner, imageUrl)
                    }
                    Toast.makeText(context, "저장 되었습니다", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).navController.navigate(R.id.action_bannerEditFragment_to_manageFragment)
                }else{
                    Toast.makeText(context, "항목을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }


            }
            btnDelete.setOnClickListener {
                bannerViewModel.deleteBanner(banner)
            }
            tabLayout.getTabAt(1)!!.select()
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab!!.position){
                        0-> (activity as MainActivity).navController.popBackStack()
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })
        }
    }

    private fun initViewModel() {
        bannerViewModel.setBannerItem(banner)
        binding.apply {
            bannerVM = bannerViewModel
            Glide.with(requireContext())
                .load(Uri.parse("http://ssafymobile.iptime.org:7890/images/${banner.img}"))
                .error(R.drawable.icon_empty_image)
                .into(ivReviewImage)
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
