package com.example.mvvmdatabindingdemo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmdatabindingdemo.databinding.HelloBinding


 class MyAdapter(private val arrayList: ArrayList<MyListViewModel>, context: Context) :
    RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    private val context: Context
    private var layoutInflater: LayoutInflater? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val myListBinding: HelloBinding =
            DataBindingUtil.inflate(layoutInflater!!, R.layout.hello, parent, false)
        return ViewHolder(myListBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myListViewModel = arrayList[position]
        holder.bind(myListViewModel)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

     inner class ViewHolder(myListBinding: HelloBinding) :
        RecyclerView.ViewHolder(myListBinding.getRoot()) {
        private val myListBinding: HelloBinding
        fun bind(myli: MyListViewModel?) {
           myListBinding.setMylistmodel(myli)
            myListBinding.executePendingBindings()
        }

        fun getMyListBinding(): HelloBinding {
            return myListBinding
        }

        init {
            this.myListBinding = myListBinding
        }
    }

    init {
        this.context = context
    }
}
