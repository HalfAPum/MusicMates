package com.example.test11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.activity_room.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            WebService.loginApi.createRoom(
                WebService.token,
                CreateRequest(ROOM_ID, "TEMP", HOST_ID)
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
        if (createData?.isPrivate == false) {
            roomNavigation.visibility = View.GONE
        }

        update()

        addTreckB.setOnClickListener {
            WebService.loginApi.addTack(WebService.token, Track(addTreckED.text.toString()))
                .enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        if (response.code() == 200 || response.code() == 201) {
                            trackList.add(addTreckED.text.toString())
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                    }

                })
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
            WebService.loginApi.getUsersInRoom(WebService.token, ROOM_ID)
                .enqueue(object : Callback<UsersListResponse> {
                    override fun onResponse(
                        call: Call<UsersListResponse>,
                        response: Response<UsersListResponse>
                    ) {
                        supportActionBar?.title = response.body()?.users?.name
                        val list = response.body()?.users?.users ?: listOf()
                        uAdapter?.update(list.map { UserItem(it.email, it.id) })
                        usersCount.text = list.size.toString()

                        val ad = StringAdapter()
                        ad.update(response.body()?.users?.tracksss?.map { it.id.toString() + " " + it.tracks } ?: listOf())
                        musicList.adapter = ad

                        Handler(Looper.getMainLooper()).postDelayed({
                        update()}, 1000L)
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
                WebService.loginApi.updateHost(WebService.token, UpdateHost(ROOM_ID, dataList[mPosition].id))
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

    companion object {
        private const val HOST_ID = 5
        private var ROOM_ID = WebService.roomId
        var trackList = mutableListOf<String>()
    }
}