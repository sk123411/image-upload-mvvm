package com.shivam.imageupload.image

import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageAPI {


    @POST("upload")
    @Multipart
    suspend fun uploadImage(@Part file: MultipartBody.Part, @Part("upload_preset") preset: RequestBody):Response<Any>

}