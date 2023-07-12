package com.ssafy.gumipresso.fragment

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.databinding.FragmentReviewModifyBinding
import com.ssafy.gumipresso.model.dto.Comment
import com.ssafy.gumipresso.model.dto.Product
import com.ssafy.gumipresso.util.UriPathUtil
import com.ssafy.gumipresso.viewmodel.CommentViewModel
import com.ssafy.gumipresso.viewmodel.ProductViewModel

private const val TAG ="ReviewModifyFragment"
class ReviewModifyFragment : Fragment() {
    private lateinit var binding: FragmentReviewModifyBinding
    private val commentViewModel : CommentViewModel by activityViewModels()
    private var imageUrl: String? = null
    private lateinit var product: Product
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        product = arguments?.getSerializable("product") as Product
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewModifyBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply{
            commentVM = commentViewModel
            productVM = product
            if((commentViewModel.comment.value as Comment).img != null){
                Glide.with(requireContext())
                    .load("https://gumipresso-back.imoneleft.synology.me/images/${(commentViewModel.comment.value as Comment).img}")
                    .error(R.drawable.icon_empty_image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.ivReviewImage)
            }
            btnInsertImage.setOnClickListener {
                checkPermission()
            }
            btnAddComment.setOnClickListener {
                if(etComment.text.toString().trim().isNotEmpty()){
                    if(imageUrl != null){
                        commentViewModel.updateCommentImage(imageUrl!!)
                    }else{
                        commentViewModel.updateComment()
                    }
                    Toast.makeText(context,"수정이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).navController.popBackStack()
                }else{
                    Toast.makeText(context, "내용을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }

            ratingBarDialog.setOnRatingBarChangeListener { ratingBar, rating, b ->
                commentViewModel.setCommentRating(rating)
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
        var intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        resultLauncher.launch(intent);
    }

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imageUri = it.data!!.data!!
                imageUrl = UriPathUtil().getPath(requireContext(), imageUri).toString()
                commentViewModel.setImageUploadState(true)
                binding.ivReviewImage.setImageURI(imageUri)
            }
        }

}