package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deasa10.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSelectTeamBinding
import com.example.deasa12.screens.dialogScreens.TeamDialogFragment
import com.example.deasa12.screens.dialogScreens.TeamDialogFragmentArgs

class SelectTeamFragment : Fragment() {
    lateinit var binding: FragmentSelectTeamBinding
    private val args: TeamDialogFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvTeam1.text = DataList.teamList[0].team
            tvTeam2.text = DataList.teamList[1].team
            tvTeam1.setOnClickListener {
                findNavController().navigate(SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(
                    tvTeam1.text.toString(), 0
                ))
            }

            tvTeam2.setOnClickListener {
                findNavController().navigate(SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(
                    tvTeam2.text.toString(), 1
                ))
            }

            btnStart.setOnClickListener {
                findNavController().navigate(R.id.action_selectTeamFragment_to_deAsaStoageFragment)
            }

        }

        binding.tvTeams.setOnClickListener {
            findNavController().navigate(R.id.action_selectTeamFragment_to_teamDialogFragment)
        }

    }


}