package com.example.deasa12.screens

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSetingsBinding
import com.example.deasa12.utils.FirebaseUtils
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
            getPhotoUrl()
//            getrVideUri()
        }


        binding.flowLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            findNavController().navigate(R.id.action_setingsFragment_to_startFragment)
        }

        binding.flowLogIn.setOnClickListener {
            findNavController().navigate(R.id.action_setingsFragment_to_logInFragment)
        }

        binding.imgAddphoto.setOnClickListener {
            if (mAuth.currentUser != null) {
                getImage()
//                getVideo()
            } else {
                erorDialog("You are not registered")
            }
        }

        if (mAuth.currentUser != null) {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .document(mAuth.currentUser!!.uid).get()
                .addOnSuccessListener { querySnapshot ->
                    dataFirstName = querySnapshot.data?.get("firstName").toString()
                    dataLastName = querySnapshot.data?.get("lastName").toString()
                    imgId = querySnapshot.data?.get("imgageId").toString()
                    binding.tvSetingsEmail.text = querySnapshot.data?.get("email").toString()
                    binding.tvSetingsPassword.text = querySnapshot.data?.get("password").toString()
                    binding.tvUserName.text = "$dataFirstName $dataLastName"
                    Picasso.get().load(imgId).into(binding.imgProfil)
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

    fun erorDialog(title: String) {
        val bulder = AlertDialog.Builder(context)
        bulder.setTitle(title)
        bulder.setPositiveButton("Ok") { dialog, i -> }
        bulder.show()
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
            binding.tvSetingsEmail.visibility = View.VISIBLE
            binding.tvSetingsPassword.visibility = View.VISIBLE
        } else {
            isClick = false
            binding.imgMore.setImageResource(R.drawable.ic_more)
            binding.tvSetingsEmail.visibility = View.INVISIBLE
            binding.tvSetingsPassword.visibility = View.INVISIBLE
        }
    }
}







