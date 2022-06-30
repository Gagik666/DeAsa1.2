package com.example.deasa12.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.deasa12.Extensions.openFragment
import com.example.deasa12.`object`.dataList.DataList
import com.example.deasa12.R
import com.example.deasa12.databinding.FragmentSelectTeamBinding
import com.example.deasa12.screens.dialogScreens.TeamDialogFragmentArgs
import com.example.studentapp.database.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectTeamFragment : Fragment() {
    lateinit var binding: FragmentSelectTeamBinding
    private val args: TeamDialogFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSelectTeamBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tvTeam1.text = DataList.teamList[0].team
            tvTeam2.text = DataList.teamList[1].team
            tvTeam1.setOnClickListener {
                findNavController().navigate(
                    SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(
                        tvTeam1.text.toString(), 0
                    )
                )
            }

            tvTeam2.setOnClickListener {
                findNavController().navigate(
                    SelectTeamFragmentDirections.actionSelectTeamFragmentToTeamDialogFragment(
                        tvTeam2.text.toString(), 1
                    )
                )
            }
            val db = UserDatabase.getDatabase(context?.applicationContext!!)

            binding.tvTeams.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    db.userDao().deleteAll()
                }
                    Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show()


            }

            btnStartSinger.setOnClickListener {
                DataList.listSingeer.shuffle()
                DataList.tempList.clear()
                when (binding.rdGrupSetings.checkedRadioButtonId) {
                    R.id.rdBtn60Sec -> Value.timer = 10
                    R.id.rdBtn90sec -> Value.timer = 60
                    R.id.rdBtn120sec -> Value.timer = 75
                }
                DataList.queueList[0].stage = 0
                openFragment(R.id.action_selectTeamFragment_to_deAsaStoageFragment)
            }

        }

    }
}