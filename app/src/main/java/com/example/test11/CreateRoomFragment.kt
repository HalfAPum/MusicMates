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
      if(requireActivity().hasConnection().not()) {
        requireActivity().makeText("Отсутствует интернет")
        return@setOnClickListener
      }
      if (!validateNumber(roomMembersCount.text.toString(),
          0..64, "WRONG ROOM MEMBERS COUNT")) {
        return@setOnClickListener
      }
      if (radioButton2.isChecked.not() &&
        !validateNumber(trackCountEditText.text.toString(),
          0..10, "WRONG TRACKS COUNT NUMBER")) {
        return@setOnClickListener
      }
      val intent = Intent(requireContext(), RoomActivity::class.java)
      val roomData = RoomSerializedData(
        roomName = if(roomNameEditText.text.toString().isBlank()) "New name 1" else roomNameEditText.text.toString(),
        isPrivate = radioButton1.isChecked,
        maxMembers = roomMembersCount.text.toString().toInt(),
      preferableMusic = "",
      isAutoFill = radioButton2.isChecked,
        tracksCount = if (trackCountEditText.text.toString().isBlank()) 0
          else trackCountEditText.text.toString().toInt(),
      )
      intent.putExtra("CREATE_DATA", roomData)
      startActivity(intent)
    }

  }

  private fun validateNumber(
    number: String,
    range: IntRange,
    errMsg: String
  ): Boolean {
    if (number.isBlank()) {
      context?.makeText(errMsg)
      return false
    }
    val result = (number.toInt() > range.first
            && number.toInt() < range.last)
    if (result.not()) context?.makeText(errMsg)
    return result
  }
}