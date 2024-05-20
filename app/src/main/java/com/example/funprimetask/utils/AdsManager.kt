package com.example.funprimetask.utils

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.widget.FrameLayout
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd

class AdsManager(val context: Context) {

    private val TAG = "AdManagerAds"
    var adView: AdView? = null

    fun getAdSize(ad_view_container: FrameLayout, activity: Activity): AdSize {

        val display = activity?.windowManager?.defaultDisplay
        val outMetrics = DisplayMetrics()
        display?.getMetrics(outMetrics)

        val density = outMetrics.density

        var adWidthPixels = ad_view_container.width.toFloat()
        if (adWidthPixels == 0f) {
            adWidthPixels = outMetrics.widthPixels.toFloat()
        }

        val adWidth = (adWidthPixels / density).toInt()
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, adWidth)
    }

    fun loadBannerAd(
        adLayout: FrameLayout?, activity: Activity, listener: AdmobBannerAdListener? = null
    ): AdView? {

        if (adLayout == null) return null

        adLayout.removeAllViews()

        val adRequest = AdRequest.Builder().build()
        adView = mContext?.let { AdView(it) }
        adView!!.adUnitId = "ca-app-pub-3940256099942544/9214589741"

        var adSize = getAdSize(adLayout, activity)
        adView!!.setAdSize(adSize)
        adLayout.addView(adView)

        adView!!.loadAd(adRequest)
        adView!!.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.e(TAG, "${activity.localClassName} AdManager Banner Ad Loaded")
                listener?.onAdLoaded()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                Log.e(TAG, "AdManager Banner Ad Failed To Load")
                super.onAdFailedToLoad(p0)
                listener?.onAdFailed()
            }
        }
        return adView
    }


    fun loadInterstitial() {
        InterstitialAd.load(context,
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    mInterstitialAd = ad
//                    EventBus.getDefault().post(AdInterstitialModel(true))
                    Log.e(TAG, "AdManager Interstitial Ad Loaded")
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    mInterstitialAd = null
//                    EventBus.getDefault().post(AdInterstitialModel(true))
                    Log.e(TAG, "AdManager Interstitial Ad Failed Load")
                }
            })
    }

    fun showInterstitial(re: Boolean = true, activity: Activity?, listener: InterstitialAdListener) {

        reload = re

        if (activity == null) {
            listener.onAdClosed()
            return
        }

        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.e(TAG, "AdManager Interstitial Ad On Dismissed Full Screen Content")
                    mInterstitialAd = null
                    isInterstitialShowing = false
                    listener.onAdClosed()
                    if (reload) loadInterstitial()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                    Log.e(TAG, "AdManager Interstitial Ad Failed On Dismissed Full Screen Content")
                    mInterstitialAd = null
                    isInterstitialShowing = false

                }

                override fun onAdShowedFullScreenContent() {
                    Log.e(TAG, "AdManager Interstitial Ad On Ad Showed Full Screen Content")
                    mInterstitialAd = null
                    isInterstitialShowing = true
                }
            }
            mInterstitialAd!!.show(activity)
            Log.e(TAG, "AdManager Interstitial Ad Showed")
        } else {
            Log.e(TAG, "AdManager Interstitial Ad Failed")
            listener?.onAdClosed()
        }
    }

    interface InterstitialAdListener {
        fun onAdClosed()
    }

    public interface AdmobBannerAdListener {
        fun onAdLoaded()
        fun onAdFailed()
    }

    companion object {
        public var reload = true
        public var mInterstitialAd: InterstitialAd? = null
        var adsManagerInstance: AdsManager? = null
        private var mContext: Context? = null
        var isInterstitialShowing = false

        val instance: AdsManager
            get() {
                if (adsManagerInstance == null) {
                    adsManagerInstance = AdsManager(mContext!!)
                }
                return adsManagerInstance!!
            }
    }

    init {
        if (mContext == null) {
            mContext = context
        }
    }
}