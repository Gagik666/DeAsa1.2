package com.example.deasa10.dataList

import com.example.deasa12.models.Queue
import com.example.deasa12.models.Teams


object DataList {
    val teamList = mutableListOf(
        Teams(0, "Team 1", 0),
        Teams(1, "Team 2", 0)
    )

    val tempList = mutableListOf<String>()

    //    val teamPointList = mutableListOf<Point>()
    val queueList = mutableListOf<Queue>(
        Queue(1)
    )

    val singerList =
        mutableListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 12)
}