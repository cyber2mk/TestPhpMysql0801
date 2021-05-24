package com.example.testphpmysql0801


import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class ViewArtistsActivity : AppCompatActivity() {

    private var listView: ListView? = null
    private var artistList: MutableList<Artist>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_artists)
                        Log.d("xxyy","로그출력 => setContentView = ")
        listView = findViewById<ListView>(R.id.listViewArtists)
                        Log.d("xxyy","로그출력 => listView = $listView")
        artistList = mutableListOf<Artist>()
                        Log.d("xxyy","로그출력 => artistList = $artistList")
        loadArtists();
                        Log.d("xxyy","로그출력 => 777777777777 = ")
    }

    private fun loadArtists() {
        Log.d("xxyy","로그출력 => 8888888URL_GET_ARTIST = ")
        val stringRequest = StringRequest(Request.Method.GET, EndPoints.URL_GET_ARTIST,
            Response.Listener<String> { s ->
                Log.d("xxyy","로그출력 => 2aaa222aaaa = $s")
                try {
                    Log.d("xxyy","로그출력 => aaaaaa1111aaaa = ")
                    val obj = JSONObject(s)
                    if (!obj.getBoolean("error")) {
                        val array = obj.getJSONArray("artists")
                        Log.d("xxyy","로그출력 => array = $array")
                        for (i in 0..array.length() - 1) {
                            val objectArtist = array.getJSONObject(i)
                            val artist = Artist(
                                objectArtist.getString("name"),
                                objectArtist.getString("genre")
                            )
                            artistList!!.add(artist)
                            val adapter = ArtistList(this@ViewArtistsActivity, artistList!!)
                            listView!!.adapter = adapter
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_LONG).show()

                    }
                } catch (e: JSONException) {
                    Log.d("xxyy","로그출력 => JSONException = $e: JSONException")
                    Log.d("xxyy","로그출력 => e.printStackTrace = $e.printStackTrace")
                    e.printStackTrace()
                }
            }, Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() })

        val requestQueue = Volley.newRequestQueue(this)
                            Log.d("xxyy","로그출력 => requestQueue = $requestQueue")
        requestQueue.add<String>(stringRequest)
                            Log.d("xxyy","로그출력 => stringRequest = $stringRequest")
    }
}