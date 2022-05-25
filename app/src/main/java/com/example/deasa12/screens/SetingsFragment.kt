package com.example.deasa12.screens

import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSetingsBinding
import com.example.deasa12.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class SetingsFragment : Fragment() {
    lateinit var binding: FragmentSetingsBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var imgId: String
    lateinit var imgProfil: ImageView
    lateinit var upLoadUri: Uri
    lateinit var dataFirstName: String
    lateinit var dataLastName: String

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



        binding.btnSave.setOnClickListener {
            val downLoadUrlTask =
                FirebaseUtils().mStaorageRef.child("users/profilPic${System.currentTimeMillis()}.png").downloadUrl
            downLoadUrlTask.addOnSuccessListener {
                Toast.makeText(context, "error downLoad", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                imgId = it.toString()
                binding.tvSetings.text = imgId
                Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
            }

            val updateHasMap = hashMapOf<String, Any>(
                "firstName" to imgId,
            )

            FirebaseUtils().fireStoreDatabase.collection("users").document(FirebaseUtils().uid)
                .update(updateHasMap).addOnSuccessListener {

                    Toast.makeText(context, "stacvec", Toast.LENGTH_SHORT).show()
                }
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
            } else {
                erorDialog("You are not registered")
            }
        }

        binding.imgProfil.setOnClickListener {
            upLoadImage(upLoadUri)
        }

        if (mAuth.currentUser != null) {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .document(mAuth.currentUser!!.uid).get()
                .addOnSuccessListener { querySnapshot ->
                    dataFirstName = querySnapshot.data?.get("firstName").toString()
                    dataLastName = querySnapshot.data?.get("lastName").toString()
                    imgId = querySnapshot.data?.get("imgageId").toString()
                    binding.tvUserName.text = "$dataFirstName $dataLastName"
                    binding.tvSetings.text = imgId
                    Picasso.get().load(imgId).into(binding.imgProfil);
                }
        } else {
            binding.tvUserName.text = "There is no user"
        }
    }

    fun upLoadImage(imageUri: Uri) {

        val imageFileName = "users/profilPic${System.currentTimeMillis()}.png"
        val upLoadTask = FirebaseUtils().mStaorageRef.child(imageFileName).putFile(imageUri)
        upLoadTask.addOnSuccessListener {
            Toast.makeText(context, "stacvrc", Toast.LENGTH_SHORT).show()


        }.addOnFailureListener {
            Toast.makeText(context, "eror", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && data != null && data.data != null) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(context, "stacvec ${data.data}", Toast.LENGTH_SHORT).show()

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

}







