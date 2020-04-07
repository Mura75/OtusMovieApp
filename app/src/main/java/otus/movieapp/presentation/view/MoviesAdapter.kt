package otus.movieapp.presentation.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import otus.movieapp.R
import otus.movieapp.data.model.MovieData
import otus.movieapp.data.network.NetworkConstants

class MoviesAdapter(
    private val itemClickListener: ((item: MovieData) -> Unit)? = null
) : RecyclerView.Adapter<BaseViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1

    private var isLoaderVisible = false

    private val movies = ArrayList<MovieData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            VIEW_TYPE_NORMAL -> MovieViewHolder(
                view = inflater.inflate(R.layout.item_movie, parent, false),
                itemClickListener = itemClickListener
            )
            VIEW_TYPE_LOADING -> ProgressViewHolder(
                view = inflater.inflate(R.layout.item_loading, parent, false)
            )
            else -> throw Throwable("invalid view")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoaderVisible) {
            if (position == movies.size - 1) {
                VIEW_TYPE_LOADING
            } else {
                VIEW_TYPE_NORMAL
            }
        } else {
            VIEW_TYPE_NORMAL
        }
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bind(movies[position])
        }
    }

    fun addItems(list: List<MovieData>) {
        movies.addAll(list)
        notifyDataSetChanged()
    }

    fun setNewItems(list: List<MovieData>) {
        movies.clear()
        addItems(list)
        isLoaderVisible = false
    }

    fun addLoading() {
        isLoaderVisible = true
        movies.add(MovieData(id = -1))
        notifyItemInserted(movies.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position = movies.size - 1
        if (movies.isNotEmpty()) {
            val item = getItem(position)
            if (item != null) {
                movies.removeAt(position)
                notifyItemRemoved(position)
            }
        }
    }

    fun getItem(position: Int): MovieData? {
        return movies.get(position)
    }

    fun clearAll() {
        movies.clear()
        notifyDataSetChanged()
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

    inner class ProgressViewHolder(view: View): BaseViewHolder(view) {

        override fun clear() {}
    }
}