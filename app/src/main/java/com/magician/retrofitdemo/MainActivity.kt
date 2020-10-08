package com.magician.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retService = RetrofitInstance.getRetrofitInstance()
            .create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Album>> = liveData {
            val response = retService.getAlbum()
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    Log.i("MYTAG", albumItem.title)
                    val result=" "+"Album Title: ${albumItem.title}"+"\n"+
                            " "+"Album id: ${albumItem.id}"+"\n"+
                            " "+"User id: ${albumItem.userId}"+"\n\n\n"

                    text_view.append(result)
                }
            }
        })
    }
}