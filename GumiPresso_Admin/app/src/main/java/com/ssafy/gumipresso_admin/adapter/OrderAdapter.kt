package com.ssafy.gumipresso_admin.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.util.set
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.ssafy.gumipresso_admin.R
import com.ssafy.gumipresso_admin.databinding.ListItemOrderBinding
import com.ssafy.gumipresso_admin.model.dto.RecentOrder
import com.ssafy.gumipresso_admin.viewmodel.OrderViewModel

private const val TAG ="OrderAdapter"
class OrderAdapter(val list: MutableList<RecentOrder>): RecyclerView.Adapter<OrderAdapter.ViewHolder>() {
    private var sparseArray = SparseArray<Boolean>()
    private var itemView = HashMap<Int, ConstraintLayout>()
    private var imageView = HashMap<Int, ImageView>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder: ${list.size}")
        val listItemOrderBinding =
            ListItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(listItemOrderBinding, list)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sparseArray, itemView, imageView)
    }

    override fun getItemCount() = list.size

    class ViewHolder(val binding: ListItemOrderBinding, val list: MutableList<RecentOrder>) : RecyclerView.ViewHolder(binding.root) {


        private var parentView: ConstraintLayout = binding.constSimple
        private var childViewWrap: ConstraintLayout = binding.constDetail
        private var item_arrow: ImageView = binding.ivDropdown

        init {
            binding.orderVM = OrderViewModel()
        }
        fun bind(
            sparseArray: SparseArray<Boolean>,
            itemView: HashMap<Int, ConstraintLayout>,
            imageView: HashMap<Int, ImageView>
        ) {
            if (sparseArray[adapterPosition] == null) {
                sparseArray.put(adapterPosition, false)
            }

            binding.orderVM!!.setOrders(list[adapterPosition])
            binding.orderVM!!.getTotalValue()
            itemView[adapterPosition] = childViewWrap
            imageView[adapterPosition] = item_arrow

            if (!sparseArray[adapterPosition]) {
                collapseItem(
                    itemView[adapterPosition]!!,
                    imageView[adapterPosition]!!
                )
            } else {
                expandItem(
                    itemView[adapterPosition]!!,
                    imageView[adapterPosition]!!
                )
            }

            parentView.setOnClickListener {
                TransitionManager.beginDelayedTransition(childViewWrap, AutoTransition())

                when (childViewWrap.visibility) {
                    View.VISIBLE -> {
                        sparseArray[adapterPosition] = false
                        childViewWrap.visibility = View.GONE
                        collapseItem(childViewWrap, item_arrow)
                    }

                    View.GONE -> {
                        sparseArray[adapterPosition] = true
                        binding.recyclerRecentDetail.smoothScrollToPosition(adapterPosition)
                        childViewWrap.visibility = View.VISIBLE
                        expandItem(childViewWrap, item_arrow)
                    }
                }
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun expandItem(view: View, imageView: ImageView) {
            view.visibility = View.VISIBLE
            imageView.apply {
                setImageDrawable(
                    this.context.resources.getDrawable(
                        R.drawable.ic_arrow_up,
                        null
                    )
                )
            }
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        fun collapseItem(view: View, imageView: ImageView) {
            view.visibility = View.GONE
            imageView.apply {
                setImageDrawable(
                    this.context.resources.getDrawable(
                        R.drawable.ic_arrow_up,
                        null
                    )
                )
            }
        }
    }

}
