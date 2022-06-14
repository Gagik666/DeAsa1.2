package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.deasa12.Extensions.openFragment
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSelectTeamBinding
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
                findNavController().navigate(
                    SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(
                        tvTeam1.text.toString(), 0
                    )
                )
            }

            tvTeam2.setOnClickListener {
                findNavController().navigate(
                    SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(
                        tvTeam2.text.toString(), 1
                    )
                )
            }

            btnStart.setOnClickListener {
                DataList.listSingeer.shuffle()
                DataList.tempList.clear()
                when (binding.rdGrupSetings.checkedRadioButtonId) {
                    R.id.rdBtn60Sec -> Values.timer = 45
                    R.id.rdBtn90sec -> Values.timer = 60
                    R.id.rdBtn120sec -> Values.timer = 75
                }
                openFragment(R.id.action_selectTeamFragment_to_deAsaStoageFragment)
            }

        }

        binding.tvTeams.setOnClickListener {
            openFragment(R.id.action_selectTeamFragment_to_teamDialogFragment)
        }
    }
}