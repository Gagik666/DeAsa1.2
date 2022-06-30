package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.deasa12.Extensions.*
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationFragment : Fragment() {
    lateinit var binding: FragmentRegistrationBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvSignUp.setOnClickListener {
            isEmailSent()
        }
        mAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseFirestore.getInstance()
        binding.btnRegistration.setOnClickListener {
            if (context?.let { it1 -> checkForInternet(it1) } == true) {
                if (binding.edFirstName.text.toString().isEmpty()) {
                    binding.edFirstName.error = ("firstName is empty")
                } else if (binding.edLastName.text.toString().isEmpty()) {
                    binding.edLastName.error = ("lastName is empty")
                } else if (!isValidEmail(binding.edEmail.text.toString())) {
                    binding.edEmail.error = ("email is invalide")
                } else if (binding.edPassword.text.toString().isEmpty()) {
                    binding.edPassword.error = ("password is empty")
                } else {
                    binding.progressBar.visibility = View.VISIBLE
                    mAuth.createUserWithEmailAndPassword(
                        binding.edEmail.text.toString(),
                        binding.edPassword.text.toString()
                    )
                        .addOnCompleteListener { Task ->
                            if (Task.isSuccessful) {
                                isEmailSent()
                                binding.progressBar.visibility = View.GONE
                                openFragment(R.id.action_registrationFragment_to_logInFragment)
                                val firbaseUser = FirebaseAuth.getInstance().currentUser!!.uid
                                val hashMap = hashMapOf<String, Any>(
                                    "firstName" to binding.edFirstName.text.toString(),
                                    "lastName" to binding.edLastName.text.toString(),
                                    "email" to binding.edEmail.text.toString(),
                                    "password" to binding.edPassword.text.toString(),
                                    "imgageId" to Value.IMAGE_URL
                                )
                                firebaseDatabase.collection("users").document(firbaseUser)
                                    .set(hashMap)
                                    .addOnSuccessListener {
                                        emailVerifiDialog(getUrl("https://${binding.edEmail.text}"))
                                    }
                            } else {
                                dialog("There is such a user")
                                binding.progressBar.visibility = View.GONE
                            }
                        }
                }
            } else {
                dialog("There is not conection internet")
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.tvLogIn.setOnClickListener {
            openFragment(R.id.action_registrationFragment_to_logInFragment)
        }


    }




}

