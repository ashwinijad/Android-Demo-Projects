package com.example.mvpdemo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mvpdemo.Model.RetroPhoto
import com.example.mvpdemo.R
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso


class CustomAdapter(context: Context, dataList: List<RetroPhoto?>?) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    private val dataList: List<RetroPhoto?>?
    private val context: Context

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mView: View
        var txtTitle: TextView
        var id:TextView
         val coverImage: ImageView

        init {
            mView = itemView
            txtTitle = mView.findViewById(R.id.Text2)
            coverImage = mView.findViewById(R.id.avatar)
            id=mView.findViewById(R.id.Text1)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.custom_row, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.txtTitle.text = dataList!![position]?.title
        holder.id.text= dataList[position]?.id.toString()
        val builder = Picasso.Builder(context)
        builder.downloader(OkHttp3Downloader(context))
        builder.build().load(dataList[position]?.thumbnailUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(holder.coverImage)
    }

    override fun getItemCount(): Int {
        return dataList!!.size
    }

    init {
        this.context = context
        this.dataList = dataList
    }
}