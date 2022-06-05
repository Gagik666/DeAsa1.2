package com.example.deasa12.screens



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.deasa12.Extensions.checkForInternet
import com.example.deasa12.Extensions.dialog
import com.example.deasa12.Extensions.isValidEmail
import com.example.deasa12.Extensions.openFragment
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
                binding.edEmail.requestFocus()
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
                        openFragment(R.id.action_logInFragment_to_startFragment)
                    } else {
                        binding.progressBar.visibility = View.GONE
                        if (context?.let { it1 -> !checkForInternet(it1) } == true) {
                            dialog("There is not conection internet")
                        } else if (!Task.isSuccessful) {
                            dialog("There is not user")

                        }
                    }
                }
            }
        }
        binding.tvRegistration.setOnClickListener {
            openFragment(R.id.action_logInFragment_to_registrationFragment)

        }
    }
}