package com.aman.ache.dashBoardActivity.secondary.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.secondary.model.DetailDataClass
import com.aman.ache.databinding.BodyPartItemBinding
import com.aman.ache.databinding.DetailListItemBinding
import com.bumptech.glide.Glide


//this adapter will help to show the content in the detail fragment i.e to show the name of the exercises with serial number.
//in the specific body part.

class DetailAdapter(private val list:MutableList<DetailDataClass>,private val listener:OnDetailItemClick) : RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val binding = DetailListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return DetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {

        holder.binding.execName.text = list[position].name
        holder.binding.count.text = (position+1).toString()
        if(position==list.size-1){
            holder.binding.view.setBackgroundResource(R.color.app_bg)
        }


        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }

    }

    override fun getItemCount() = list.size

    class DetailViewHolder(val binding: DetailListItemBinding) : RecyclerView.ViewHolder(binding.root)
}

interface OnDetailItemClick{
    fun onItemClick(position:Int)
}
