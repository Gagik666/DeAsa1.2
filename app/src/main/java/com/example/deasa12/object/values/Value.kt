import android.content.SharedPreferences

object Value {
    const val IMAGE_URL = "https://firebasestorage.googleapis.com/v0/b/deasa12-431ef.appspot.com/o/users%2FprofilPic1653498247215.png?alt=media&token=79cb6286-e7a5-4d21-950e-53acb15aa231"
    var timer = 60
    var start = -4
    var end = 0
    var step = 0
    var p = 0
    var point = 0
    var lisIsEmpty = false
    var sound = false
    var googlePref: SharedPreferences? = null

}