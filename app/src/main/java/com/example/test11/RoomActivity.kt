package com.example.test11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.activity_room.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        val createData = intent.extras?.getSerializable("CREATE_DATA") as? RoomSerializedData
        supportActionBar?.title = createData?.roomName
        fillType.text = if (createData?.isAutoFill == true) "AUTO" else "FIXED"
        roomAccessType.text = if (createData?.isPrivate == true) "PRIVATE" else "PUBLIC"
        usersMaxCount.text = "/" + createData?.maxMembers.toString()
        WebService.loginApi.createRoom(WebService.token, CreateRequest("TEMP",HOST_ID))
            .enqueue(object : Callback<Response<String>> {
                override fun onResponse(
                    call: Call<Response<String>>,
                    response: Response<Response<String>>
                ) {
                    Log.d("tag1"," values WTFfff ${response.code()}")
                }

                override fun onFailure(call: Call<Response<String>>, t: Throwable) {
                    Log.d("tag1","$t")
                }

            })
    }

    companion object {
        private const val ROOM_ID = "1"
        private const val HOST_ID = 7
    }
}