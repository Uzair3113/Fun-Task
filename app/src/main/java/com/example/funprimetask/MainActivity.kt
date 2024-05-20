package com.example.funprimetask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.funprimetask.databinding.ActivityMainBinding
import com.example.funprimetask.utils.AdsManager

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AdsManager(this)
        AdsManager.instance.loadInterstitial()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = binding.botNavViewMainActivity
        bottomNav.setupWithNavController(navController)
        binding.botNavViewMainActivity.visibility = View.GONE


        Handler(Looper.getMainLooper()).postDelayed({

            AdsManager.instance.showInterstitial(
                true,
                this,
                object : AdsManager.InterstitialAdListener {
                    override fun onAdClosed() {
                        navController.navigate(R.id.apiCallFragment)
                        binding.botNavViewMainActivity.visibility = View.VISIBLE
                    }

                })
        }, 5000)


    }
}