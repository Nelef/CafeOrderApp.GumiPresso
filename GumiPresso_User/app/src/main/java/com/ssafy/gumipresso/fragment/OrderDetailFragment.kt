package com.ssafy.gumipresso.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.CommentAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentOrderDetailBinding
import com.ssafy.gumipresso.dialog.DialogComment
import com.ssafy.gumipresso.model.dto.*
import com.ssafy.gumipresso.util.FavoriteUtil
import com.ssafy.gumipresso.util.NoticeMessageUtil
import com.ssafy.gumipresso.viewmodel.*

private const val TAG = "OrderDetailFragment"

class OrderDetailFragment : Fragment() {
    private lateinit var binding: FragmentOrderDetailBinding
    private val productViewModel: ProductViewModel by viewModels()
    private val cartViewModel: CartViewModel by activityViewModels()
    private val commentViewModel: CommentViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val favoriteViewModel: FavoriteViewModel by activityViewModels()

    private lateinit var commentAdapter: CommentAdapter
    private lateinit var commentList: MutableList<Comment>
    private lateinit var user: User
    private lateinit var product: Product
    private lateinit var productId: String
    private var isFavorite = false

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
                        product.id!!,
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

        // 툴바 즐겨찾기 구현
        binding.apply {
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.add_favorite -> {
                        Toast.makeText(requireContext(), "즐겨찾기 추가", Toast.LENGTH_SHORT).show()
                        toolbar.menu.findItem(R.id.add_favorite).isVisible = false
                        toolbar.menu.findItem(R.id.remove_favorite).isVisible = true
                        FavoriteUtil.addToSharedPreference(productViewModel.product.value?.name.toString())
                        true
                    }
                    R.id.remove_favorite -> {
                        Toast.makeText(requireContext(), "즐겨찾기 제거", Toast.LENGTH_SHORT).show()
                        toolbar.menu.findItem(R.id.add_favorite).isVisible = true
                        toolbar.menu.findItem(R.id.remove_favorite).isVisible = false
                        favoriteViewModel.deleteFavoriteList(productViewModel.product.value?.name.toString())
                        FavoriteUtil.setListToSharedPreference(favoriteViewModel.favoriteList.value as MutableList<String>)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun initViewModel() {
        productId = arguments?.getString("product_id").toString()

        productViewModel.getSelectProduct(productId)
        commentViewModel.getComments(productId.toInt())
        user = userViewModel.user.value as User

        productViewModel.product.observe(viewLifecycleOwner) {
            binding.productVM = productViewModel
            product = it
            initisFavorite(product.name)
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
                    commentViewModel.setCommentItem(commentList[position])
                    (activity as MainActivity).navController.navigate(R.id.action_orderDetailFragment_to_reviewModifyFragment, bundleOf("product" to product))
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
                        commentViewModel.setCommentItem(commentList[position])
                        commentViewModel.deleteComment()
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
        (activity as MainActivity).visibilityBottomNavBar(false)
        super.onDestroy()
    }

    fun initisFavorite(name:String) {
        for(str in favoriteViewModel.favoriteList.value!!){
            if(str == name){
                isFavorite = true
                break
            }
        }
        if (isFavorite) {
            binding.toolbar.menu.findItem(R.id.add_favorite).isVisible = false
            binding.toolbar.menu.findItem(R.id.remove_favorite).isVisible = true
        } else {
            binding.toolbar.menu.findItem(R.id.add_favorite).isVisible = true
            binding.toolbar.menu.findItem(R.id.remove_favorite).isVisible = false
        }
    }
}