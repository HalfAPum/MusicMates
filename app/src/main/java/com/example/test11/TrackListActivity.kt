package com.example.test11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_track_list.*

class TrackListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_list)
        val adapter = StringAdapter()
        adapter.update(RoomActivity.trackList)
        myTrackslist.adapter = adapter
        myTrackslist.layoutManager = LinearLayoutManager(this)
    }
}