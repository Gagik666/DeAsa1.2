package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deasa10.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.adapters.SingerAdapter
import com.example.deasa12.databinding.FragmentDeAsaStoageBinding
import kotlinx.coroutines.*

class DeAsaStoageFragment : Fragment() {
    lateinit var binding: FragmentDeAsaStoageBinding
    lateinit var singerAdapter: SingerAdapter
    var queue = 0
    var point = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDeAsaStoageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSingerTempList()
        binding.rvSinger.layoutManager = LinearLayoutManager(this.context)

        singerAdapter = SingerAdapter(DataList.tempList.toMutableSet().toMutableList()) {
            point++
            binding.tvPoint.text = "Point $point"
            if (queue == 1 || queue == 3) {
                binding.tvTeam.text = DataList.teamList[0].team
                DataList.teamList[0].point += 1
            } else {
                binding.tvTeam.text = DataList.teamList[1].team
                DataList.teamList[1].point += 1
            }
            var x = DataList.tempList.toMutableSet().toMutableList().size.toString().toInt()

            if (point.toString() == x.toString()) {
                Toast.makeText(context, "stacvec", Toast.LENGTH_SHORT).show()
            }
        }



        binding.rvSinger.adapter = singerAdapter


        GlobalScope.launch(Dispatchers.Default) {
            seconsd()
        }
        queue = DataList.queueList[0].queue
        if (queue == 1 || queue == 3) {
            binding.tvTeam.text = DataList.teamList[0].team
            DataList.teamList[0].point += point
        } else {
            binding.tvTeam.text = DataList.teamList[1].team
            DataList.teamList[1].point += point
        }
    }

    private suspend fun seconsd() {
        for (i in 3 downTo 0) {
            delay(1000)
            withContext(Dispatchers.Main) {
                binding.tvSeconds.text = i.toString()
                if (i == 0) {
                    findNavController().navigate(R.id.action_deAsaStoageFragment_to_pointFragment)
                }
                DataList.tempList.clear()
            }
        }
    }


    fun getSingerTempList(): MutableList<String> {
        for (i in 1..6) {
            val randomSinger = (0..19).random()
            DataList.tempList.add(DataList.singerList[randomSinger].toString())
        }
        return DataList.tempList
    }

}