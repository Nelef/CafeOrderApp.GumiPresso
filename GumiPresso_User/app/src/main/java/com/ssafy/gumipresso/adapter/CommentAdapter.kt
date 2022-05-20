package com.ssafy.gumipresso.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.gumipresso.databinding.ListCommentItemBinding
import com.ssafy.gumipresso.fragment.OrderDetailFragment
import com.ssafy.gumipresso.model.dto.Comment

class CommentAdapter(val commentList: List<Comment>, val frag: OrderDetailFragment, var userId: String): RecyclerView.Adapter<CommentAdapter.ViewHoler>(){
    inner class ViewHoler(val binding: ListCommentItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(item: Comment, view: View, position: Int){
            binding.apply {
                commentVM = item
                if(item.userId == userId){
                    btnMenuDelete.visibility = View.VISIBLE
                    btnMenuEdit.visibility = View.VISIBLE
                    divider0.visibility = View.VISIBLE
                }
                btnMenuEdit.setOnClickListener {
                    onClickEdit.onClick(view, position)
                }
                btnMenuDelete.setOnClickListener {
                    onClickRemove.onClick(view, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHoler {
        val listCommentItemBinding =
            ListCommentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHoler(listCommentItemBinding)
    }

    override fun onBindViewHolder(holder: ViewHoler, position: Int) {
        val item = commentList[position]
        holder.apply {
            bind(item, holder.itemView, position)
        }
    }

    override fun getItemCount(): Int = commentList.size

    lateinit var onClickRemove: OnClickRemove
    lateinit var onClickEdit: OnClickEdit
    interface OnClickRemove{
        fun onClick(view: View, position: Int)
    }
    interface OnClickEdit{
        fun onClick(view: View, position: Int)
    }
}