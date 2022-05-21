package com.ssafy.gumipresso.fragment

import android.app.AlertDialog
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
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.CommentAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentOrderDetailBinding
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

private const val TAG = "OrderDetailFragment"

class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentList: MutableList<Comment>
    private lateinit var user: User
    private lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).visibilityBottomNavBar(true)

        val productId = arguments?.getString("product_id").toString()

        productViewModel.getSelectProduct(productId)
        commentViewModel.getComments(productId.toInt())

        initViewModel()

        binding.apply {
            commentVM = commentViewModel
            btnPlus.setOnClickListener {
                productViewModel!!.setOrderQuantity(true)
            }
            btnMinus.setOnClickListener {
                productViewModel!!.setOrderQuantity(false)
            }
            btnAdd.setOnClickListener {

                val product = productViewModel.product.value as Product
                val quantity = productViewModel.quantity.value as Int
                if (quantity == 0) {
                    Toast.makeText(context, "수량을 추가해 주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    val cart = Cart(
                        product.id,
                        product.img,
                        product.name,
                        quantity,
                        product.price,
                        product.price * quantity,
                        product.type
                    )
                    cartViewModel.addCart(cart)
                    Toast.makeText(context, "장바구니에 추가 되었습니다.", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).navController.popBackStack()
                }
            }
            btnAddComment.setOnClickListener {
                (activity as MainActivity).movePage(CONST.FRAG_REVIEW_WRITE, productId)
            }
            ivBack.setOnClickListener {
                (activity as MainActivity).visibilityBottomNavBar(false)
                (activity as MainActivity).navController.popBackStack()
            }
        }
    }

    fun insert(comment: Comment) {
        commentViewModel.insertComment(comment)
        Toast.makeText(requireContext(), "등록되었습니다", Toast.LENGTH_SHORT).show()
    }

    private fun initViewModel() {
        user = userViewModel.user.value as User

        productViewModel.product.observe(viewLifecycleOwner) {
            binding.productVM = productViewModel
            product = it
        }
        productViewModel.quantity.observe(viewLifecycleOwner) {
            binding.productVM = productViewModel
        }
        commentViewModel.avgRating.observe(viewLifecycleOwner) {
            binding.commentVM = commentViewModel
        }

        commentViewModel.commentList.observe(viewLifecycleOwner) {
            commentViewModel.setAverageRating()
            commentList = it
            initAdapter()
        }
    }

    private fun initAdapter() {

        commentAdapter = CommentAdapter(commentList, this, user.id)
        commentAdapter.apply {
            onClickEdit = object : CommentAdapter.OnClickEdit {
                override fun onClick(view: View, position: Int) {
                    val dialog = DialogComment()
                    dialog.arguments = bundleOf("comment" to commentList[position])
                    dialog.onClickConfime = object : DialogComment.OnClickConfirm {
                        override fun onClicked(comment: Comment) {
                            commentViewModel.updateComment(comment)
                            Toast.makeText(context, "수정되었습니다", Toast.LENGTH_SHORT).show()
                        }
                    }
                    dialog.show(parentFragmentManager.beginTransaction(), "DC")
                }
            }
            onClickRemove = object : CommentAdapter.OnClickRemove {
                override fun onClick(view: View, position: Int) {
                    val builder = AlertDialog.Builder(requireActivity())
                    builder.setTitle("알림")
                    builder.setMessage("리뷰를 삭제하시겠습니까?")
                    builder.setNegativeButton("취소") { dialog, _ ->
                        dialog.cancel()
                    }
                    builder.setPositiveButton("확인") { dialog, _ ->
                        commentViewModel.deleteComment(commentList[position].id)
                        Toast.makeText(context, "삭제되었습니다", Toast.LENGTH_SHORT).show()
                    }.show()
                }
            }
        }
        binding.recyclerComment.apply {
            adapter = commentAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).visibilityBottomNavBar(false)
    }

}