package com.ssafy.gumipresso_admin.fragment.manage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso_admin.databinding.FragmentMenuEditBinding
import com.ssafy.gumipresso_admin.viewmodel.ProductViewModel
import com.ssafy.gumipresso_amdin.util.UriPathUtil

class MenuEditFragment : Fragment() {
    private lateinit var binding: FragmentMenuEditBinding
    private val productViewModel: ProductViewModel by viewModels()

    private var imageUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnInsertImage.setOnClickListener {
                checkPermission()
            }
            btnRegist.setOnClickListener {
                if(etProductName.text.toString().trim().isEmpty() || etProductPrice.text.toString().trim().isEmpty() || imageUrl == ""){
                    Toast.makeText(context, "모든 항목을 입력하셔야 합니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    productViewModel.insertProduct(etProductName.text.toString(), etProductPrice.text.toString().toInt(), "s", imageUrl)
                }
            }
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