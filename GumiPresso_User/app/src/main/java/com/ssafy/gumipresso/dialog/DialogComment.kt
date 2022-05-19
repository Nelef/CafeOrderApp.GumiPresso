package com.ssafy.gumipresso.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.ssafy.gumipresso.databinding.DialogCommentBinding
import com.ssafy.gumipresso.model.dto.Comment

class DialogComment : DialogFragment() {
    private lateinit var binding: DialogCommentBinding
    private lateinit var comment: Comment
    lateinit var onClickConfime: OnClickConfirm
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        comment = arguments?.getSerializable("comment") as Comment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogCommentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            ratingBarDialog.rating = comment.rating
            etDialogComment.setText(comment.comment)
            btnDialogModify.setOnClickListener {
                comment.comment = etDialogComment.text.toString()
                comment.rating = ratingBarDialog.rating
                onClickConfime.onClicked(comment)
                dismiss()
            }
            btnDialogCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    interface OnClickConfirm{
        fun onClicked(comment: Comment)
    }


}