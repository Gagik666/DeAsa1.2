package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSetingsBinding
import com.google.firebase.auth.FirebaseAuth

class SetingsFragment : Fragment() {
    lateinit var binding: FragmentSetingsBinding
    private lateinit var mAuth: FirebaseAuth
   override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentSetingsBinding.inflate(inflater)
       return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    }
}

