package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.example.deasa12.Extensions.dialog
import com.example.deasa12.Extensions.isRegistred
import com.example.deasa12.Extensions.openFragment
import com.example.deasa12.R
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.database.database.SingerInfo
import com.example.deasa12.databinding.FragmentStartBinding
import com.example.deasa12.utils.FirebaseUtils
import com.example.studentapp.database.UserDatabase
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class StartFragment : Fragment() {
    lateinit var binding: FragmentStartBinding
    private lateinit var mAuth: FirebaseAuth
    lateinit var dataFirstName: String
    lateinit var dataLastName: String
    lateinit var dataLastImgUrl: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isRegistred()) {
            if (!FirebaseAuth.getInstance().currentUser?.isEmailVerified!!) {
                openFragment(R.id.action_startFragment_to_logInFragment)
            }
        }

        val db = UserDatabase.getDatabase(context?.applicationContext!!)
        mAuth = FirebaseAuth.getInstance()
        binding.apply {
            imgMenu.setOnClickListener {
                binding.drawerLayout.openDrawer((GravityCompat.START))
            }


            nvMenu.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemSetings -> openFragment(R.id.action_startFragment_to_setingsFragment)
                    R.id.itemRating -> {
                        if (isRegistred()) {
                            openFragment(R.id.action_startFragment_to_ratingFragment)
                        } else {
                            dialog("You are not registered")
                        }
                    }
                    R.id.itemUsers -> {
                        if (isRegistred()) {
                            openFragment(R.id.action_startFragment_to_usersFragment)
                        } else {
                            dialog("You are not registered")
                        }
                    }
                    R.id.itemHelp -> openFragment(R.id.action_startFragment_to_helpFragment)
                }
                true
            }
        }

        val navMenuHeader = binding.nvMenu.getHeaderView(0)
        val name = navMenuHeader.findViewById<TextView>(R.id.tvUser)
        val img = navMenuHeader.findViewById<ImageView>(R.id.imgProfilHeader)


        if (isRegistred()) {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .document(mAuth.currentUser!!.uid).get()
                .addOnSuccessListener { querySnapshot ->
                    dataFirstName = querySnapshot.data?.get("firstName").toString()
                    dataLastName = querySnapshot.data?.get("lastName").toString()
                    dataLastImgUrl = querySnapshot.data?.get("imgageId").toString()

                    if (Value.googlePref?.getBoolean("isGoogle", false) == true) {
                        name.text = mAuth.currentUser?.displayName.toString()
                        Picasso.get().load(mAuth.currentUser?.photoUrl).into(img)
                    } else {
                        name.text = "$dataFirstName $dataLastName"
                        Picasso.get().load(dataLastImgUrl).into(img)
                    }
                }
        }

        FirebaseUtils().fireStoreDatabase.collection("Help").document("help").get()
            .addOnSuccessListener { Task ->
                for (i in 1..Task.data?.size!!) {
                    DataList.helpList.add(Task.data?.get("$i").toString())
                }

            }

        binding.btnPlayInTeams.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.userDao().getAll().forEach {
                    DataList.listSingeer.add(it.name)
                }
            }
            openFragment(R.id.action_startFragment_to_selectTeamFragment)
        }

        FirebaseUtils().fireStoreDatabase.collection("Singers")
            .document("fByI386z9nPFY19rdTuU").get()
            .addOnSuccessListener { Task ->

                CoroutineScope(Dispatchers.IO).launch {
                    Task.data?.forEach {
                        if (db.userDao().isNotExists(it.value.toString())) {
                            db.userDao().insertData(
                                SingerInfo(it.value.toString())
                            )
                        }
                    }
                }

            }
    }


}

