package com.ssafy.gumipresso.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.ssafy.gumipresso.databinding.DialogScoreBinding
import com.ssafy.gumipresso.model.dto.Comment
import com.ssafy.gumipresso.viewmodel.CommentViewModel

class DialogScore: DialogFragment() {
    private lateinit var binding: DialogScoreBinding
    private val commentViewModel: CommentViewModel by activityViewModels()
    private lateinit var comment: Comment
    lateinit var onClickConfirm: OnClickConfirmListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comment = arguments?.getSerializable("comment") as Comment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            btnConfirm.setOnClickListener {

                comment.rating = ratingBarDialog.rating
                onClickConfirm.onClicked(comment)
                //commentViewModel.insertComment(comment)
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }


    interface OnClickConfirmListener{
        fun onClicked(comment: Comment)
    }
}