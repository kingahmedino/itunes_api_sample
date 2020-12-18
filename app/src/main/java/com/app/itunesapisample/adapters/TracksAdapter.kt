package com.app.itunesapisample.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.itunesapisample.databinding.TrackItemBinding
import com.app.itunesapisample.models.Track

class TracksAdapter(private val context: Context, private val tracks: List<Track>) :
    RecyclerView.Adapter<TracksAdapter.BindingHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = TrackItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingHolder(binding.root)
    }

    override fun getItemCount() = tracks.size

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val track = tracks[position]
        holder.mBinding?.track = track
        holder.mBinding?.executePendingBindings()
    }

    inner class BindingHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mBinding = DataBindingUtil.bind<TrackItemBinding>(itemView)
    }
}