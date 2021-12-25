package com.example.test11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.activity_room.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RoomActivity : AppCompatActivity() {

    var uAdapter: UserAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        uAdapter = UserAdapter()
        recyclerView.adapter = uAdapter
        registerForContextMenu(recyclerView)
        val createData = intent.extras?.getSerializable("CREATE_DATA") as? RoomSerializedData
        supportActionBar?.title = createData?.roomName
        fillType.text =
            if (createData?.isAutoFill == true) "AUTO" else "FIXED (${createData?.tracksCount})"
        roomAccessType.text = if (createData?.isPrivate == true) "PRIVATE" else "PUBLIC"
        usersMaxCount.text = "/" + createData?.maxMembers.toString()
        if (createData?.roomName != null) {
            uAdapter?.isHost = true
            WebService.roomId = Random().nextInt(100000) + 10000
            WebService.loginApi.createRoom(
                WebService.token,
                CreateRequest(WebService.roomId, createData.roomName, HOST_ID, createData.isPrivate.not(),
                    createData.isAutoFill, createData.tracksCount, createData.maxMembers
                )
            )
                .enqueue(object : Callback<String> {
                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                    }

                })
        } else {
            uAdapter?.isHost = false
            val state = View.GONE
            textView4.visibility = state
            textView3.visibility = state
            textView5.visibility = state
            roomAccessType.visibility = state
            usersCount.visibility = state
            usersMaxCount.visibility = state
            fillType.visibility = state
            roomNavigation.visibility = state
        }
        update()

        addTreckB.setOnClickListener {
            var track = addTreckED.text.toString()
            if (track.startsWith("https://open.spotify.com/track/")) {

                track = track.substring(31).substringBefore("?")

                WebService.spotifyApi.getTrackInfo("Bearer ${WebService.spotifyToken}", track).enqueue(object : Callback<SpotifyResponse> {
                    override fun onResponse(call: Call<SpotifyResponse>, response: Response<SpotifyResponse>) {
                        track = response.body()?.name ?: "Err parsing name"
                        WebService.loginApi.addTack(
                            WebService.token,
                            Track(track, WebService.roomId)
                        )
                            .enqueue(object : Callback<String> {
                                override fun onResponse(call: Call<String>, response: Response<String>) {
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                }

                            })
                        trackList.add(track)
                        addTreckED.text = null
                    }
                    override fun onFailure(call: Call<SpotifyResponse>, t: Throwable) {
                    }
                })
            } else makeText("WRONG LINK")
        }

        roomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.room_action -> {}
                R.id.settings -> {
                    startActivity(Intent(this, SettingsRoomActivity::class.java))
                }
            }
            true
        }

    }

    override fun onResume() {
        super.onResume()
        roomNavigation.selectedItemId = R.id.room_action
    }

    private fun update() {
        Handler(Looper.getMainLooper()).postDelayed({
            WebService.loginApi.getUsersInRoom(WebService.token, WebService.roomId)
                .enqueue(object : Callback<UsersListResponse> {
                    override fun onResponse(
                        call: Call<UsersListResponse>,
                        response: Response<UsersListResponse>
                    ) {
                        supportActionBar?.title = response.body()?.users?.name
                        val list = response.body()?.users?.users ?: listOf()
                        uAdapter?.update(list.map { UserItem(it.email, it.id) }.toSet().toList())
                        usersCount.text = list.size.toString()

                        val ad = StringAdapter()
                        ad.update(response.body()?.users?.tracksss?.map { it.tracks } ?: listOf())
                        musicList.adapter = ad
                        privateToken = if (response.body()?.users?.privateAccessToken.isNullOrBlank()){
                            "This is public room)"
                        } else response.body()?.users?.privateAccessToken!!
                        roomAccessType.text = if (response.body()?.users?.isPublic == true) "PUBLIC" else "PRIVATE"
                        Handler(Looper.getMainLooper()).postDelayed({
                        update()}, 1000L)
                        fillType.text =
                            if (response.body()?.users?.isAuto == true) "AUTO" else "FIXED (${response.body()?.users?.fixed})"
                        supportActionBar?.title = response.body()?.users?.name
                        usersMaxCount.text = "/" + response.body()?.users?.maxUsers
                    }
                    override fun onFailure(call: Call<UsersListResponse>, t: Throwable){}
                })

        },1000L)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.myTrackList) {
            startActivity(Intent(this, TrackListActivity::class.java))
        }
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        with (recyclerView.adapter as UserAdapter) {
            if (item.itemId == UserAdapter.CHANGE_HOST_ID &&
                    dataList[mPosition].id != HOST_ID) {
                WebService.loginApi.updateHost(WebService.token, UpdateHost(WebService.roomId, dataList[mPosition].id))
                    .enqueue(object : Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {}
                        override fun onFailure(call: Call<String>, t: Throwable) {}
                    })
                isHost = false
                notifyDataSetChanged()
                makeText("Вы больше не хост")
            }else makeText("Вы уже хост")
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        trackList.clear()
    }

    companion object {
        private var HOST_ID = WebService.userId
        var trackList = mutableListOf<String>()
        var privateToken = ""
    }
}