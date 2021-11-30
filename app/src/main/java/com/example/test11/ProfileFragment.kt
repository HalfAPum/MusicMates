package com.example.test11

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.test11.MainActivity2.Companion.CODE
import com.example.test11.MainActivity2.Companion.SAVE_LOGIN
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.android.synthetic.main.activity_main2.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_main2, container, false)
    }

    override fun onStart() {
        super.onStart()
        val builder = AuthorizationRequest.Builder(
            SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        )
        builder.setScopes(arrayOf("streaming"))
        val request = builder.build()

        fun performSignIn() {
            if(requireActivity().hasConnection().not()) {
                requireActivity().makeText("Отсутствует интернет")
                return
            }
            AuthorizationClient.openLoginActivity(requireActivity(), CODE, request)
        }

//        val editor = requireActivity().getPreferences(MODE_PRIVATE)
//        if (editor.getBoolean(SAVE_LOGIN, false)) {
//            performSignIn()
//        }

        spotifyLogIn.setOnClickListener {
            performSignIn()
        }
    }

    companion object {
        private const val REDIRECT_URI = "https://nure-mates-backend.herokuapp.com/api/v1/callback"
        private const val SPOTIFY_CLIENT_ID = "4b0c176906b1423daebb17c63a864bc6"
    }
}