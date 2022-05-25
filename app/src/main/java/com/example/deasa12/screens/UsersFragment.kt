package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deasa12.adapters.UsersAdapter
import com.example.deasa12.databinding.FragmentUsersBinding
import com.example.deasa12.models.UserModel
import com.example.deasa12.utils.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User

class UsersFragment : Fragment() {
    lateinit var binding: FragmentUsersBinding
    lateinit var usersAdapter: UsersAdapter
    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userList = mutableListOf<UserModel>()
        mAuth = FirebaseAuth.getInstance()



        binding.rvUsers.layoutManager = LinearLayoutManager(this.context)
        FirebaseUtils().fireStoreDatabase.collection("Rating").get()
            .addOnSuccessListener { Task ->
                Task.forEach {
                    userList.add(
                        UserModel(
                            it.data["firstName"].toString(),
                            it.data["lastName"].toString(),
                            it.data["rating"].toString().toInt(),
                            it.data["imgageId"].toString()
                        )
                    )
                }
                usersAdapter = UsersAdapter(userList) {

                }

                binding.rvUsers.adapter = usersAdapter
            }

    }
}