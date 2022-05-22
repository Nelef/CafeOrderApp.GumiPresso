package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListCommentItemBinding
import com.ssafy.gumipresso.fragment.OrderDetailFragment
import com.ssafy.gumipresso.model.dto.Comment
import com.ssafy.gumipresso.viewmodel.CommentViewModel

class CommentAdapter(
    val commentList: List<Comment>,
    val frag: OrderDetailFragment,
    var userId: String
) : RecyclerView.Adapter<CommentAdapter.ViewHoler>() {

    inner class ViewHoler(val binding: ListCommentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.commentVM = CommentViewModel()
        }
        fun bind(item: Comment) {
            binding.apply {
                commentVM!!.setCommentItem(item)
                if (item.userId == userId) {
                    btnMenuDelete.visibility = View.VISIBLE
                    btnMenuEdit.visibility = View.VISIBLE
                    divider0.visibility = View.VISIBLE
                }
            }
        }

        fun bindClickListener(listener: OnClickEdit) {
            binding.btnMenuEdit.setOnClickListener {
                listener.onClick(it, adapterPosition)
            }
        }

        fun bindClickListener(listener: OnClickRemove) {
            binding.btnMenuDelete.setOnClickListener {
                listener.onClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val listCommentItemBinding =
            ListCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(listCommentItemBinding).apply {
            bindClickListener(onClickRemove)
            bindClickListener(onClickEdit)
        }
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = commentList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = commentList.size

    lateinit var onClickEdit: OnClickEdit
    lateinit var onClickRemove: OnClickRemove

    interface OnClickEdit {
        fun onClick(view: View, position: Int)
    }

    interface OnClickRemove {
        fun onClick(view: View, position: Int)
    }
}