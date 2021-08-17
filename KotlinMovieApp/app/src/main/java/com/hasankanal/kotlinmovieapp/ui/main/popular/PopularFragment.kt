package com.hasankanal.kotlinmovieapp.ui.main.popular

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.hasankanal.kotlinmovieapp.R
import com.hasankanal.kotlinmovieapp.common.BaseFragment
import com.hasankanal.kotlinmovieapp.databinding.FragmentPopularBinding
import com.hasankanal.kotlinmovieapp.ui.main.MovieAdapter
import kotlinx.android.synthetic.main.fragment_popular.*
import kotlinx.android.synthetic.main.fragment_popular.progressBar
import kotlinx.android.synthetic.main.fragment_top_rated.*


class PopularFragment : BaseFragment<FragmentPopularBinding, PopularViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_popular

    override fun getViewModel(): Class<PopularViewModel> = PopularViewModel::class.java

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

        viewModel.getPopularMovies()

        nextPageBt.setOnClickListener {
            viewModel.nextpage()
        }

        viewModel.popularMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerViewPopular.visibility = View.VISIBLE
                recyclerViewPopular.layoutManager = GridLayoutManager(context?.applicationContext!!,3)
                recyclerViewPopular.adapter = MovieAdapter(it){
                    val bundle = bundleOf("movie_details" to it)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_popularFragment2_to_movieDetailFragment, bundle)
                }
            }
        })

        viewModel.loadingPopularMovie.observe(viewLifecycleOwner, Observer { loadingTopRated ->
            loadingTopRated?.let {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    recyclerViewPopular.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })

    }



}