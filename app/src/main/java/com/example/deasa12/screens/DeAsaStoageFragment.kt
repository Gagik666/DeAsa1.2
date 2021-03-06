package com.example.deasa12.screens

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.recreate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deasa12.Extensions.openFragment
import com.example.deasa12.Extensions.song
import com.example.deasa12.Extensions.songOff
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.adapters.SingerAdapter
import com.example.deasa12.databinding.FragmentDeAsaStoageBinding
import com.example.deasa12.viewModel.DeAsaViewModel
import com.google.firebase.auth.FirebaseAuth


class DeAsaStoageFragment : Fragment() {
    lateinit var binding: FragmentDeAsaStoageBinding
    lateinit var singerAdapter: SingerAdapter
    lateinit var deAsaViewModel: DeAsaViewModel
    private lateinit var mAuth: FirebaseAuth

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
        mAuth = FirebaseAuth.getInstance()
        deAsaViewModel = ViewModelProvider(this).get(DeAsaViewModel::class.java)
        getSingerTempList()

        deAsaViewModel.liveDataTimer.observe(viewLifecycleOwner, Observer {
            binding.tvSeconds.text = it

            if (it.toString().toInt() < 4) {
                binding.tvSeconds.setTextColor(Color.parseColor("#AA0000"))

            } else {
                binding.tvSeconds.setTextColor(Color.parseColor("#38006b"))
            }

            if (it.toString().toInt() == 3) {
                song(R.raw.time_of)
            }

            if (it.toString().toInt() == 0) {
                openFragment(R.id.action_deAsaStoageFragment_to_pointFragment)
                DataList.tempList.clear()
                Value.lisIsEmpty = false
            }
        })
        queue = DataList.queueList[0].queue

        if (queue == 1 || queue == 3) {
            binding.tvTeam.text = DataList.teamList[0].team
            deAsaViewModel.liveDataPoint0.observe(viewLifecycleOwner, Observer {
                binding.tvTeam.text = DataList.teamList[0].team
                binding.tvPoint.text = "Point $it  "
            })
        } else {
            binding.tvTeam.text = DataList.teamList[1].team
            deAsaViewModel.liveDataPoint1.observe(viewLifecycleOwner, Observer {
                binding.tvTeam.text = DataList.teamList[1].team
                binding.tvPoint.text = "Point $it  "
            })
        }

        binding.rvSinger.layoutManager = LinearLayoutManager(this.context)
        x = DataList.tempList.size
        singerAdapter = SingerAdapter(DataList.tempList) {
            Value.p++
            if (DataList.listSingeer.size - Value.step <= 6) {
                openFragment(R.id.action_deAsaStoageFragment_to_pointFragment)
                Value.lisIsEmpty = true
            }
            if (it) {
                song(R.raw._true)
                point++
            } else {
                song(R.raw._false)
                point--
            }
            if (queue == 1 || queue == 3) {
                binding.tvTeam.text = DataList.teamList[0].team
                binding.tvPoint.text = "Point ${DataList.teamList[0].point + 1}  "
                deAsaViewModel.updatePoint0(it)
            } else {
                binding.tvTeam.text = DataList.teamList[1].team
                binding.tvPoint.text = "Point ${DataList.teamList[1].point + 1}  "
                deAsaViewModel.updatePoint1(it)
            }
            if (point == x) {
                recreate(context as Activity)
                DataList.tempList.clear()

            }
        }
        binding.rvSinger.adapter = singerAdapter
    }

    fun getSingerTempList() {
        Value.start += 5
        Value.end += 5
        for (i in Value.start..Value.end) {
            DataList.tempList.add(DataList.listSingeer[i])
            Value.step = i
        }

    }


}