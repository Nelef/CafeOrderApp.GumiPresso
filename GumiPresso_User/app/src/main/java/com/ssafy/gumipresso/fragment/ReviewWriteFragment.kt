package com.ssafy.gumipresso.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.ssafy.gumipresso.viewmodel.CartViewModel
import com.ssafy.gumipresso.viewmodel.CommentViewModel
import com.ssafy.gumipresso.viewmodel.ProductViewModel
import com.ssafy.gumipresso.viewmodel.UserViewModel

class ReviewWriteFragment : Fragment() {
    private lateinit var binding: FragmentReviewWriteBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

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
    }

    fun insert(comment: Comment) {
        commentViewModel.insertComment(comment)
        Toast.makeText(requireContext(), "등록되었습니다", Toast.LENGTH_SHORT).show()
    }
}