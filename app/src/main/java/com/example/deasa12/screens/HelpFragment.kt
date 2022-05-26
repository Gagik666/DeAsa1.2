package com.example.deasa12.screens

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import com.example.deasa12.R
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
        val mediaControlerHelp = MediaController(context)
        if (Values.vidoHelp) {
            binding.apply {
                mediaControlerHelp.setAnchorView(vvHelp)
                val onlineUrl = Uri.parse("https://firebasestorage.googleapis.com/v0/b/deasa12-431ef.appspot.com/o/video%2FhelpVido.mp4?alt=media&token=6b065a46-3132-4259-8b69-9067485c7f852121")
                vvHelp.setMediaController(mediaControlerHelp)
                vvHelp.setVideoURI(onlineUrl)
                vvHelp.requestFocus()
                vvHelp.start()
            }
        }


    }


}