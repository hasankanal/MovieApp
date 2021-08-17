package com.hasankanal.kotlinmovieapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hasankanal.kotlinmovieapp.R
import com.hasankanal.kotlinmovieapp.databinding.ItemMovieBinding
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult

class MovieAdapter(val movieDetailList: List<MovieResult> , val onItemClick : (MovieResult) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){
    class MovieViewHolder(var view : ItemMovieBinding) : RecyclerView.ViewHolder(view.root) {
        fun bind(model: MovieResult, onItemClick: (MovieResult) -> Unit){
            itemView.setOnClickListener { onItemClick(model) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemMovieBinding>(inflater, R.layout.item_movie
                , parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.view.movie = movieDetailList[position]
        holder.bind(movieDetailList[position],onItemClick)
    }

    override fun getItemCount(): Int {
        return movieDetailList.size
    }
}