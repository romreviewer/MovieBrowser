package com.romreviewer.moviebrowser.ui.moviedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.json.JSONArray
import org.json.JSONException

class MovieDetailViewPager(application: Application) : AndroidViewModel(application) {
    val movieName = MutableLiveData("")
    val overview = MutableLiveData("")
    val releaseDate= MutableLiveData("")
    val geneList = mutableListOf<String>()
    var genre: ArrayList<Int>? = null
    private val gen =
        "[{\"id\":28,\"name\":\"Action\"},{\"id\":12,\"name\":\"Adventure\"},{\"id\":16,\"name\":\"Animation\"},{\"id\":35,\"name\":\"Comedy\"},{\"id\":80,\"name\":\"Crime\"},{\"id\":99,\"name\":\"Documentary\"},{\"id\":18,\"name\":\"Drama\"},{\"id\":10751,\"name\":\"Family\"},{\"id\":14,\"name\":\"Fantasy\"},{\"id\":36,\"name\":\"History\"},{\"id\":27,\"name\":\"Horror\"},{\"id\":10402,\"name\":\"Music\"},{\"id\":9648,\"name\":\"Mystery\"},{\"id\":10749,\"name\":\"Romance\"},{\"id\":878,\"name\":\"Science Fiction\"},{\"id\":10770,\"name\":\"TV Movie\"},{\"id\":53,\"name\":\"Thriller\"},{\"id\":10752,\"name\":\"War\"},{\"id\":37,\"name\":\"Western\"}]"

    fun genre() {
        try {
            val jsonObject = JSONArray(gen)
            for (j in 0 until jsonObject.length()) {
                val genname = jsonObject.getJSONObject(j)
                val gencodearr = genre
                for (k in 0 until gencodearr!!.size) {
                    val gencode: Int = gencodearr[k]
                    if (genname.getInt("id") == gencode) {
                        val lisItem = genname.getString("name")
                        geneList.add(lisItem)
                    }
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.d("GenE",e.message.toString())
            geneList.clear()
        }
    }

}