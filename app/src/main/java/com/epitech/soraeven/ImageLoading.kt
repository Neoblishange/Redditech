package com.epitech.soraeven

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.request.target.Target

class ImageLoading {
    fun simpleImageViewIntegration(context: Context, url: String?, imageView: ImageView){
        val indexer = url?.indexOf("?")
        val extractedImageURL = if (indexer!! >= 0) url.substring(0, indexer) else url
        Glide.with(context)
            .load(extractedImageURL)
            .into(imageView)
    }

    fun customViewIntegration(context: Context, url: String?, view: View){
        Glide.with(context)
            .load(url)
            .override(Target.SIZE_ORIGINAL, view.getHeight())
            .fitCenter()
            .into(object : CustomViewTarget<View, Drawable>(view) {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    view.background = resource
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    // Handle error
                }

                override fun onResourceCleared(placeholder: Drawable?) {
                    // Handle placeholder
                }
            })
    }
}