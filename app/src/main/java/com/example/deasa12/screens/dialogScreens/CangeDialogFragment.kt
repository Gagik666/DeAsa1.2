package com.example.deasa12.screens.dialogScreens

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.deasa12.Extensions.isRegistred
import com.example.deasa12.Extensions.openFragment
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentCangeDialogBinding
import com.example.deasa12.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth

class CangeDialogFragment : DialogFragment() {
    lateinit var binding: FragmentCangeDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCangeDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnChange.setOnClickListener {
            if (isRegistred()) {
                FirebaseUtils().fireStoreDatabase.collection("users").document(FirebaseUtils().uid)
                    .get().addOnSuccessListener { Task ->
                        if (Task.data?.get("password")
                                .toString() == binding.edOldPassword.text.toString()
                        ) {
                            changPassword()
                        }
                    }
            }
        }
    }

    fun changPassword() {
        val hashMap = hashMapOf<String, Any>(
            "password" to binding.edNewPasswod.text.toString(),
        )
                FirebaseAuth.getInstance().currentUser?.updatePassword(binding.edNewPasswod.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(context, "Password Changed !", Toast.LENGTH_SHORT).show()
                    FirebaseUtils().fireStoreDatabase.collection("users").document(FirebaseUtils().uid)
                        .update(hashMap)
                    ActivityCompat.recreate(context as Activity)
                    dismiss()
                } else {
                    Toast.makeText(context, "Error !", Toast.LENGTH_SHORT).show()
                }
            }
    }


}

