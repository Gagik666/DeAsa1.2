package com.example.deasa12.screens.dialogScreens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentTeamDialogBinding

class TeamDialogFragment : DialogFragment() {
    lateinit var binding: FragmentTeamDialogBinding
    private val args: TeamDialogFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.tvName.text = args.name
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_teamDialogFragment_to_selectTeamFragment)
            if (args.id == 0) {
                if (binding.edName.text.isEmpty())
                    DataList.teamList[0].team = "Team 1"
                else
                    DataList.teamList[0].team = binding.edName.text.toString()
            }

            if (args.id == 1) {
                if (binding.edName.text.isEmpty())
                    DataList.teamList[1].team = "Team 2"
                else
                    DataList.teamList[1].team = binding.edName.text.toString()
            }
            dismiss()
        }
    }
}