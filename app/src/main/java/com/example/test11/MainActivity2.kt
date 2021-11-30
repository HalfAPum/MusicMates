package com.example.test11

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import kotlinx.android.synthetic.main.activity_main3.*

class MainActivity2 : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main3)

    initViews()
  }

  protected fun initViews() {
    val findRoomFragment = FindRoomFragment()
    val createRoomFragment = CreateRoomFragment()
    val profile = ProfileFragment()

    makeCurrentFragment(findRoomFragment)

    navigation.setOnNavigationItemSelectedListener {
      val fragment = when(it.itemId) {
        R.id.action_find_room -> findRoomFragment
        R.id.action_create_room -> createRoomFragment
        R.id.action_profile -> profile
        else -> findRoomFragment
      }
      makeCurrentFragment(fragment)
      true
    }
  }

  private fun makeCurrentFragment(fragment: Fragment) =
    supportFragmentManager.beginTransaction().apply {
      replace(R.id.fragment_container, fragment)
      commit()
    }


  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)

    if (requestCode == CODE) {
      val response = AuthorizationClient.getResponse(resultCode, data)
      when (response.type) {
        AuthorizationResponse.Type.TOKEN -> {
          makeText("Авторизация упешна")
//          saveLogin()
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

 fun saveLogin() {
    val editor = getPreferences(MODE_PRIVATE).edit()
    editor.putBoolean(SAVE_LOGIN, true)
    editor.apply()
  }

  companion object {
    const val CODE = 1
    const val SAVE_LOGIN = "SAVE_SPOTIFY_LOGIN"
  }
}