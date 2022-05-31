package com.example.deasa12.screens

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.findNavController
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
        val db = UserDatabase.getDatabase(context?.applicationContext!!)
        mAuth = FirebaseAuth.getInstance()
        binding.apply {
            imgMenu.setOnClickListener {
                binding.drawerLayout.openDrawer((GravityCompat.START))
            }


            nvMenu.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemSetings -> findNavController().navigate(R.id.action_startFragment_to_setingsFragment)
                    R.id.itemRating -> {
                        if (mAuth.currentUser != null) {
                            findNavController().navigate(R.id.action_startFragment_to_ratingFragment)
                        } else {
                            erorDialog("You are not registered")
                        }
                    }
                    R.id.itemUsers -> {
                        if (mAuth.currentUser != null) {
                            findNavController().navigate(R.id.action_startFragment_to_usersFragment)
                        } else {
                            erorDialog("You are not registered")
                        }
                    }
                    R.id.itemHelp -> findNavController().navigate(R.id.action_startFragment_to_helpFragment)
                }

                true
            }
        }

        val navMenuHeader = binding.nvMenu.getHeaderView(0)
        val name = navMenuHeader.findViewById<TextView>(R.id.tvUser)
        val img = navMenuHeader.findViewById<ImageView>(R.id.imgProfilHeader)
        if (mAuth.currentUser != null) {
            FirebaseUtils().fireStoreDatabase.collection("users")
                .document(mAuth.currentUser!!.uid).get()
                .addOnSuccessListener { querySnapshot ->
                    dataFirstName = querySnapshot.data?.get("firstName").toString()
                    dataLastName = querySnapshot.data?.get("lastName").toString()
                    dataLastImgUrl = querySnapshot.data?.get("imgageId").toString()
                    Picasso.get().load(dataLastImgUrl).into(img)
                    name.text = "$dataFirstName $dataLastName"
                }
        } else {
            name.text = "There is no user"
        }

        binding.btnPlayInTeams.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_selectTeamFragment)
        }



            FirebaseUtils().fireStoreDatabase.collection("Singers")
                .document("fByI386z9nPFY19rdTuU").get()
                .addOnSuccessListener { Task ->
//                    for (i in 1..Task.data?.size!!) {
////                        DataList.listSingeer.add(Task.data!!["$i"].toString())
//                        db.userDao().insertData(
//                            SingerInfo(Task.data[i])
//                        )
//                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        Task.data?.forEach {
                            db.userDao().insertData(
                                SingerInfo(it.value.toString())
                            )
                        }

                        db.userDao().getAll().forEach {
                            DataList.listSingeer.add(it.name)
                        }
                    }


                }
    }

    fun erorDialog(title: String) {
        val bulder = AlertDialog.Builder(context)
        bulder.setTitle(title)
        bulder.setPositiveButton("Ok") { dialog, i -> }
        bulder.show()
    }

}

