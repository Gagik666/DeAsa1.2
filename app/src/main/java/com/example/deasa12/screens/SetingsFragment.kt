package com.example.deasa12.screens

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.findNavController
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSetingsBinding
import com.google.android.gms.tasks.Continuation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class SetingsFragment : Fragment() {
    lateinit var binding: FragmentSetingsBinding
    private lateinit var mAuth: FirebaseAuth

    lateinit var imgProfil: ImageView
     var intervalClick = false
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentSetingsBinding.inflate(inflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgProfil = binding.imgProfil

        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) {
            binding.apply {
                flowLogOut.visibility = View.VISIBLE
                flowLogIn.visibility = View.INVISIBLE
            }
        } else {
            binding.apply {
                flowLogOut.visibility = View.INVISIBLE
                flowLogIn.visibility = View.VISIBLE
            }
        }


        binding.flowLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_setingsFragment_to_startFragment)
        }

        binding.flowLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_setingsFragment_to_logInFragment)
        }

        binding.btnSave.setOnClickListener {
            val rdBtnGrup = binding.rdGrupSetings.checkedRadioButtonId
            when(rdBtnGrup) {
                R.id.rdBtn60Sec -> Values.timer = 60
                R.id.rdBtn90sec -> Values.timer = 90
                R.id.rdBtn120sec -> Values.timer = 120
            }
            findNavController().navigate(R.id.action_setingsFragment_to_startFragment)
        }

        binding.tvInterval.setOnClickListener {
            if (!intervalClick) {
                intervalClick = true
                binding.rdGrupSetings.visibility = View.VISIBLE
            } else {
                intervalClick = false
                binding.rdGrupSetings.visibility = View.GONE
            }
        }

        binding.tvSetings.setOnClickListener {
            getImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null && data.data != null) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(context, "stacvec ${data.data}", Toast.LENGTH_SHORT).show()
                binding.imgProfil.setImageURI(data.data)
            }
        }
    }




    fun getImage() {
        val intentChuser  = Intent()
        intentChuser.type = "image/*"
        intentChuser.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intentChuser, 1)
    }

}





