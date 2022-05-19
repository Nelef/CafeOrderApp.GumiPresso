package com.ssafy.gumipresso.adapter.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.ssafy.gumipresso.model.dto.RecentOrder
import com.ssafy.gumipresso.model.dto.RecentOrderDetail
import com.ssafy.gumipresso.util.DateFormatUtil


@BindingAdapter("recentOrderName")
fun recentOrderName(view: TextView, src: LiveData<RecentOrder>) {
    val list = (src.value as RecentOrder).recentOrderDetail
    var totalQuantity = 0
    for(item in list){
        totalQuantity += item.quantity
    }
    val text = if(list.size > 1) "외 ${totalQuantity - 1} 잔" else "${totalQuantity} 잔"
    view.text = "${list[0].name} ${text}"
}
@BindingAdapter("recentOrderPrice")
fun recentOrderPrice(view: TextView, src: LiveData<RecentOrder>){
    val list = (src.value as RecentOrder).recentOrderDetail
    var price = 0
    for(item in list){
        price += item.price * item.quantity
    }
    view.text = "${price}원"
}

@BindingAdapter("recentOrderDate")
fun recentOrderDate(view: TextView, src: LiveData<RecentOrder>){
    view.text = DateFormatUtil.convertYYMMDD((src.value as RecentOrder).orderTime)
}

@BindingAdapter("recentOrderQuantity")
fun recentOrderQuantity(view: TextView, src: LiveData<RecentOrderDetail>){
    view.text = (src.value as RecentOrderDetail).quantity.toString()
}

@BindingAdapter("recentOrderImage")
fun recentOrderImage(view: ImageView, src: LiveData<RecentOrder>){
    val packageName = "com.ssafy.gumipresso"
    val s = "@drawable/${(src.value as RecentOrder).recentOrderDetail[0].img}"
    val resId = view.resources.getIdentifier(s.substring(0, s.length-4), "drawable", packageName)
    view.setImageResource(resId)
}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, src: String?){
    val packageName = "com.ssafy.gumipresso"
    val s = "@drawable/${src}"
    val resId = view.resources.getIdentifier(s.substring(0, s.length-4), "drawable", packageName)
    view.setImageResource(resId)
}

@BindingAdapter("rating")
fun rating(view: TextView, src: Float?){
    view.text = "%.1f".format(src)
}