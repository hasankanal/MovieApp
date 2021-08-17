package com.hasankanal.kotlinmovieapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hasankanal.kotlinmovieapp.R
import com.hasankanal.kotlinmovieapp.databinding.FragmentSignUpBinding
import kotlinx.android.synthetic.main.fragment_sign_up.*

import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class SignUpFragment : Fragment(R.layout.fragment_sign_up) ,AuthListener,  KodeinAware{


    override val kodein by kodein()
    private lateinit var dataBinding : FragmentSignUpBinding
    private val factory: AuthViewModelFactory by instance()
    private lateinit var viewModel : AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_sign_up,container,false)
        return dataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this,factory).get(AuthViewModel::class.java)
        dataBinding.viewSignup = viewModel

        viewModel.authListener = this
    }

    override fun onStarted() {
        progressbar.visibility = View.VISIBLE
    }

    override fun onSuccess() {
        progressbar.visibility = View.GONE
        Toast.makeText(context, "Kayit Olusturuldu!", Toast.LENGTH_LONG).show()
        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    override fun onFailure(message: String) {
        progressbar.visibility = View.GONE
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}