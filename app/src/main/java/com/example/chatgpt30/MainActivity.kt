package com.example.chatgpt30

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.chatgpt30.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




   // getPrompt()

    getImage()




    }


   fun  getImage(){

       lifecycleScope.launch(Dispatchers.IO) {


           val oldBitmap=BitmapFactory.decodeResource(resources,R.drawable.star)

           val width=256
           val height=256

           val newBitmap=Bitmap.createScaledBitmap(oldBitmap,width,height,true)

           val file= File(cacheDir,"star.png")

           val os:OutputStream=FileOutputStream(file)
           newBitmap.compress(Bitmap.CompressFormat.PNG,100,os)
           os.flush()
           os.close()

           val request=RequestBody.create("image/*".toMediaTypeOrNull(),file)

            val body=MultipartBody.Part.createFormData("image",file.name,request)


           try {
            val data=   App.api?.getImage(body,"Сделай звезду желтым",1)

               if (data!!.isSuccessful){

                   Log.d("ololo",data.body()!!.data[0].url)
               }else{
               Log.d("ololo",data.errorBody()?.string().toString())

               }



           }catch (e:Exception){

               Log.d("ololo",e.message.toString())
           }finally {
               Log.d("ololo","Error")
           }


















       }



   }




    private fun getPrompt() {
        lifecycleScope.launch(Dispatchers.IO) {


            try {
                val data=  App.api?.getPrompt(MyRequest("text-davinci-003","Когда умер Есенин?",2048,2))

                if (data!!.isSuccessful){


                    launch(Dispatchers.Main) {

                        binding.txt.text=data.body()!!.choices[0].text
                        Log.d("ololo",data.body()!!.choices[0].text)
                    }



                }else{

                    Log.d("ololo",data.errorBody().toString())
                }
            }catch (e:Exception){

                Log.d("ololo",e.message.toString())

            }finally {
                Log.d("ololo","Error")


            }}
    }


}