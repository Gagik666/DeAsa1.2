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
        val mediaControlerHelp = MediaController(context)

//        binding.tvAbout.append("Կարող եք ․․․\n")
//        binding.tvAbout.append(" .Գրանցվել պրոյեկտում\n")
//        binding.tvAbout.append(" .գանհատել պրոյեկտը\n")
//        binding.tvAbout.append(" .տեսնել ուրիշների գնահատականները\n")
//        binding.tvAbout.append(" .եթե գրանցվել եք հնարավորություն ունեք պահպանել խաղի արդյունքները նախորդի հետ համեմատելու համար\n")

        DataList.helpList.forEach {
            binding.tvAbout.append("$it \n")
        }

        binding.apply {
            mediaControlerHelp.setAnchorView(vvHelp)
            val onlineUrl =
                Uri.parse("https://firebasestorage.googleapis.com/v0/b/deasa12-431ef.appspot.com/o/video%2FhelpVido.mp4?alt=media&token=2778c168-16eb-46ba-a1b6-d1c2d1e824d8")
            vvHelp.setMediaController(mediaControlerHelp)
            vvHelp.setVideoURI(onlineUrl)
            vvHelp.requestFocus()
            vvHelp.start()
        }
    }
}