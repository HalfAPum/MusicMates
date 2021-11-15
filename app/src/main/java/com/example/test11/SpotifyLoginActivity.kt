package com.example.test11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.android.synthetic.main.activity_main2.*

class SpotifyLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val builder = AuthorizationRequest.Builder(
            SPOTIFY_CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        )
        builder.setScopes(arrayOf("streaming"))
        val request = builder.build()

        fun performSignIn() {
            if(hasConnection().not()) {
                makeText("Отсутствует интернет")
                return
            }
            AuthorizationClient.openLoginActivity(this, CODE, request)
        }

        val editor = getPreferences(MODE_PRIVATE)
        if (editor.getBoolean(SAVE_LOGIN, false)) {
            performSignIn()
        }

        spotifyLogIn.setOnClickListener {
            performSignIn()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CODE) {
            val response = AuthorizationClient.getResponse(resultCode, data)
            when (response.type) {
                AuthorizationResponse.Type.TOKEN -> {
                    makeText("Авторизация упешна")
                    saveLogin()
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                    finish()
                }
                AuthorizationResponse.Type.ERROR -> makeText("Ошибка при авторизации")
                AuthorizationResponse.Type.CODE -> makeText("Неизвестный пользователь")
                AuthorizationResponse.Type.EMPTY -> makeText("Отсутствует пользователь")
                AuthorizationResponse.Type.UNKNOWN -> makeText("Неизвестный пользователь")
                else -> makeText("Ошибка при авторизации")
            }
        }
    }

    private fun saveLogin() {
        val editor = getPreferences(MODE_PRIVATE).edit()
        editor.putBoolean(SAVE_LOGIN, true)
        editor.apply()
    }

    companion object {
        private const val REDIRECT_URI = "https://nure-mates-backend.herokuapp.com/api/v1/callback"
        private const val CODE = 1
        private const val SAVE_LOGIN = "SAVE_SPOTIFY_LOGIN"
        private const val SPOTIFY_CLIENT_ID = "4b0c176906b1423daebb17c63a864bc6"
    }
}