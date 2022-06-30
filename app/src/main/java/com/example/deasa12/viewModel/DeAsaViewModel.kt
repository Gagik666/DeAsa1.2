package com.example.deasa12.viewModel


import android.app.Application
import android.os.CountDownTimer

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.deasa12.`object`.dataList.DataList


class DeAsaViewModel(application: Application) : AndroidViewModel(application) {
    val liveDataTimer = MutableLiveData<String>()
    val liveDataPoint0 = MutableLiveData<Int>()
    val liveDataPoint1 = MutableLiveData<Int>()


    init {
        timer()
    }




    private fun timer() {
        object : CountDownTimer(Value.timer.toLong() * 1000, 1000) {
            override fun onTick(p0: Long) {
                liveDataTimer.value = (p0 / 1000).toString()
            }

            override fun onFinish() {
            }

        }.start()
    }



    fun updatePoint0(p0: Boolean) {
        if (p0) DataList.teamList[0].point += 1 else DataList.teamList[0].point -= 1
        liveDataPoint0.value = DataList.teamList[0].point
    }

    fun updatePoint1(p1: Boolean) {

        if (p1) DataList.teamList[1].point += 1 else DataList.teamList[1].point -= 1
        liveDataPoint1.value = DataList.teamList[1].point

    }




}