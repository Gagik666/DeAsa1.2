package com.example.deasa12.viewModel


import android.app.Application
import android.os.CountDownTimer
import android.widget.Toast

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class DeAsaViewModel(application: Application) : AndroidViewModel(application) {
    val liveDataTimer = MutableLiveData<String>()

init {
    timer()
}


    private fun timer() {
        object : CountDownTimer(Values.timer.toLong() * 1000, 1000) {
            override fun onTick(p0: Long) {
                liveDataTimer.value = (p0/1000).toString()
            }

            override fun onFinish() {
            }

        }.start()
    }


}