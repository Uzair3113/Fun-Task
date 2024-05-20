package com.example.funprimetask

import android.app.Application
import com.example.funprimetask.utils.AdsManager

class App: Application() {
   override fun onCreate() {
      super.onCreate()
      AdsManager(this)
   }
}