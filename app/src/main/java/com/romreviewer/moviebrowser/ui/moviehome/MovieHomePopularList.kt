package com.romreviewer.moviebrowser.ui.moviehome

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.romreviewer.moviebrowser.data.api.POSTER_BASE_URL
import com.romreviewer.moviebrowser.R
import com.romreviewer.moviebrowser.data.model.Movie
import com.romreviewer.moviebrowser.data.repository.NetworkState
import com.romreviewer.moviebrowser.ui.moviedetails.MovieDetail

class MovieHomePopularList(val context: Context) : PagedListAdapter<Movie, RecyclerView.ViewHolder>(MovieDiffCallback()) {

    val MOVIE_VIEW_TYPE = 1
    val NETWORK_VIEW_TYPE = 2

    private var networkState: NetworkState? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View

        return if (viewType == MOVIE_VIEW_TYPE) {
            view = layoutInflater.inflate(R.layout.movie_list_item, parent, false)
            MovieItemViewHolder(view)
        } else {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            NetworkStateItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == MOVIE_VIEW_TYPE) {
            (holder as MovieItemViewHolder).bind(getItem(position),context)
        }
        else {
            (holder as NetworkStateItemViewHolder).bind(networkState)
        }
    }


    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState != NetworkState.LOADED
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            NETWORK_VIEW_TYPE
        } else {
            MOVIE_VIEW_TYPE
        }
    }




    class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }


    class MovieItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(movie: Movie?,context: Context) {
            val moviePosterURL = POSTER_BASE_URL + movie?.posterPath
            Glide.with(itemView.context)
                .load(moviePosterURL)
                .centerCrop()
                .into(itemView.findViewById<ImageView>(R.id.cv_iv_movie_poster));

            itemView.setOnClickListener{
                val intent = Intent(context, MovieDetail::class.java)
                intent.putExtra("id", movie?.id)
                intent.putExtra("overview",movie?.overview)
                intent.putExtra("url",moviePosterURL)
                intent.putExtra("name",movie?.title)
                intent.putExtra("releaseDate",movie?.releaseDate)
                intent.putExtra("genre",movie?.genre_ids as ArrayList<*>)
                //intent.putExtra("vote",movie.vote_average)
                intent.putExtra("voteCount",movie.vote_count)
                context.startActivity(intent)
            }

        }

    }

    class NetworkStateItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {

        fun bind(networkState: NetworkState?) {
            val errorMsgItem=itemView.findViewById<TextView>(R.id.error_msg_item)
            val progressBarItem=itemView.findViewById<ProgressBar>(R.id.progress_bar_item)
            if (networkState != null && networkState == NetworkState.LOADING) {
                progressBarItem.visibility = View.VISIBLE;
            }
            else  {
                progressBarItem.visibility = View.GONE;
            }

            if (networkState != null && networkState == NetworkState.ERROR) {
                errorMsgItem.visibility = View.VISIBLE;
                errorMsgItem.text = networkState.msg;
            }
            else if (networkState != null && networkState == NetworkState.ENDOFLIST) {
                errorMsgItem.visibility = View.VISIBLE;
                errorMsgItem.text = networkState.msg;
            }
            else {
                errorMsgItem.visibility = View.GONE;
            }
        }
    }


    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {                             //hadExtraRow is true and hasExtraRow false
                notifyItemRemoved(super.getItemCount())    //remove the progressbar at the end
            } else {                                       //hasExtraRow is true and hadExtraRow false
                notifyItemInserted(super.getItemCount())   //add the progressbar at the end
            }
        } else if (hasExtraRow && previousState != newNetworkState) { //hasExtraRow is true and hadExtraRow true and (NetworkState.ERROR or NetworkState.ENDOFLIST)
            notifyItemChanged(itemCount - 1)       //add the network message at the end
        }

    }
}