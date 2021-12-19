package com.example.test11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_private_room_enter.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivateRoomEnter : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_private_room_enter, container, false)
    }

    override fun onStart() {
        super.onStart()
        goPrivate.setOnClickListener {
            val privateToken = privateroomTokenEdittext.text
            WebService.loginApi.joinPrivateRoom(WebService.token, privateToken.toString()).enqueue(
                object : Callback<UsersList> {
                    override fun onResponse(call: Call<UsersList>, response: Response<UsersList>) {
                        Log.d("tag1", "WWWWWWww ${response.body()}")
                        WebService.roomId = response.body()?.id ?: 0
                        startActivity(Intent(requireActivity(), RoomActivity::class.java))
                    }

                    override fun onFailure(call: Call<UsersList>, t: Throwable) {
                    }
                }
            )
        }
    }

    companion object {

        fun newInstance() =
            PrivateRoomEnter()
    }
}