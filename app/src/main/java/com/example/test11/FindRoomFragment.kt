package com.example.test11

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_find_room.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindRoomFragment : Fragment() {

  private var list1 = listOf<PublicRooms>()

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_find_room, container, false)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WebService.loginApi.getRooms(WebService.token).enqueue(object: Callback<List<PublicRooms>>{
      override fun onResponse(call: Call<List<PublicRooms>>, response: Response<List<PublicRooms>>) {
        list1 = response.body() ?: listOf()
        val list = response.body()?.map { it.name } ?: listOf()
        val adapter = StringAdapter()
        adapter.update(list)
        publicRoomsList.adapter = adapter
        adapter.onClick = {
          //todo change user_id to real
          WebService.loginApi.addUser(WebService.token, AddUser(5, list1[it].id)).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
            }

          })
          WebService.roomId = list1[it].id
          startActivity(Intent(requireActivity(), RoomActivity::class.java))
        }
      }

      override fun onFailure(call: Call<List<PublicRooms>>, t: Throwable) {}

    })
  }
}