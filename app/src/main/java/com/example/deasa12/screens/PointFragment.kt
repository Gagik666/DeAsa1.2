package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.deasa12.Extensions.checkForInternet
import com.example.deasa12.Extensions.dialog
import com.example.deasa12.Extensions.openFragment
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentPointBinding
import com.example.deasa12.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth

class PointFragment : Fragment() {
    lateinit var binding: FragmentPointBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var team1name: String
    lateinit var team1point: String
    lateinit var team2name: String
    lateinit var team2point: String
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
        if (mAuth.currentUser != null) {
            FirebaseUtils().fireStoreDatabase.collection("Teams")
                .document(mAuth.currentUser!!.uid).get().addOnSuccessListener { querySnapshot ->
                    team1name = querySnapshot.data?.get("Team1Name").toString()
                    team1point = querySnapshot.data?.get("Team1Point").toString()
                    team2name = querySnapshot.data?.get("Team2Name").toString()
                    team2point = querySnapshot.data?.get("Team2Point").toString()

                    if (querySnapshot.data?.get("Team1Name") == null) {
                        binding.btnRefreshResults.text = "Add Data"
                        binding.tvShowhResult.visibility = View.INVISIBLE
                    } else {
                        binding.btnRefreshResults.text = "Refresh Data"
                    }

                    binding.tvTeam1NameOlden.text = team1name
                    binding.tvTeam1PointOlden.text = team1point
                    binding.tvTeam2NamaeOlden.text = team2name
                    binding.tvTeam2PointOlden.text = team2point
                }

        }

        binding.tvShowhResult.setOnClickListener {
            binding.tvTeam1NameOlden.visibility = View.VISIBLE
            binding.tvTeam1PointOlden.visibility = View.VISIBLE
            binding.tvTeam2NamaeOlden.visibility = View.VISIBLE
            binding.tvTeam2PointOlden.visibility = View.VISIBLE
        }


        val queue = DataList.queueList[0].queue.toString().toInt()
        if (queue == 4) {
            binding.btnPlay.visibility = View.INVISIBLE
            binding.btnAgain.visibility = View.VISIBLE
            if (mAuth.currentUser != null) {
                binding.btnRefreshResults.visibility = View.VISIBLE
                binding.tvShowhResult.visibility = View.VISIBLE
            }
        }
        binding.btnPlay.setOnClickListener {
            DataList.queueList[0].queue += 1
            Value.point = 0
                openFragment(R.id.action_pointFragment_to_deAsaStoageFragment)
        }

        binding.tvTeam1Point.text = DataList.teamList[0].point.toString()
        binding.tvTeam2Point.text = DataList.teamList[1].point.toString()
        binding.tvTeam1Name.text = DataList.teamList[0].team
        binding.tvTeam2Namae.text = DataList.teamList[1].team

        binding.btnRefreshResults.setOnClickListener {
            if (context?.let { it1 -> !checkForInternet(it1) } == true) {
                binding.progressBar.visibility = View.GONE
                dialog("There is not conection internet")
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
                            dialog("data updated")
                        }
                } else {
                    binding.progressBar.visibility = View.GONE
                    dialog("You are not Registered")
                }
            }

            binding.tvTeam1NameOlden.visibility = View.INVISIBLE
            binding.tvTeam1PointOlden.visibility = View.INVISIBLE
            binding.tvTeam2NamaeOlden.visibility = View.INVISIBLE
            binding.tvTeam2PointOlden.visibility = View.INVISIBLE

            binding.tvTeam1NameOlden.text = binding.tvTeam1Name.text
            binding.tvTeam1PointOlden.text = binding.tvTeam1Point.text
            binding.tvTeam2NamaeOlden.text = binding.tvTeam2Namae.text
            binding.tvTeam2PointOlden.text = binding.tvTeam2Point.text
        }



        binding.btnAgain.setOnClickListener {
            openFragment(R.id.action_pointFragment_to_startFragment)
            DataList.teamList[0].point = 0
            DataList.teamList[1].point = 0
            DataList.teamList[0].team = "Team 1"
            DataList.teamList[1].team = "Team 2"
            DataList.queueList[0].queue = 1
            DataList.tempList.clear()
            DataList.tempSongList.clear()
            Value.p = 0
            Value.p = 0
            Value.start = -4
            Value.end = 0
            Value.step = 0
            Value.point = 0
            DataList.listSingeer.clear()
        }

        if (Value.lisIsEmpty) {
            dialog("Sorry, the singers' names have expired")
            binding.btnAgain.visibility = View.VISIBLE
            binding.btnPlay.visibility = View.INVISIBLE
            binding.btnRefreshResults.visibility = View.VISIBLE
            binding.tvShowhResult.visibility = View.VISIBLE
        }

    }
}