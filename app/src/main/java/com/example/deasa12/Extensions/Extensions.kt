package com.example.deasa12.Extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.deasa12.R
import com.example.deasa12.`object`.dataList.DataList
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

fun Fragment.emailVerifiDialog(url: String) {
    val bulder = AlertDialog.Builder(context)
    bulder.setTitle("Please confirm the mail")
    bulder.setPositiveButton("Ok") { dialog, i ->
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).also { (it) }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        dialog.dismiss()
        isEmailSent()
    }
    bulder.show()
}


fun Fragment.checkForInternet(context: Context): Boolean {

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

fun Fragment.isEmailSent(): Boolean {
    var emailSent = false
    FirebaseAuth.getInstance().currentUser?.sendEmailVerification()?.addOnCompleteListener {
        if (it.isSuccessful) {
            Toast.makeText(context, "The mail has been sent", Toast.LENGTH_SHORT).show()
            emailSent = true
        } else {
            Toast.makeText(context, "The mail has not sent", Toast.LENGTH_SHORT).show()
            emailSent = false
        }
    }
    return emailSent
}

fun Fragment.getUrl(email: String): String {
    var link = ""
    var index = email.indexOf('@')
    for (i in index + 1 until email.length) {
        link += email[i]
    }
    return "https://$link"
}

fun Fragment.isGoogle(res: Boolean) {
    val editor = Value.googlePref?.edit()?.putBoolean("isGoogle", res)?.apply()
}

fun Fragment.isTrueSong() {
    val mediaPlayer = MediaPlayer.create(context, R.raw._true)
    if (Value.sound)
        mediaPlayer.start()
}

fun Fragment.isFalesSong() {
    val mediaPlayer = MediaPlayer.create(context, R.raw._false)
    if (Value.sound)
        mediaPlayer.start()
}








