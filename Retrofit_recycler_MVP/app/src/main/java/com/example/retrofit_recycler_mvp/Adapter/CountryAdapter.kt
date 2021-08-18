package com.example.retrofit_recycler_mvp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit_recycler_mvp.Model.CountryRes
import com.example.retrofit_recycler_mvp.R


class CountryAdapter(context: Context, list: List<CountryRes>) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {
    private val context: Context
    private var list: List<CountryRes> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvCountryName.setText(list[position].name)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCountryName: TextView

        init {
            tvCountryName = itemView.findViewById(R.id.tv_country_name)
        }
    }

    init {
        this.context = context
        this.list = list
    }
}
