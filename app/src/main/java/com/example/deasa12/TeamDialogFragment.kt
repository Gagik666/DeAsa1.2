package com.example.deasa12

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.deasa10.dataList.DataList
import com.example.deasa12.databinding.FragmentTeamDialogBinding

class TeamDialogFragment : DialogFragment()  {
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


        binding.tvName.text = args.name


            if (args.id == 2) {
                DataList.teamList.forEach {
                    if(it.firstPlayer == binding.tvName.text) {
                        it.firstPlayer = binding.edName.text.toString()
                    }
                }
            }

            if (args.id == 3) {
                DataList.teamList.forEach {
                    if(it.secondPlayer == binding.tvName.text) {
                        it.secondPlayer = binding.edName.text.toString()
                    }
                }
            }
            dismiss()
        }


    }
