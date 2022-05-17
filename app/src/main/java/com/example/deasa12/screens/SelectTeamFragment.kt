package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deasa10.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.TeamDialogFragment
import com.example.deasa12.adapters.TeamAdapter
import com.example.deasa12.databinding.FragmentSelectTeamBinding
import com.example.deasa12.models.Teams

class SelectTeamFragment : Fragment() {
    lateinit var binding: FragmentSelectTeamBinding
    lateinit var teamAdapter: TeamAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var list = mutableListOf<Teams>()
        var teamDialog = TeamDialogFragment()

        binding.rvTeams.layoutManager = LinearLayoutManager(this.context)

        teamAdapter = TeamAdapter(DataList.teamList) {

            if (it.id == 1) {
                findNavController().navigate(SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(it.team, 1))
            }
            if (it.id == 2) {
                findNavController().navigate(SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(it.firstPlayer, 2))
            }
            if (it.id == 3) {
                findNavController().navigate(SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(it.secondPlayer, 3))
            }

        }



//        teamViewModel.liveDataTeam.observe(viewLifecycleOwner, Observer {
//            binding.tvTeams.text = it.toString()
//        })

        binding.rvTeams.adapter = teamAdapter

//        teamViewModel.getTeams().observe(viewLifecycleOwner, Observer {
//            it.forEach {
//                list.add(
//                    Teams(it.id, it.team, it.firstPlayer, it.secondPlayer)
//                )
//            }
//        })

        binding.tvTeams.setOnClickListener {
            teamAdapter.notifyDataSetChanged()
        }

    }
}