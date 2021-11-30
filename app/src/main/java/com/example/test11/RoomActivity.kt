package com.example.test11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_room.*
import kotlinx.android.synthetic.main.activity_room.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
        val uAdapter = UserAdapter()
        recyclerView.adapter = uAdapter
        registerForContextMenu(recyclerView)
        val createData = intent.extras?.getSerializable("CREATE_DATA") as? RoomSerializedData
        supportActionBar?.title = createData?.roomName
        fillType.text = if (createData?.isAutoFill == true) "AUTO" else "FIXED (${createData?.tracksCount})"
        roomAccessType.text = if (createData?.isPrivate == true) "PRIVATE" else "PUBLIC"
        usersMaxCount.text = "/" + createData?.maxMembers.toString()

        WebService.loginApi.createRoom(WebService.token, CreateRequest(ROOM_ID, "TEMP",HOST_ID))
            .enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
//                    if (response.code() == 200 || response.code() == 201) {
//                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("tag1","$t")
                }

            })

        Handler(Looper.getMainLooper()).postDelayed({
           WebService.loginApi.getUsersInRoom(WebService.token, ROOM_ID.toString())
               .enqueue(object : Callback<UsersListResponse> {
                   override fun onResponse(
                       call: Call<UsersListResponse>,
                       response: Response<UsersListResponse>
                   ) {
                       uAdapter.update((response.body()?.users?.users ?: listOf()).map { UserItem(it.email, it.id) })
                   }
                   override fun onFailure(call: Call<UsersListResponse>, t: Throwable){}
               })

        },1000L)
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
        private const val HOST_ID = 7
        private const val ROOM_ID = 1236
    }
}