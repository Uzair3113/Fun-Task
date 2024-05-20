package com.example.funprimetask.fragments

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.funprimetask.databinding.FragmentSplashBinding
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.material.color.utilities.MaterialDynamicColors.background


class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adLoader = context?.let {
            AdLoader.Builder(it, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd { nativeAd ->
                    val template = binding.nativeAdView
                    template.setNativeAd(nativeAd)
                }
                .build()
        }

        adLoader?.loadAd(AdRequest.Builder().build())

    }


}