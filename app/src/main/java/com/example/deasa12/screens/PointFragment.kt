package com.example.deasa12.screens

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentPointBinding
import com.example.deasa12.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth

class PointFragment : Fragment() {
    lateinit var binding: FragmentPointBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val queue = DataList.queueList[0].queue.toString().toInt()
        if (queue == 4) {
            binding.btnPlay.visibility = View.INVISIBLE
            binding.btnAgain.visibility = View.VISIBLE
            binding.btnSaveResults.visibility = View.VISIBLE
            binding.tvSaveResult.visibility = View.VISIBLE
        }
        binding.btnPlay.setOnClickListener {
            DataList.queueList[0].queue += 1
            DataList.singerList.shuffle()
            findNavController().navigate(R.id.action_pointFragment_to_deAsaStoageFragment)
        }

        binding.tvTeam1Point.text = DataList.teamList[0].point.toString()
        binding.tvTeam2Point.text = DataList.teamList[1].point.toString()
        binding.tvTeam1Name.text = DataList.teamList[0].team
        binding.tvTeam2Nmae.text = DataList.teamList[1].team

        binding.btnSaveResults.setOnClickListener {
            if (context?.let { it1 -> !Funs.checkForInternet(it1) } == true) {
                binding.progressBar.visibility = View.GONE
                erorDialog("There is not conection internet")
            } else {
                binding.progressBar.visibility = View.VISIBLE
                val hashMap = hashMapOf<String, Any>(
                    "Team1Name" to DataList.teamList[0].team,
                    "Team1Point" to DataList.teamList[0].point,
                    "Team2Name" to DataList.teamList[1].team,
                    "Team2Point" to DataList.teamList[1].point
                )

                if (mAuth.currentUser != null) {
                    FirebaseUtils().fireStoreDatabase.collection("Teams")
                        .document(mAuth.currentUser!!.uid).set(hashMap).addOnSuccessListener {
                            binding.progressBar.visibility = View.GONE
                            erorDialog("data saved")
                        }
                } else {
                    binding.progressBar.visibility = View.GONE
                    erorDialog("You are not Registered")
                }
            }
        }



        binding.btnAgain.setOnClickListener {
            findNavController().navigate(R.id.action_pointFragment_to_startFragment)
            DataList.teamList[0].point = 0
            DataList.teamList[1].point = 0
            DataList.teamList[0].team = "Team 1"
            DataList.teamList[1].team = "Team 2"
            DataList.queueList[0].queue = 1
        }

    }

    fun erorDialog(title: String) {
        val bulder = AlertDialog.Builder(context)
        bulder.setTitle(title)
        bulder.setPositiveButton("Ok") { dialog, i -> }
        bulder.show()
    }


}