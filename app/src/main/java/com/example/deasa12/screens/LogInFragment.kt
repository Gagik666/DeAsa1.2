package com.example.deasa12.screens


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.deasa12.Extensions.*
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class LogInFragment : Fragment() {
    lateinit var binding: FragmentLogInBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var launcher: ActivityResultLauncher<Intent>
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
                        if (FirebaseAuth.getInstance().currentUser?.isEmailVerified!!) {
                            openFragment(R.id.action_logInFragment_to_startFragment)
                        } else {
                            emailVerifiDialog(getUrl("https://${binding.edEmail.text}"))
                        }

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

        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthwithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Toast.makeText(context, "Api Exception", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imgGoogle.setOnClickListener {
            if (Value.googlePref?.getBoolean("isGoogle", false) == false) {
                isGoogle(true)
            }
            signInWithGoogle()
        }
    }

    private fun getClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(activity!!, gso)
    }

    private fun signInWithGoogle() {
        val signInClient = getClient()
        launcher.launch(signInClient.signInIntent)
    }

    private fun firebaseAuthwithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Registration", Toast.LENGTH_SHORT).show()
                openFragment(R.id.action_logInFragment_to_startFragment)
            } else {
                Toast.makeText(context, "Error Registration", Toast.LENGTH_SHORT).show()
            }
        }
    }
}