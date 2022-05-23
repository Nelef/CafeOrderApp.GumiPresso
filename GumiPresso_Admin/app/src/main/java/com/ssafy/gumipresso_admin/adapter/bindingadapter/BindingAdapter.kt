package com.ssafy.gumipresso_admin.adapter.bindingadapter


import android.net.Uri
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.ssafy.gumipresso_admin.R


@BindingAdapter("loadImage")
fun loadImage(view: ImageView, src: String?) {
    Glide.with(view.context)
        .load(Uri.parse("http://ssafymobile.iptime.org:7890/images/${src}"))
        .error(R.drawable.icon_empty_image)
        .into(view)
}

@BindingAdapter("tableUse")
fun tableUse(view: ConstraintLayout, src: Boolean) {
    if(src){
        view.setBackgroundResource(R.color.gumipresso_red)
    } else {
        view.setBackgroundResource(R.color.gumipresso_gray)
    }
}