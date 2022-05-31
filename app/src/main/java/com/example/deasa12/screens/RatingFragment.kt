package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.deasa12.R
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.database.database.SingerInfo
import com.example.deasa12.databinding.FragmentRatingBinding
import com.example.deasa12.utils.FirebaseUtils
import com.example.studentapp.database.UserDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*


class RatingFragment : Fragment() {

    lateinit var binding: FragmentRatingBinding
    private lateinit var mAuth: FirebaseAuth
    var rating = 0
    lateinit var dataFirstName: String
    lateinit var dataLastName: String
    lateinit var imgId: String

    lateinit var size: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val tstList = mutableListOf<SingerInfo>(
            SingerInfo("a1"),
            SingerInfo("a2"),
            SingerInfo("a3"),
            SingerInfo("a4"),
            SingerInfo("a5"),
            SingerInfo("a6")
        )
        val tstList2 = mutableListOf<SingerInfo>()

        binding.apply {
            imgStar1.setOnClickListener {
                imgStar1.setImageResource(R.drawable.ic_activ_star)
                imgStar2.setImageResource(R.drawable.ic_star)
                imgStar3.setImageResource(R.drawable.ic_star)
                imgStar4.setImageResource(R.drawable.ic_star)
                imgStar5.setImageResource(R.drawable.ic_star)
                rating = 1
            }

            imgStar2.setOnClickListener {
                imgStar1.setImageResource(R.drawable.ic_activ_star)
                imgStar2.setImageResource(R.drawable.ic_activ_star)
                imgStar3.setImageResource(R.drawable.ic_star)
                imgStar4.setImageResource(R.drawable.ic_star)
                imgStar5.setImageResource(R.drawable.ic_star)
                rating = 2
            }

            imgStar3.setOnClickListener {
                imgStar1.setImageResource(R.drawable.ic_activ_star)
                imgStar2.setImageResource(R.drawable.ic_activ_star)
                imgStar3.setImageResource(R.drawable.ic_activ_star)
                imgStar4.setImageResource(R.drawable.ic_star)
                imgStar5.setImageResource(R.drawable.ic_star)
                rating = 3
            }

            imgStar4.setOnClickListener {
                imgStar1.setImageResource(R.drawable.ic_activ_star)
                imgStar2.setImageResource(R.drawable.ic_activ_star)
                imgStar3.setImageResource(R.drawable.ic_activ_star)
                imgStar4.setImageResource(R.drawable.ic_activ_star)
                imgStar5.setImageResource(R.drawable.ic_star)
                rating = 4
            }

            imgStar5.setOnClickListener {
                imgStar1.setImageResource(R.drawable.ic_activ_star)
                imgStar2.setImageResource(R.drawable.ic_activ_star)
                imgStar3.setImageResource(R.drawable.ic_activ_star)
                imgStar4.setImageResource(R.drawable.ic_activ_star)
                imgStar5.setImageResource(R.drawable.ic_activ_star)
                rating = 5
            }

            btnSave.setOnClickListener {

                progressBar.visibility = View.VISIBLE
                val hashMap = hashMapOf<String, Any>(
                    "firstName" to dataFirstName,
                    "lastName" to dataLastName,
                    "imgageId" to imgId,
                    "rating" to rating

                )

                if (mAuth.currentUser != null) {
                    FirebaseUtils().fireStoreDatabase.collection("Rating")
                        .document(mAuth.currentUser!!.uid).set(hashMap).addOnSuccessListener {
                            progressBar.visibility = View.VISIBLE
                            Toast.makeText(context, "add data fireStore", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_ratingFragment_to_startFragment)
                        }
                }
            }


            if (mAuth.currentUser != null) {
                FirebaseUtils().fireStoreDatabase.collection("users")
                    .document(mAuth.currentUser!!.uid).get()
                    .addOnSuccessListener { querySnapshot ->
                        dataFirstName = querySnapshot.data?.get("firstName").toString()
                        dataLastName = querySnapshot.data?.get("lastName").toString()
                        imgId = querySnapshot.data?.get("imgageId").toString()
                    }
            }

        }

    }


}

