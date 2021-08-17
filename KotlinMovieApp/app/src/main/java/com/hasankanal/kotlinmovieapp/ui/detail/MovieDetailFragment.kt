package com.hasankanal.kotlinmovieapp.ui.detail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.hasankanal.kotlinmovieapp.R
import com.hasankanal.kotlinmovieapp.common.BaseFragment
import com.hasankanal.kotlinmovieapp.databinding.FragmentMovieDetailBinding
import com.hasankanal.kotlinmovieapp.model.movie.MovieResult
import kotlinx.android.synthetic.main.fragment_movie_detail.*


class MovieDetailFragment : BaseFragment<FragmentMovieDetailBinding, MovieDetailViewModel>() {

    private lateinit var genreAdapter: GenreAdapter

    private var movie: MovieResult? = null
    private var isFav: Boolean?= null

    override fun getLayoutRes(): Int = R.layout.fragment_movie_detail

    override fun getViewModel(): Class<MovieDetailViewModel> = MovieDetailViewModel::class.java


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        arguments?.let {
            movie = it?.getParcelable("movie_details")
            dataBinding.detail = movie
            checkFav()
            dataBinding.imgFavorite.setOnClickListener {
                favorite()
            }
        }

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var movieDetailResponse = arguments?.getParcelable<MovieResult>("movie_details")
        viewModel.getMovieDetails(movieDetailResponse?.movieId)
        viewModel.movieDetails.observe(viewLifecycleOwner, Observer {
            it?.let {
                dataBinding.content = it
                genreAdapter = GenreAdapter(it.genres!!)
                recyclerviewGenres.adapter = genreAdapter
            }
        })

    }


    private fun favorite(){
        if (isFav!!){
            viewModel.deleteMovie(movie)
            Toast.makeText(context!!, "Removed", Toast.LENGTH_SHORT).show()
        }else{
            viewModel.insertMovie(movie)
            viewModel.addFavToFirebase(movie!!.movieId)
            Toast.makeText(context!!, "Added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkFav(){
        viewModel.getSingleMovie(movie?.movieId).observe(viewLifecycleOwner, Observer {
            if (it != null){
                dataBinding.imgFavorite.setImageResource(R.drawable.ic_favorite)
                isFav = true
            }else{
                dataBinding.imgFavorite.setImageResource(R.drawable.ic_favorite_border)
                isFav = false
            }
        })
    }
}

