package otus.movie_list.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import otus.core_api.base.BaseViewHolder
import otus.core_api.dto.MovieData
import otus.movie_list.R
import otus.movieapp.data.network.NetworkConstants

class MoviesAdapter(
    private val itemClickListener: ((item: MovieData) -> Unit)? = null
) : PagedListAdapter<MovieData, MoviesAdapter.MovieViewHolder>(movieDiffCallback) {

    companion object {
        private val movieDiffCallback = object : DiffUtil.ItemCallback<MovieData>() {
            override fun areItemsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieData, newItem: MovieData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(
            view = inflater.inflate(R.layout.item_movie, parent, false),
            itemClickListener = itemClickListener
        )
    }

    inner class MovieViewHolder(
        private val view: View,
        private val itemClickListener: ((item: MovieData) -> Unit)? = null
    ): BaseViewHolder(view) {

        private val tvTitle: TextView
        private val tvRating: TextView
        private val tvDescription: TextView
        private val tvDate: TextView
        private val ivPoster: ImageView

        init {
            tvTitle = view.findViewById(R.id.tvName)
            tvRating = view.findViewById(R.id.tvDate)
            ivPoster = view.findViewById(R.id.ivPoster)
            tvDescription = view.findViewById(R.id.tvDescription)
            tvDate = view.findViewById(R.id.tvGenre)
        }

        fun bind(item: MovieData) {
            tvTitle.text = item.title
            item.voteAverage?.let { rating ->
                tvRating.text = "$rating/10"
            }
            item.releaseDate?.let { date ->
                tvDate.text = date
            }
            item.overview?.let { tvDescription.text = it }
            val url = "${NetworkConstants.POSTER_BASE_URL}${item.posterPath}"
            Log.d("path_url", url)
            Glide.with(view.context)
                .load(url)
                .into(ivPoster)

            view.setOnClickListener {
                itemClickListener?.invoke(item)
            }
        }

        override fun clear() {

        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}