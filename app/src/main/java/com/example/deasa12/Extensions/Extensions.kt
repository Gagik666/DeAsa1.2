package com.example.deasa12.Extensions

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

fun Fragment.openFragment(fragmentId: Int) {
    findNavController().navigate(fragmentId)
}

fun Fragment.dialog(text: String) {
    val bulder = AlertDialog.Builder(context)
    bulder.setTitle(text)
    bulder.setPositiveButton("Ok") { dialog, i -> }
    bulder.show()
}


fun Fragment.checkForInternet(context: Context): Boolean {

    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        else -> false
    }
}

fun Fragment.isValidEmail(email: String): Boolean {
    return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun Fragment.isRegistred(): Boolean {
    return FirebaseAuth.getInstance().currentUser != null
}