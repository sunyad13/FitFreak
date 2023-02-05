package com.aman.ache.dashBoardActivity.secondary.adpater

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.secondary.model.BodyDataClass
import com.aman.ache.databinding.BodyPartItemBinding
import com.bumptech.glide.Glide

class BodyPartsAdapter(private val list:MutableList<BodyDataClass>, private val context: Context,private val listener: OnBodyPartItemClick,
                       val type:Int): RecyclerView.Adapter<BodyPartsAdapter.BodyPartsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BodyPartsViewHolder {
        val binding = BodyPartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return BodyPartsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BodyPartsViewHolder, position: Int) {

        holder.binding.descTextView.text = list[position].desc
        holder.binding.durationTextView.text = list[position].duration
        holder.binding.titleTextView.text = list[position].title

        Glide.with(context).load(list[position].image).into(holder.binding.imageView)

        //checking whether the recyclerView item have it's last item or not
        //if having last element then remove the bottom line that is used to separate  the items in the Recycler View.
        if(position == list.size-1){
            holder.binding.bottomView.visibility = View.GONE
            holder.binding.marginView.isVisible = true
        }

        when(type){

            0 -> {
                holder.binding.t1.setImageResource(R.drawable.ic_thunder)
                holder.binding.t2.setImageResource(R.drawable.ic_no_thunder)
                holder.binding.t3.setImageResource(R.drawable.ic_no_thunder)
            }
            1 -> {
                holder.binding.t1.setImageResource(R.drawable.ic_thunder)
                holder.binding.t2.setImageResource(R.drawable.ic_thunder)
                holder.binding.t3.setImageResource(R.drawable.ic_no_thunder)
            }
            else -> {
                holder.binding.t1.setImageResource(R.drawable.ic_thunder)
                holder.binding.t2.setImageResource(R.drawable.ic_thunder)
                holder.binding.t3.setImageResource(R.drawable.ic_thunder)
            }
        }

        holder.itemView.setOnClickListener {
            listener.onItemClick(list[position].title,list[position].image)
        }


    }

    override fun getItemCount() = list.size


    class BodyPartsViewHolder(val binding:BodyPartItemBinding):RecyclerView.ViewHolder(binding.root)

}

interface OnBodyPartItemClick {
    fun onItemClick(title:String,image:String)
}