package com.example.deasa12.screens

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentLogInBinding
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {
    lateinit var binding: FragmentLogInBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLogInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        binding.btnLogIn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            if (!isValidEmail(binding.edEmail.text.toString())) {
                binding.progressBar.visibility = View.GONE
                binding.edEmail.error = ("email is invalide")
            } else if (binding.edPassword.text.toString().isEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.edPassword.error = ("password is empty")
            } else {
                mAuth.signInWithEmailAndPassword(
                    binding.edEmail.text.toString(),
                    binding.edPassword.text.toString()
                ).addOnCompleteListener { Task ->
                    if (Task.isSuccessful) {
                        binding.progressBar.visibility = View.GONE
                        binding.tveror.visibility = View.GONE
                        findNavController().navigate(R.id.action_logInFragment_to_startFragment)
                    } else {
                        binding.tveror.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        binding.tvRegistration.setOnClickListener {
            findNavController().navigate(R.id.action_logInFragment_to_registrationFragment)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}