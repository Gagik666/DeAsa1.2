package com.example.deasa12.`object`.dataList

import com.example.deasa12.R
import com.example.deasa12.database.database.SingerInfo
import com.example.deasa12.models.Queue
import com.example.deasa12.models.SongMdel
import com.example.deasa12.models.Teams


object DataList {
    val teamList = mutableListOf(
        Teams(0, "Team 1", 0),
        Teams(1, "Team 2", 0)
    )

    val tempList = mutableListOf<String>()
    val tempSongList = mutableListOf<SongMdel>()

    val helpList = mutableSetOf<String>()
    val queueList = mutableListOf(
        Queue(1, 1)
    )

    var listSingeer = mutableListOf<String>()

}
