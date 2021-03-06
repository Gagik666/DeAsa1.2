package com.example.deasa12.screens

import Value
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.deasa12.Extensions.dialog
import com.example.deasa12.Extensions.isGoogle
import com.example.deasa12.Extensions.openFragment
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSetingsBinding
import com.example.deasa12.utils.FirebaseUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class SetingsFragment : Fragment() {
    lateinit var binding: FragmentSetingsBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var imgId: String
    lateinit var upLoadUri: Uri
    lateinit var dataFirstName: String
    lateinit var dataLastName: String
    var isClick = false
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
        binding.switchSound.isChecked = Value.sound
        binding.imgMore.setOnClickListener {
            clickMore()
        }

        if (mAuth.currentUser != null) {
            binding.apply {
                flowLogOut.visibility = View.VISIBLE
                flowLogIn.visibility = View.INVISIBLE
                imgMore.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                flowLogOut.visibility = View.INVISIBLE
                flowLogIn.visibility = View.VISIBLE
                imgMore.visibility = View.INVISIBLE
            }
        }


        binding.btnSave.setOnClickListener {
            binding.btnSave.visibility = View.INVISIBLE
            getPhotoUrl()
        }


        binding.flowLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            GoogleSignIn.getClient(
                requireContext(),
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            ).signOut()



            if (Value.googlePref?.getBoolean("isGoogle", false) == true) {
                isGoogle(false)
            }
            openFragment(R.id.action_setingsFragment_to_startFragment)
        }

        binding.tvSetingsPassword.setOnClickListener {
            openFragment(R.id.action_setingsFragment_to_cangeDialogFragment)
        }

        binding.flowLogIn.setOnClickListener {
            openFragment(R.id.action_setingsFragment_to_logInFragment)
        }

        binding.imgAddphoto.setOnClickListener {
            if (mAuth.currentUser != null) {
                binding.btnSave.visibility = View.VISIBLE
                getImage()
            } else {
                dialog("You are not registered")
            }
        }

        binding.switchSound.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if (onSwitch) {
                Value.sound = true
                binding.switchSound.isChecked = true
            }else {
                Value.sound = false
                binding.switchSound.isChecked = false
            }



        }

        if (mAuth.currentUser != null) {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .document(mAuth.currentUser!!.uid).get()
                .addOnSuccessListener { querySnapshot ->
                    dataFirstName = querySnapshot.data?.get("firstName").toString()
                    dataLastName = querySnapshot.data?.get("lastName").toString()
                    imgId = querySnapshot.data?.get("imgageId").toString()
                    if (Value.googlePref?.getBoolean("isGoogle", false) == true) {
                        binding.imgMore.visibility = View.INVISIBLE
                    } else {
                        binding.tvSetingsEmail.text = querySnapshot.data?.get("email").toString()
                        binding.tvSetingsPassword.text = querySnapshot.data?.get("password").toString()
                    }


                    if (Value.googlePref?.getBoolean("isGoogle", false) == true) {
                        Picasso.get().load(mAuth.currentUser?.photoUrl).into(binding.imgProfil)
                        binding.tvUserName.text = mAuth.currentUser?.displayName
                    } else {
                        Picasso.get().load(imgId).into(binding.imgProfil)
                        binding.tvUserName.text = "$dataFirstName $dataLastName"
                    }

                }
        } else {
            binding.tvUserName.text = "There is no user"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null && data.data != null) {
            if (resultCode == RESULT_OK) {
                upLoadUri = data.data!!
                binding.imgProfil.setImageURI(upLoadUri)
            }
        }

        if (requestCode == 2 && data != null && data.data != null) {
            if (resultCode == RESULT_OK) {
                upLoadUri = data.data!!
                binding.imgProfil.setImageURI(upLoadUri)
            }
        }
    }


    fun getImage() {
        val intentChuser = Intent()
        intentChuser.type = "image/*"
        intentChuser.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intentChuser, 1)
    }


    fun getPhotoUrl() {
        binding.progressBar.visibility = View.VISIBLE
        val imageFileName = "users/profilPic${System.currentTimeMillis()}.png"
        val upLoadTask = FirebaseUtils().mStaorageRef.child(imageFileName)
        upLoadTask.putFile(upLoadUri).addOnCompleteListener { Task1 ->
            if (Task1.isSuccessful) {
                upLoadTask.downloadUrl.addOnCompleteListener { Task2 ->
                    if (Task2.isSuccessful) {
                        val photoUrl = Task2.result.toString()
                        val updateHasMap = hashMapOf<String, Any>(
                            "imgageId" to photoUrl,
                        )
                        FirebaseUtils().fireStoreDatabase.collection("users")
                            .document(FirebaseUtils().uid)
                            .update(updateHasMap).addOnSuccessListener {
                                binding.progressBar.visibility = View.GONE
                            }
                    }
                }
            }
        }
    }

    fun clickMore() {
        if (!isClick) {
            isClick = true
            binding.imgMore.setImageResource(R.drawable.ic_more_close)
            binding.layoutAcaunt.visibility = View.VISIBLE

        } else {
            isClick = false
            binding.imgMore.setImageResource(R.drawable.ic_more)
            binding.layoutAcaunt.visibility = View.INVISIBLE

        }
    }
}







