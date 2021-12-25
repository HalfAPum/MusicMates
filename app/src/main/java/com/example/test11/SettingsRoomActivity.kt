package com.example.test11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_settings_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_room)
        update_settings.setOnClickListener {
            val fixed = if (trackCountEditText.text.toString().isNullOrBlank().not()) trackCountEditText.text.toString().toInt()
                else 0
            val maxMembbrs = if (roomMembersCount.text.toString().isNullOrBlank().not()) roomMembersCount.text.toString().toInt()
                else 8
            WebService.loginApi.updateRoom(WebService.token, UpdateRequest(
                WebService.roomId, roomNameEditText.text.toString(), radioButton.isChecked,
                radioButton3.isChecked.not(), fixed, maxMembbrs
            )
            ).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    Log.d("tag1 ", "tag1 RESP ${response.code()}")
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                }
            })
        }
        radioGroup1.setOnCheckedChangeListener { _, checkedId ->
            trackCountEditText.visibility =  when(checkedId) {
                R.id.radioButton2 -> View.GONE
                R.id.radioButton3 -> View.VISIBLE
                else -> View.GONE
            }
        }
        settingsNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.room_action -> {
                    onBackPressed()
                }
                R.id.settings -> {}
            }
            true
        }

        WebService.loginApi.getUsersInRoom(WebService.token, WebService.roomId)
            .enqueue(object : Callback<UsersListResponse> {
                override fun onResponse(
                    call: Call<UsersListResponse>,
                    response: Response<UsersListResponse>
                ) {
                    roomNameEditText.setText(response.body()?.users?.name)

                    RoomActivity.privateToken = if (response.body()?.users?.privateAccessToken.isNullOrBlank()){
                        "This is public room)"
                    } else response.body()?.users?.privateAccessToken!!
                    privatTokenAcc.text = RoomActivity.privateToken

                    if (response.body()?.users?.isPublic == true) radioButton.isChecked = true
                    if (response.body()?.users?.isAuto == false) radioButton3.isChecked = true
                    trackCountEditText.setText(response.body()?.users?.fixed.toString())
                    roomMembersCount.setText(response.body()?.users?.maxUsers.toString())
                }
                override fun onFailure(call: Call<UsersListResponse>, t: Throwable){}
            })
    }

    override fun onResume() {
        super.onResume()
        settingsNavigation.selectedItemId = R.id.settings
    }
}