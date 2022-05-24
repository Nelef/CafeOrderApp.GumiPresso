package com.ssafy.gumipresso.adapter.bindingadapter

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ssafy.gumipresso.R
import com.ssafy.gumipresso.model.dto.RecentOrderDetail
import com.ssafy.gumipresso.util.DateFormatUtil
import java.util.*


@BindingAdapter("recentOrderName")
fun recentOrderName(view: TextView, src: List<RecentOrderDetail>) {
    val list = src
    var totalQuantity = 0
    for (item in list) {
        totalQuantity += item.quantity
    }
    val text = if (list.size > 1) "외 ${totalQuantity - 1} 잔" else "${totalQuantity} 잔"
    view.text = "${list[0].name} ${text}"
}

@BindingAdapter("recentOrderPrice")
fun recentOrderPrice(view: TextView, src: List<RecentOrderDetail>) {
    val list = src
    var price = 0
    for (item in list) {
        price += item.price * item.quantity
    }
    view.text = "${price}원"
}

@BindingAdapter("recentOrderDate")
fun recentOrderDate(view: TextView, src: Date) {
    view.text = DateFormatUtil.convertYYMMDD(src)
}

@BindingAdapter("recentOrderQuantity")
fun recentOrderQuantity(view: TextView, src: LiveData<RecentOrderDetail>) {
    view.text = (src.value as RecentOrderDetail).quantity.toString()
}

//@BindingAdapter("recentOrderImage")
//fun recentOrderImage(view: ImageView, src: LiveData<RecentOrder>){
//    val packageName = "com.ssafy.gumipresso"
//    val s = "@drawable/${(src.value as RecentOrder).recentOrderDetail[0].img}"
//    val resId = view.resources.getIdentifier(s.substring(0, s.length-4), "drawable", packageName)
//    view.setImageResource(resId)
//}

@BindingAdapter("loadImage")
fun loadImage(view: ImageView, src: String?) {
    Glide.with(view.context)
        .load(Uri.parse("http://ssafymobile.iptime.org:7890/images/${src}"))
        .error(R.drawable.icon_empty_image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view)
}

@BindingAdapter("rating")
fun rating(view: TextView, src: Float?) {
    view.text = "%.1f".format(src)
}

@BindingAdapter("toAnonymous")
fun toAnonymous(view: TextView, src: String) {
    Log.d("TAG", "toAnonymous: $src")
    if (src.length >= 4) {
        view.text = src.slice(IntRange(0, 3)) + "***"
    } else {
        view.text = src + "***"
    }
}

@BindingAdapter("toString")
fun toString(view: TextView, src: Int) {
    view.text = src.toString()
}

@BindingAdapter("toStringWon") // 금액 콤마로 변경.
fun toStringWon(view: TextView, src: Int) {
    view.text = src.toString() + " 원"
}

@BindingAdapter("tableUse")
fun tableUse(view: ConstraintLayout, src: Boolean) {
    if(src){
        view.setBackgroundResource(R.color.gumipresso_red)
    } else {
        view.setBackgroundResource(R.color.gumipresso_gray)
    }
}

@BindingAdapter("orderStateTitle")
fun orderStateTitle(view: TextView, src: String?){
    when(src){
        "Y" -> view.text = "수령 완료"
        "P" -> view.text = "결제 완료"
        "N" -> view.text = "주문 완료"
    }
}
