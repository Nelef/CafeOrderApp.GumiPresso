package com.ssafy.gumipresso.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.ProductAdapter
import com.ssafy.gumipresso.common.CONST
import com.ssafy.gumipresso.databinding.FragmentOrderBinding
import com.ssafy.gumipresso.model.dto.Product
import com.ssafy.gumipresso.viewmodel.ProductViewModel

private const val TAG ="OrderFragment"
class OrderFragment: Fragment(){
    private lateinit var binding: FragmentOrderBinding
    private val productViewModel: ProductViewModel by viewModels()

    private lateinit var productAdapter: ProductAdapter
    private lateinit var productList: List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvMenuDistance.text = (activity as MainActivity).distanceMethod()
            ivMap.setOnClickListener {
                (activity as MainActivity).movePage(CONST.FRAG_MAPS, null)
            }
            fab.setOnClickListener {
                (activity as MainActivity).movePage(CONST.FRAG_CART_FROM_ORDER, null)
            }
        }

        // 전체 메뉴
        productViewModel.productList.observe(viewLifecycleOwner){
            if(it != null){
                productList = productViewModel.productList.value as List<Product>
                initProductAdapter()
            }
        }
        productViewModel.getProductList()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> {
                        // 전체 메뉴
                        productViewModel.productList.observe(viewLifecycleOwner){
                            if(it != null){
                                productList = productViewModel.productList.value as List<Product>
                                initProductAdapter()
                            }
                        }
                        productViewModel.getProductList()
                    }
                    1 -> {
                        // 즐겨찾기 메뉴
                        productList = emptyList()
                        initProductAdapter()
                    }
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) { }
            override fun onTabReselected(tab: TabLayout.Tab?) { }
        })
    }

    fun tvMenuDistanceChange() {
        binding.tvMenuDistance.text = (activity as MainActivity).distanceMethod()
    }

    private fun initProductAdapter(){
        productAdapter = ProductAdapter(productList)
        productAdapter.onProductItemClick = object : ProductAdapter.OnProductItemClick{
            override fun onClick(view: View, position: Int) {
                (activity as MainActivity).movePage(CONST.FRAG_ORDER_DETAIL, productList[position].id.toString())
            }
        }
        binding.recyclerProductList.apply {
            layoutManager = LinearLayoutManager (context)
            adapter = productAdapter
        }

    }
}