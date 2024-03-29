package com.ssafy.gumipresso.fragment

import android.Manifest
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
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.CommentAdapter
import com.ssafy.gumipresso.databinding.FragmentOrderDetailBinding
import com.ssafy.gumipresso.databinding.FragmentReviewWriteBinding
import com.ssafy.gumipresso.dialog.DialogComment
import com.ssafy.gumipresso.dialog.DialogScore
import com.ssafy.gumipresso.model.dto.Cart
import com.ssafy.gumipresso.model.dto.Comment
import com.ssafy.gumipresso.model.dto.Product
import com.ssafy.gumipresso.model.dto.User
import com.ssafy.gumipresso.util.UriPathUtil
import com.ssafy.gumipresso.viewmodel.*

private const val TAG ="ReviewWriteFragment"
class ReviewWriteFragment : Fragment() {
    private lateinit var binding: FragmentReviewWriteBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val imageViewModel: ImageViewModel by viewModels()

    private lateinit var user: User
    private lateinit var product: Product
    private lateinit var commentList: MutableList<Comment>

    private var imageUrl: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.getSelectProduct(arguments?.getString("product_id").toString())
        commentViewModel.getComments(arguments?.getString("product_id").toString().toInt())

        initViewModel()

        binding.btnAddComment.setOnClickListener {
            val inputString = binding.etComment.text.toString().trim()
            if (!inputString.isEmpty()) {
                insert(Comment(user.id, product.id!!, binding.ratingBarDialog.rating, inputString, null))
                (activity as MainActivity).navController.popBackStack()
            } else {
                Toast.makeText(context, "코멘트를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnInsertImage.setOnClickListener {
            checkPermission()
        }
    }

    private fun initViewModel() {
        user = userViewModel.user.value as User

        productViewModel.product.observe(viewLifecycleOwner) {
            binding.productVM = productViewModel
            product = it
        }
        commentViewModel.commentList.observe(viewLifecycleOwner) {
            commentViewModel.setAverageRating()
            commentList = it
        }
        binding.ivBack.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }
    }

    fun insert(comment: Comment) {
        Log.d(TAG, "insert: $imageUrl")
        commentViewModel.insertComment(comment,imageUrl)
        Toast.makeText(requireContext(), "등록되었습니다", Toast.LENGTH_SHORT).show()
    }


    fun openGalleryForImages() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        resultLauncher.launch(intent);
    }

    val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imageUri = it.data!!.data!!
                imageUrl = UriPathUtil().getPath(requireContext(), imageUri).toString()
                commentViewModel.setImageUploadState(true)
                binding.ivReviewImage.setImageURI(imageUri)
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
}