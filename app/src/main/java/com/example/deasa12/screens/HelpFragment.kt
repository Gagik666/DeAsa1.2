package com.example.deasa12.screens

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import com.example.deasa12.R
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {
    lateinit var binding: FragmentHelpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        DataList.helpList.forEach {
            binding.tvAbout.append("$it \n")
        }

    }
}