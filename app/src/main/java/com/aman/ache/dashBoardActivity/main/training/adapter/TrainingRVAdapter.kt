package com.aman.ache.dashBoardActivity.main.training.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.secondary.SecondaryActivity
import com.aman.ache.databinding.TrainingItemBinding

class TrainingRVAdapter(val context:Context):RecyclerView.Adapter<TrainingRVAdapter.ViewHolder>() {

    private lateinit var intent:Intent
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = TrainingItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        intent = Intent(context,SecondaryActivity::class.java)

        when(position){
            0 -> holder.binding.imageView.setImageResource(R.drawable.p1)
            1 -> holder.binding.imageView.setImageResource(R.drawable.p2)
            else -> holder.binding.imageView.setImageResource(R.drawable.p3)
        }

        holder.itemView.setOnClickListener {
            when(position){
                0 -> {
                    //beginner
                    intent.putExtra("sender",0)
                    context.startActivity(intent)
                }
                1 -> {
                    //intermediate
                    intent.putExtra("sender",1)
                    context.startActivity(intent)
                }
                else -> {
                    //advance
                    intent.putExtra("sender",2)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount() = 3


    class ViewHolder(val binding:TrainingItemBinding) : RecyclerView.ViewHolder(binding.root)


}