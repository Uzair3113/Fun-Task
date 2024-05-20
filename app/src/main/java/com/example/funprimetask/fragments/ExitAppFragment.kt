package com.example.funprimetask.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.funprimetask.R
import com.example.funprimetask.databinding.FragmentExitAppBinding
import com.example.funprimetask.utils.AdsManager


class ExitAppFragment : Fragment() {

    private lateinit var binding: FragmentExitAppBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentExitAppBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGoBack.setOnClickListener {
            AdsManager.instance.showInterstitial(true, requireActivity(), object :AdsManager.InterstitialAdListener{
                override fun onAdClosed() {
                    findNavController().navigate(R.id.apiCallFragment)
                }

            })

        }

        binding.btnExitApp.setOnClickListener {
            activity?.finishAffinity()
        }

    }
}