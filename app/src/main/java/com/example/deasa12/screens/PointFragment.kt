package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentPointBinding

class PointFragment : Fragment() {
    lateinit var binding: FragmentPointBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val queue = DataList.queueList[0].queue.toString().toInt()
        if (queue == 4) {
            binding.btnPlay.visibility = View.INVISIBLE
            binding.btnNext.visibility = View.VISIBLE
        }
        binding.btnPlay.setOnClickListener {
            DataList.queueList[0].queue += 1
            findNavController().navigate(R.id.action_pointFragment_to_deAsaStoageFragment)
        }

        binding.tvTeam1Point.text = DataList.teamList[0].point.toString()
        binding.tvTeam2Point.text = DataList.teamList[1].point.toString()
    }



}