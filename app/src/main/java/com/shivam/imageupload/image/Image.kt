package com.shivam.imageupload.image

import com.shivam.imageupload.helper.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

interface Image {

suspend fun uploadImage(file:MultipartBody.Part, preset:RequestBody):Resource<Any>


}