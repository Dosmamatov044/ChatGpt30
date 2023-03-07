package com.example.chatgpt30

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ChatGptInterfaceApi {



@POST("v1/completions")

 suspend  fun getPrompt(@Body request:MyRequest):Response<ResponseModel>


 @Multipart
 @POST("v1/images/edits")

 suspend fun getImage(@Part image:MultipartBody.Part,@Part("prompt") text:String,@Part("n")n:Int ):Response<ImageModel>









}