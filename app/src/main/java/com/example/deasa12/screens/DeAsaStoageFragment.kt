package com.example.deasa12.screens

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.adapters.SingerAdapter
import com.example.deasa12.databinding.FragmentDeAsaStoageBinding
import com.example.deasa12.viewModel.DeAsaViewModel
import kotlinx.coroutines.*

class DeAsaStoageFragment : Fragment() {
    lateinit var binding: FragmentDeAsaStoageBinding
    lateinit var singerAdapter: SingerAdapter
    lateinit var deAsaViewModel: DeAsaViewModel
    var queue = 0
    var point = 0
    var x = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeAsaStoageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deAsaViewModel = ViewModelProvider(this).get(DeAsaViewModel::class.java)
        getSingerTempList()
        deAsaViewModel.liveDataTimer.observe(viewLifecycleOwner, Observer {
            binding.tvSeconds.text = it
            if (it.toString().toInt() == 0) {
                findNavController().navigate(R.id.action_deAsaStoageFragment_to_pointFragment)
                DataList.tempList.clear()
            }
        })

        queue = DataList.queueList[0].queue
        if (queue == 1 || queue == 3) {
            binding.tvTeam.text = DataList.teamList[0].team
            binding.tvPoint.text = "Point ${DataList.teamList[0].point}"
            DataList.teamList[0].point += point
        } else {
            binding.tvTeam.text = DataList.teamList[1].team
            binding.tvPoint.text = "Point ${DataList.teamList[1].point}"
            DataList.teamList[1].point += point
        }

        binding.rvSinger.layoutManager = LinearLayoutManager(this.context)
        x = DataList.tempList.toMutableSet().toMutableList().size
        singerAdapter = SingerAdapter(DataList.tempList.toMutableSet().toMutableList()) {
            if (it) point++ else point--
            binding.tvPoint.text = "Point $point  "
            if (queue == 1 || queue == 3) {
                binding.tvTeam.text = DataList.teamList[0].team
                binding.tvPoint.text = "Point ${DataList.teamList[0].point + 1}"
                if (it) DataList.teamList[0].point += 1 else DataList.teamList[0].point -= 1
            } else {
                binding.tvTeam.text = DataList.teamList[1].team
                binding.tvPoint.text = "Point ${DataList.teamList[1].point + 1}"
                if (it) DataList.teamList[1].point += 1 else DataList.teamList[1].point -= 1
            }
            if (point == x) {
                recreate(context as Activity)
                DataList.tempList.clear()
            }
        }
        binding.rvSinger.adapter = singerAdapter
    }
    fun getSingerTempList(): MutableList<String> {
        for (i in 1..5) {
            val randomSinger = (0..76).random()
            DataList.tempList.add(DataList.singerList[randomSinger])
        }
        return DataList.tempList
    }



}