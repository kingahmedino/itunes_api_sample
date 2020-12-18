package com.app.itunesapisample.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.itunesapisample.R
import com.app.itunesapisample.adapters.TracksAdapter
import com.app.itunesapisample.models.Track
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

const val COLUMN_SPAN = 2

@BindingAdapter("tracksList")
fun setTracks(recyclerView: RecyclerView, tracks: List<Track>?){
    val layoutManager = recyclerView.layoutManager
    if (layoutManager == null){
        recyclerView.layoutManager =
            GridLayoutManager(recyclerView.context, COLUMN_SPAN, GridLayoutManager.HORIZONTAL, false)
    }
    if (tracks != null){
        recyclerView.adapter = TracksAdapter(recyclerView.context, tracks)
    }
}

@BindingAdapter("imageUrl")
fun setImage(imageView: ImageView, url: String?){
    Glide.with(imageView.context)
        .load(url)
        .placeholder(R.drawable.ic_baseline_error_outline_24)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}