package com.example.testphpmysql0801


import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * C Belal이 작성  on 5/21/2017.
 */
class ArtistList(private val context: Activity, internal var artists: List<Artist>) : ArrayAdapter<Artist>(context, R.layout.layout_list_artist, artists) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.layout_list_artist, null, true)

        val textViewName = listViewItem.findViewById<TextView>(R.id.textViewName)
        val textViewGenre = listViewItem.findViewById<TextView>(R.id.textViewGenre)

        val artist = artists[position]
        textViewName.text = artist.name
        textViewGenre.text = artist.genre

        return listViewItem
    }
}