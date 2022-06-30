package com.example.deasa12.utils

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class FirebaseUtils {
    val fireStoreDatabase = FirebaseFirestore.getInstance()
    val isVerifi = FirebaseAuth.getInstance().currentUser
    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val mStaorageRef = FirebaseStorage.getInstance().reference
}