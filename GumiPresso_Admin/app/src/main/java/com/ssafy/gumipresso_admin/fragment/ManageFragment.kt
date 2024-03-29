package com.ssafy.gumipresso_admin.fragment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.activity.LoginActivity
import com.ssafy.gumipresso_admin.activity.MainActivity
import com.ssafy.gumipresso_admin.adapter.ManageAdapter
import com.ssafy.gumipresso_admin.common.ApplicationClass
import com.ssafy.gumipresso_admin.common.ManageItems
import com.ssafy.gumipresso_admin.databinding.FragmentManageBinding
import com.ssafy.gumipresso_admin.fragment.manage.PushMessageFragment

class ManageFragment : Fragment() {
    private lateinit var binding: FragmentManageBinding

    private lateinit var manageAdapter: ManageAdapter
    private lateinit var manageItemsList: List<Map<String, Any>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manageItemsList = ManageItems.get()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivLogout.setOnClickListener{
            ApplicationClass.userPrefs.edit().clear().commit()
            Toast.makeText(context, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
            activity?.startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
        }

        manageAdapter = ManageAdapter(manageItemsList)
        manageAdapter.onManageItemClickListener = object : ManageAdapter.OnManageItemClickListener{
            override fun onClick(view: View, position: Int) {
                (activity as MainActivity).navController.navigate(manageItemsList[position]["action"].toString().toInt())
            }
        }
        binding.recyclerManage.apply {
            adapter = manageAdapter
            layoutManager = GridLayoutManager(context, 3)
        }
    }

}