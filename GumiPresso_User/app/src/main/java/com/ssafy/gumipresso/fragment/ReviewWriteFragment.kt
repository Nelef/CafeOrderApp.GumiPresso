package com.ssafy.gumipresso.fragment

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
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

class ReviewWriteFragment : Fragment() {
    private lateinit var binding: FragmentReviewWriteBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val imageViewModel: ImageViewModel by viewModels()

    private lateinit var user: User
    private lateinit var product: Product
    private lateinit var commentList: MutableList<Comment>

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
                insert(Comment(user.id, product.id, binding.ratingBarDialog.rating, inputString))
                (activity as MainActivity).navController.popBackStack()
            } else {
                Toast.makeText(context, "코멘트를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnInsertImage.setOnClickListener {
            openGalleryForImages()
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
        commentViewModel.insertComment(comment)
        Toast.makeText(requireContext(), "등록되었습니다", Toast.LENGTH_SHORT).show()
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
                val realUri = UriPathUtil().getPath(requireContext(), imageUri).toString()

                // UI 이미지 설정
                binding.ivReviewImage.setImageURI(imageUri)
                // 이미지 전송
//                imageViewModel.uploadImage(realUri)
            }
        }
}