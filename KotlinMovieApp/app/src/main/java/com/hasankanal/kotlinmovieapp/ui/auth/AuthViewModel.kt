package com.hasankanal.kotlinmovieapp.ui.auth

import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import com.hasankanal.kotlinmovieapp.repositories.UserRepository
import com.hasankanal.kotlinmovieapp.model.favori.FavMovie
import com.hasankanal.kotlinmovieapp.model.user.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel (
    private val repository: UserRepository
        ) : ViewModel() {
    //Kullanıcıdan email ve sifre alınması icin
    var email: String? = null
    var password: String? = null

    //Durumları kontrol etmek icin gerekli Listener
    var authListener: AuthListener? = null

    var movieList = ArrayList<FavMovie>()


    //Kullanacagimiz her seyi disposables icine ekleyecegiz ve isimiz bittiginde hepsini direkt atabiliriz.
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }


    fun login() {

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Gecersiz e-mail veya sifre!")
            return
        }

        authListener?.onStarted()

        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                //Girisin basarili oldugunu bildiriyoruz
                authListener?.onSuccess()
                System.out.println("Hata")
            }, {
                //Girisin hatali oldugunu bildiriyoruz
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun signup() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onFailure("Please input all values")
            return
        }
        authListener?.onStarted()
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
                writeNewUser(movieList,user!!.uid,getUsernameFromEmail(user!!.email),user!!.email)
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)


    }

    fun goToSignup(view: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
        Navigation.findNavController(view).navigate(action)
    }

    fun goToLogin(view: View) {
        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        Navigation.findNavController(view).navigate(action)
    }

    private fun writeNewUser(favMovieList : ArrayList<FavMovie>,userId : String , username : String? , email: String?){
        val user = User(favMovieList,userId!!,username!!,email!!)

        FirebaseDatabase.getInstance().reference.child("users").child(userId).setValue(user)
    }

    private fun getUsernameFromEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }






    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }



}