package com.example.test11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings_room.*

class SettingsRoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_room)
        privatTokenAcc.text = RoomActivity.privateToken
        settingsNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.room_action -> {
                    onBackPressed()
                }
                R.id.settings -> {}
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        settingsNavigation.selectedItemId = R.id.settings
    }
}