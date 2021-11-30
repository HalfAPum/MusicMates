package com.example.test11

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_create_room.*

class CreateRoomFragment : Fragment() {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_create_room, container, false)
  }

  override fun onStart() {
    super.onStart()
    radioGroup1.setOnCheckedChangeListener { _, checkedId ->
      trackCountEditText.visibility =  when(checkedId) {
        R.id.radioButton2 -> View.GONE
        R.id.radioButton3 -> View.VISIBLE
        else -> View.GONE
      }
    }

    createButton.setOnClickListener {
      val intent = Intent(requireContext(), RoomActivity::class.java)
      val roomData = RoomSerializedData(
        roomName = if(roomNameEditText.text.toString().isBlank()) "New name 1" else roomNameEditText.text.toString(),
        isPrivate = radioButton1.isChecked,
        maxMembers = if(roomMembersCount.text.toString().isBlank()) 32
        else if (roomMembersCount.text.toString().toInt() > 64) 64
      else if (roomMembersCount.text.toString().toInt() < 8) 8
      else roomMembersCount.text.toString().toInt(),
      preferableMusic = "",
      isAutoFill = radioButton2.isChecked,
        tracksCount = if(trackCountEditText.text.toString().isBlank()) 5
        else if (trackCountEditText.text.toString().toInt() > 64) 10
        else if (trackCountEditText.text.toString().toInt() < 8) 2
        else trackCountEditText.text.toString().toInt(),
      )
      intent.putExtra("CREATE_DATA", roomData)
      startActivity(intent)
    }

  }
}