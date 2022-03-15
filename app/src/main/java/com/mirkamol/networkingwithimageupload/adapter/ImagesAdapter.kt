package com.mirkamol.networkingwithimageupload.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mirkamol.networkingwithimageupload.R
import com.mirkamol.networkingwithimageupload.model.CatItem

class ImagesAdapter(val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val list: ArrayList<CatItem> = ArrayList()

    inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo = view.findViewById<ImageView>(R.id.iv_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val image = list[position]
        if (holder is ImageViewHolder) {
            Glide.with(context).load(image.url).into(holder.photo)


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addList(list: List<CatItem>){
        this.list.addAll(list)
        notifyDataSetChanged()
    }


}