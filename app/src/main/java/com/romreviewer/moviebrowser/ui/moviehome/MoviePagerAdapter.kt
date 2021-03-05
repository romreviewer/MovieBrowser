package com.romreviewer.moviebrowser.ui.moviehome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romreviewer.moviebrowser.R
import com.romreviewer.moviebrowser.data.api.POSTER_BACK_DROP
import com.romreviewer.moviebrowser.data.api.TheMovieDBClient
import com.romreviewer.moviebrowser.data.model.MovieViewItem

class MoviePagerAdapter(val data: List<MovieViewItem>) : RecyclerView.Adapter<MoviePagerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v: View = inflater.inflate(R.layout.item_movie_large, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(POSTER_BACK_DROP+data[position].imagePath)
            .centerCrop()
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageViewMoviePoster)

    }
}
