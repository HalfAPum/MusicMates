package com.example.test11

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class LoginActivity : AppCompatActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        oneTapClient = Identity.getSignInClient(this)
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(CLIENT_ID)
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)
                    .build())
            // Automatically sign in when exactly one credential is retrieved.
            .setAutoSelectEnabled(true)
            .build()

        val editor = getPreferences(MODE_PRIVATE)
        if (editor.getBoolean(SAVE_LOGIN, false)) {
            performSignIn()
        }

        signInButton.setOnClickListener {
            performSignIn()
        }
    }

    private fun performSignIn() {
        if(hasConnection().not()) {
            makeText("Отсутствует интернет")
            return
        }
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(this) { result ->
                try {
                    startIntentSenderForResult(
                        result.pendingIntent.intentSender, CODE,
                        null, 0, 0, 0, null
                    )
                } catch (e: IntentSender.SendIntentException) {
                    Log.e(TAG, "Couldn't start One Tap UI: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(this) { e ->
                // No saved credentials found. Launch the One Tap sign-up flow, or
                // do nothing and continue presenting the signed-out UI.
                Log.d(TAG, e.localizedMessage)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let { data ->
            if (requestCode == CODE) {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    if (credential.googleIdToken != null) {
                        WebService.loginApi.validateToken(LoginRequest(credential.googleIdToken!!))
                            .enqueue(object : Callback<LoginResponse> {
                                override fun onResponse(
                                    call: Call<LoginResponse>,
                                    response: Response<LoginResponse>
                                ) {
                                    when (response.code()) {
                                        500 -> makeText("Ошибка сервера")
                                        400 -> makeText("Ошибка клиента")
                                        200 -> {
                                            makeText("Авторизация успешна")
                                            saveLogin()
                                            login()
                                        }
                                        201 -> {
                                            makeText("Поздравляем, вы успешно зарегестрировались")
                                            login()
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                    Log.d(TAG, "$t")
                                }

                            })
                    }
                } catch (e: Exception) {}
            }
        }
    }

    private fun saveLogin() {
        val editor = getPreferences(MODE_PRIVATE).edit()
        editor.putBoolean(SAVE_LOGIN, true)
        editor.apply()
    }

    private fun login() {
        val intent = Intent(this@LoginActivity, SpotifyLoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val CODE = 1
        const val TAG = "tag1"
        const val SAVE_LOGIN = "SAVE_LOGIN"
        private const val CLIENT_ID = "729737132048-ibi4jv8pcehjuf7en408k7emqh84uu0o.apps.googleusercontent.com"
    }
}