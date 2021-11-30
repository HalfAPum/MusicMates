package com.example.test11

import android.content.Context
import android.widget.Toast
import android.net.ConnectivityManager

import android.net.NetworkInfo
import androidx.annotation.IdRes





fun Context.makeText(text: String) {
  Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.hasConnection(): Boolean {
  val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
  var wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
  if (wifiInfo != null && wifiInfo.isConnected) {
    return true
  }
  wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
  if (wifiInfo != null && wifiInfo.isConnected) {
    return true
  }
  wifiInfo = cm.activeNetworkInfo
  return wifiInfo != null && wifiInfo.isConnected
}