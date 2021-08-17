package com.hasankanal.kotlinmovieapp.ui.main.toprated

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.hasankanal.kotlinmovieapp.R
import com.hasankanal.kotlinmovieapp.common.BaseFragment
import com.hasankanal.kotlinmovieapp.databinding.FragmentTopRatedBinding
import com.hasankanal.kotlinmovieapp.ui.main.MovieAdapter
import kotlinx.android.synthetic.main.fragment_top_rated.*


class TopRatedFragment : BaseFragment<FragmentTopRatedBinding, TopRatedViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_top_rated

    override fun getViewModel(): Class<TopRatedViewModel> = TopRatedViewModel::class.java

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getTopRatedMovies()

        nextPageTopRatedBt.setOnClickListener {


            viewModel.nextPageTopRated()
        }



        viewModel.topRatedMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerViewTopRated.visibility = View.VISIBLE
                recyclerViewTopRated.layoutManager = GridLayoutManager(context?.applicationContext!!,3)
                recyclerViewTopRated.adapter = MovieAdapter(it) {
                    val bundle = bundleOf("movie_details" to it)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_topRatedFragment_to_movieDetailFragment, bundle)
                }
            }
        })

        viewModel.loadingTopRatedMovie.observe(viewLifecycleOwner, Observer { loadingTopRated ->
            loadingTopRated?.let {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    recyclerViewTopRated.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }
}