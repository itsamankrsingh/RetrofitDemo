package com.magician.retrofitdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var retService: AlbumService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        retService = RetrofitInstance.getRetrofitInstance()
            .create(AlbumService::class.java)

        //getRequestWithQueryParameter()
        //getRequestWithPathParameter()
        uploadItem()

    }

    private fun uploadItem() {
        val album = AlbumItem(-1, "Tis is my first post requrst", 2069)
        val postResponse: LiveData<Response<AlbumItem>> = liveData {
            val response = retService.uploadAlbum(album)
            emit(response)
        }

        postResponse.observe(this, Observer {
            val receivedAlbumItem=it.body()
            val result=" "+"Album Title: ${receivedAlbumItem!!.title}"+"\n"+
                    " "+"Album Id: ${receivedAlbumItem!!.id}"+"\n"+
                    " "+"User Id: ${receivedAlbumItem!!.userId}"+"\n\n\n"

            text_view.append(result)
        })
    }

    private fun getRequestWithPathParameter() {
        //path parameter

        val pathRespomse: LiveData<Response<AlbumItem>> = liveData {
            val response = retService.getAlbum(3)
            emit(response)
        }

        pathRespomse.observe(this, Observer {
            val title = it.body()!!.title
            Toast.makeText(applicationContext, title, Toast.LENGTH_LONG).show()
        })
    }

    private fun getRequestWithQueryParameter() {
        val responseLiveData: LiveData<Response<Album>> = liveData {
            val response = retService.getSortedAlbum(3)
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    Log.i("MYTAG", albumItem.title)
                    val result = " " + "Album Title: ${albumItem.title}" + "\n" +
                            " " + "Album id: ${albumItem.id}" + "\n" +
                            " " + "User id: ${albumItem.userId}" + "\n\n\n"

                    text_view.append(result)
                }
            }
        })
    }
}