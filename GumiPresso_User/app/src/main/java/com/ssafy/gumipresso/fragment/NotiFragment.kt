package com.ssafy.gumipresso.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.gumipresso.activity.MainActivity
import com.ssafy.gumipresso.adapter.NoticeAdapter
import com.ssafy.gumipresso.databinding.FragmentNotiBinding
import com.ssafy.gumipresso.util.NoticeMessageUtil
import com.ssafy.gumipresso.viewmodel.NoticeViewModel

private const val TAG ="NotiFragment"
class NotiFragment : Fragment() {
    private lateinit var binding: FragmentNotiBinding
    private val noticeViewModel: NoticeViewModel by viewModels()

    private lateinit var noticeList: List<String>
    private lateinit var noticeAdapter: NoticeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getCloudMessage()

        binding.ivBack.setOnClickListener {
            (activity as MainActivity).navController.popBackStack()
        }
    }

    private fun initViewModel(){
        noticeViewModel.notice.observe(viewLifecycleOwner){
            binding.homeNoticeViewModel = noticeViewModel
            if(noticeViewModel.notice.value != null){
                initNoticeAdapter()
            }
        }
    }

    private fun initNoticeAdapter(){
        noticeList = noticeViewModel.notice.value as List<String>
        noticeAdapter = NoticeAdapter(noticeList)
        Log.d(TAG, "initNoticeAdapter: $noticeList")
        noticeAdapter.onDeleteButtonClickListener = object : NoticeAdapter.OnDeleteButtonClick {
            override fun onDeleteClick(view: View, position: Int) {
                noticeViewModel.deleteNotice(position)
                NoticeMessageUtil.setListToSharedPreference(noticeViewModel.notice.value as MutableList<String>)
                initNoticeAdapter()
                binding.homeNoticeViewModel = noticeViewModel
            }
        }

        binding.apply {
            recyclerNoticeList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recyclerNoticeList.adapter = noticeAdapter
        }
    }
    private fun getCloudMessage(){
        noticeViewModel.getNoticeList()
    }
}