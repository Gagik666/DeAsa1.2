package com.example.deasa12.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.deasa12.R
import com.example.deasa12.databinding.ItemTeamsBinding
import com.example.deasa12.models.Teams

class TeamAdapter(
    private val teamList: List<Teams>,
    private val onClickItem: (team: Teams) -> Unit
): RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    inner class TeamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ItemTeamsBinding.bind(itemView)

        fun bind(teams: Teams, index: Int) = with(binding) {
            tvTeam.text = teams.team
            tvPlayer1.text = teams.firstPlayer
            tvPlayer2.text = teams.secondPlayer


            tvTeam.setOnClickListener {
                teams.id = 1
                onClickItem.invoke(teams)

            }

            tvPlayer1.setOnClickListener {
                teams.id = 2
                onClickItem.invoke(teams)
            }

            tvPlayer2.setOnClickListener {
                teams.id = 3
                onClickItem.invoke(teams)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TeamViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_teams, parent, false)
    )

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(teamList[position], position)
    }

    override fun getItemCount() = teamList.size



}