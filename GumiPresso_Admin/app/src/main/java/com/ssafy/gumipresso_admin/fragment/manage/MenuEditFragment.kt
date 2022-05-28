package com.ssafy.gumipresso_admin.fragment.manage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.activity.LoginActivity
import com.ssafy.gumipresso_admin.activity.MainActivity
import com.ssafy.gumipresso_admin.adapter.ProductAdapter
import com.ssafy.gumipresso_admin.common.ApplicationClass
import com.ssafy.gumipresso_admin.databinding.FragmentMenuEditBinding
import com.ssafy.gumipresso_admin.model.dto.Product
import com.ssafy.gumipresso_admin.viewmodel.ProductViewModel
import com.ssafy.gumipresso_amdin.util.UriPathUtil

private const val TAG ="MenuEditFragment"
class MenuEditFragment : Fragment() {
    private lateinit var binding: FragmentMenuEditBinding
    private val productViewModel: ProductViewModel by activityViewModels()

    lateinit var productAdapter: ProductAdapter
    lateinit var productList: List<Product>
    private var imageUrl = ""
    private val typeList = listOf<String>("coffee","cookie")
    private var type = "coffee"
    lateinit var product: Product
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

        initViewModel()

        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, typeList)
        binding.apply {
            tabLayout.addOnTabSelectedListener( object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab!!.position){
                        0 -> {
                            constRegist.visibility = View.VISIBLE
                            constEditDelete.visibility = View.GONE
                        }
                        1 -> {
                            constEditDelete.visibility = View.VISIBLE
                            constRegist.visibility = View.GONE
                        }
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
                override fun onTabReselected(tab: TabLayout.Tab?) {
                    when(tab!!.position){
                        0 -> {
                            productViewModel.setProductItem(Product("","",0,""))
                            productViewModel.setFlagState(false)

                        }
                    }
                }
            })
            spinner.apply {
                adapter = spinnerAdapter
                onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int,id: Long) {
                        type = typeList[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }
                }
            }
            
            btnInsertImage.setOnClickListener {
                checkPermission()
            }
            btnRegist.setOnClickListener {
                val name = etProductName.text.toString()
                val price = etProductPrice.text.toString().toInt()
                if(name.trim().isEmpty() || price.toString().trim().isEmpty() || imageUrl == ""){
                    Toast.makeText(context, "모든 항목을 입력하셔야 합니다.", Toast.LENGTH_SHORT).show()
                }
                else{
                    if(productViewModel.flagImageChange.value as Boolean && productViewModel.flagEdit.value as Boolean){
                        product.name = name
                        product.price = price
                        productViewModel.updateProduct(product, imageUrl)
                    }
                    else if(productViewModel.flagEdit.value as Boolean){
                        product.name = name
                        product.price = price
                        productViewModel.updateProduct(product, null)
                    }
                    else{
                        productViewModel.insertProduct(etProductName.text.toString(), etProductPrice.text.toString().toInt(), type, imageUrl)
                    }
                    Toast.makeText(context, "저장 되었습니다.", Toast.LENGTH_SHORT).show()
                    (activity as MainActivity).navController.popBackStack()
                }
            }
            btnDelete.setOnClickListener {
                productViewModel.deleteProduct(product.id!!)
                Toast.makeText(context, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).navController.popBackStack()
            }
            ivLogout.setOnClickListener{
                ApplicationClass.userPrefs.edit().clear().commit()
                Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                activity?.startActivity(Intent(activity, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }

    private fun initViewModel() {
        productViewModel.productList.observe(viewLifecycleOwner){
            productList = productViewModel.productList.value as List<Product>
            initAdapter()
        }
        productViewModel.flagEdit.observe(viewLifecycleOwner){
            binding.productVM = productViewModel
        }
        productViewModel.getProductList()
    }

    private fun initAdapter(){
        productAdapter = ProductAdapter(productList)
        Log.d(TAG, "initAdapter: $productList")
        productAdapter.onClickProductItemListener = object :ProductAdapter.OnClickProductItemListener{
            override fun onClick(view: View, position: Int) {
                productViewModel.setFlagState(true)
                product = productList[position]
                productViewModel.setProductItem(product)
                imageUrl = productList[position].img
                binding.constEditDelete.visibility = View.GONE
                binding.constRegist.visibility = View.VISIBLE
                binding.tabLayout.getTabAt(0)!!.select()
                binding.productVM = productViewModel
                Glide.with(requireContext()).load(Uri.parse("http://ssafymobile.iptime.org:7890/images/${imageUrl}"))
                    .error(R.drawable.icon_empty_image)
                    .into(binding.ivReviewImage)

            }
        }
        binding.recyclerProductEdit.apply {
            adapter = productAdapter
            layoutManager = GridLayoutManager(context, 3)
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

    val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data!!
                val imageUri = data.data!!
                productViewModel.setFlagImageItemChange(true)
                imageUrl = UriPathUtil().getPath(requireContext(), imageUri).toString()
                binding.ivReviewImage.setImageURI(imageUri)
            }
        }
}