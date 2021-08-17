package com.hasankanal.kotlinmovieapp.ui.main.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.load.model.ModelCache
import com.hasankanal.kotlinmovieapp.R
import com.hasankanal.kotlinmovieapp.common.BaseFragment
import com.hasankanal.kotlinmovieapp.common.BaseVMFragment
import com.hasankanal.kotlinmovieapp.databinding.FragmentFavoriteBinding
import com.hasankanal.kotlinmovieapp.ui.detail.MovieDetailViewModel
import com.hasankanal.kotlinmovieapp.ui.main.MovieAdapter
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : BaseFragment<FragmentFavoriteBinding, FavoriteViewModel>() {


    override fun getLayoutRes(): Int = R.layout.fragment_favorite
    override fun getViewModel(): Class<FavoriteViewModel> = FavoriteViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.favMovie = viewModel
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewModel.addValueEventListener()

            viewModel.favMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerviewFavorites.visibility = View.VISIBLE
                recyclerviewFavorites.layoutManager = GridLayoutManager(context!!, 3)
                recyclerviewFavorites.adapter = MovieAdapter(it){
                    val bundle = bundleOf("movie_details" to it)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_favoriteFragment_to_movieDetailFragment, bundle)
                }
            }
        })
        viewModel.loadingFavoriMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    progressBarFavorite.visibility = View.VISIBLE
                    recyclerviewFavorites.visibility = View.GONE
                }else{
                    progressBarFavorite.visibility =View.GONE
                }
            }
        })





        }












   /*     viewModel.getAllMovies().observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerviewFavorites.layoutManager = GridLayoutManager(context!!, 3)
                recyclerviewFavorites.adapter = MovieAdapter(it){
                    val bundle = bundleOf("movie_details" to it)
                    Navigation.findNavController(view)
                            .navigate(R.id.action_favoriteFragment_to_movieDetailFragment, bundle)
                }
            }
        })

    */

    }




