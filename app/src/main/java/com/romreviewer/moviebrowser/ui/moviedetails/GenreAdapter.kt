package com.romreviewer.moviebrowser.ui.moviedetails

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.romreviewer.moviebrowser.R

class GenreAdapter(val listItem: List<String>): RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.gener_item,
            parent,
            false
        )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val li = listItem[position]
        holder.textview.text = li
    }

    override fun getItemCount(): Int {
        return listItem.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textview: TextView = itemView.findViewById<View>(R.id.textView2) as TextView
    }


}