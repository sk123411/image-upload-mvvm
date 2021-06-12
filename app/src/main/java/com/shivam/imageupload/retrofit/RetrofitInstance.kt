package com.shivam.imageupload.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {


    companion object{

         var instance:Retrofit?=null


        fun getRetrofitInstance():Retrofit{

            if (instance==null){

                instance = Retrofit.Builder().baseUrl("https://api.cloudinary.com/v1_1/{Your Cloudinary Code}/")
                    .addConverterFactory(GsonConverterFactory.create()).build()

            }

            return instance!!


        }

    }
}