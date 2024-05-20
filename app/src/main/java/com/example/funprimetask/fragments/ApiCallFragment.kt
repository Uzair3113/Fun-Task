package com.example.funprimetask.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.funprimetask.R
import com.example.funprimetask.adapters.MainPhotosAdapter
import com.example.funprimetask.databinding.FragmentApiCallBinding
import com.example.funprimetask.interfaces.PhotoApiServiceInterface
import com.example.funprimetask.models.PhotosDataModel
import com.example.funprimetask.utils.AdsManager
import com.example.funprimetask.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiCallFragment : Fragment() {

    private lateinit var binding: FragmentApiCallBinding
    private lateinit var tempList: ArrayList<PhotosDataModel>
    private var tempTitle: String = ""
    private var tempURL: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentApiCallBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tempList = ArrayList()

        val retrofitClient = RetrofitClient.create()
        val call = retrofitClient.getPhotos()

        call.enqueue(object : Callback<List<PhotosDataModel>> {
            override fun onResponse(
                call: Call<List<PhotosDataModel>>,
                response: Response<List<PhotosDataModel>>
            ) {
                if (response.isSuccessful) {
                    val newPhotosList = response.body()
                    settingUpRecyclerView(newPhotosList!!)
                    newPhotosList.forEach { tempList.add(PhotosDataModel(it.albumId, it.id, it.title, it.url, it.thumbnailUrl)) }

                    Log.d("SIZE", newPhotosList.size.toString())
                } else {
                    Toast.makeText(context, "Unsuccessful response", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PhotosDataModel>>, t: Throwable) {
                Log.d("SIZETAG", t.message.toString())
            }

        })

    }

    private fun settingUpRecyclerView(list: List<PhotosDataModel>) {
        binding.recViewPhotos.layoutManager = LinearLayoutManager(context)
        binding.recViewPhotos.adapter = context?.let { MainPhotosAdapter(it, list) {
            tempTitle = it.title
            tempURL = it.url

            AdsManager.instance.showInterstitial(true, requireActivity(), object :AdsManager.InterstitialAdListener{
                override fun onAdClosed() {
                    findNavController().navigate(R.id.action_apiCallFragment_to_itemViewFragment, bundleOf("imageTitle" to tempTitle, "imageURL" to tempURL))
                }

            })
        }
        }
        binding.recViewPhotos.adapter?.notifyDataSetChanged()
        binding.pBar2.visibility = View.GONE
    }

}