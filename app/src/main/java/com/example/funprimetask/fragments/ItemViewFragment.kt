package com.example.funprimetask.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.funprimetask.R
import com.example.funprimetask.databinding.FragmentItemViewBinding
import com.example.funprimetask.utils.AdsManager


class ItemViewFragment : Fragment() {

    private lateinit var binding: FragmentItemViewBinding
    private var tempTitle: String = ""
    private var tempURL: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentItemViewBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tempTitle = arguments?.getString("imageTitle").toString()
        tempURL = arguments?.getString("imageURL").toString()

        binding.txtImageName.text = tempTitle
        context?.let {
            Glide.with(it)
                .load(tempURL)
                .centerCrop()
                .into(binding.imgDisplayingPhoto)
        }

        binding.imgSharePhoto.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_STREAM, tempURL)
                type = "image/jpeg"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                putExtra(Intent.EXTRA_SUBJECT, "Share Photo")
            }

            context?.startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        AdsManager.instance.loadBannerAd(binding.layoutBanner, requireActivity())
    }

}