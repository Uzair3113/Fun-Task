package com.example.funprimetask.interfaces


import com.example.funprimetask.models.PhotosDataModel
import retrofit2.Call
import retrofit2.http.GET

interface PhotoApiServiceInterface {
    @GET("photos")
    fun getPhotos(): Call<List<PhotosDataModel>>
}