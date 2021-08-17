package com.hasankanal.kotlinmovieapp.ui.main.upcoming

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
import com.hasankanal.kotlinmovieapp.databinding.FragmentUpcomingBinding
import com.hasankanal.kotlinmovieapp.ui.main.MovieAdapter
import kotlinx.android.synthetic.main.fragment_top_rated.*
import kotlinx.android.synthetic.main.fragment_upcoming.*
import kotlinx.android.synthetic.main.fragment_upcoming.progressBar

class UpcomingFragment : BaseFragment<FragmentUpcomingBinding, UpcomingViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_upcoming

    override fun getViewModel(): Class<UpcomingViewModel>  = UpcomingViewModel::class.java

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



        viewModel.getUpcomingMovies()

        nextPageUpcomingBt.setOnClickListener {
            viewModel.nextPageUpcoming()
        }

        viewModel.upcomingMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerViewUpcoming.visibility = View.VISIBLE
                recyclerViewUpcoming.layoutManager = GridLayoutManager(context?.applicationContext!!, 3)
                recyclerViewUpcoming.adapter = MovieAdapter(it){
                    val bundle = bundleOf("movie_details" to it)
                    Navigation.findNavController(view)
                        .navigate(R.id.action_upcomingFragment_to_movieDetailFragment,bundle)
                }
            }
        })

        viewModel.loadingUpcomingMovie.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it) {
                    progressBar.visibility = View.VISIBLE
                    recyclerViewUpcoming.visibility = View.GONE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }


}