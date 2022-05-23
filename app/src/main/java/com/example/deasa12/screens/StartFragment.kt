package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentStartBinding
import com.example.deasa12.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth

class StartFragment : Fragment() {
    lateinit var binding: FragmentStartBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var dataFirstName: String
    lateinit var dataLastName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding.apply {
            imgMenu.setOnClickListener {
                binding.drawerLayout.openDrawer((GravityCompat.START))
            }

            nvMenu.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemSetings -> findNavController().navigate(R.id.action_startFragment_to_setingsFragment)
                    R.id.itemRating -> findNavController().navigate(R.id.action_startFragment_to_ratingFragment)
                    R.id.itemUsers -> findNavController().navigate(R.id.action_startFragment_to_usersFragment)
                    R.id.itemHelp -> findNavController().navigate(R.id.action_startFragment_to_helpFragment)
                    R.id.itemProfil -> findNavController().navigate(R.id.action_startFragment_to_profilFragment)
                }

                true
            }
        }

        val navMenuHeader = binding.nvMenu.getHeaderView(0)
        val name = navMenuHeader.findViewById<TextView>(R.id.tvUser)
        if (mAuth.currentUser != null) {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .document(mAuth.currentUser!!.uid).get()
                .addOnSuccessListener { querySnapshot ->
                    dataFirstName = querySnapshot.data?.get("firstName").toString()
                    dataLastName = querySnapshot.data?.get("lastName").toString()
                    name.text = "$dataFirstName $dataLastName"
                }
        } else {
            name.text = "There is no user"
        }

        binding.btnPlayInTeams.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_selectTeamFragment)
        }

    }

}

