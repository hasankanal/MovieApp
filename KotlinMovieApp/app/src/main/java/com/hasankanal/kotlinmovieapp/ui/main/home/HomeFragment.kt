package com.hasankanal.kotlinmovieapp.ui.main.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hasankanal.kotlinmovieapp.R
import com.hasankanal.kotlinmovieapp.common.BaseFragment
import com.hasankanal.kotlinmovieapp.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding , HomeViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_home
    override fun getViewModel(): Class<HomeViewModel> = HomeViewModel::class.java



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        dataBinding.home=viewModel

        return dataBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }




}