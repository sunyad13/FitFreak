package com.aman.ache.dashBoardActivity.search.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.search.SearchExec
import com.aman.ache.dashBoardActivity.secondary.adpater.DetailAdapter
import com.aman.ache.dashBoardActivity.secondary.model.DetailDataClass
import com.aman.ache.databinding.DetailListItemBinding

import com.aman.ache.databinding.SearchItemListBinding

class SearchAdapter(private val context: Context, private val list:MutableList<DetailDataClass>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = SearchItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.binding.text.text = list[position].name


      val an =  when(list[position].an.lowercase()){

            "push" -> {
                    R.raw.pushups
            }

            "b" -> {
                R.raw.bicep
            }

            "c" -> {
                R.raw.cycling
            }

            "d" -> {
                R.raw.dumbell
            }

            "bar" -> {
                R.raw.barbell
            }

          "pull" -> {
            R.raw.pullups
          }

          "cool" -> {
              R.raw.cooldown
          }

            else -> {
                //squats
                R.raw.squats
            }


        }

        holder.binding.lottie.setAnimation(an)




        holder.itemView.setOnClickListener {
            val intent = Intent(context,SearchExec::class.java)
            intent.putExtra("send",list[position].name)
            context.startActivity(intent)
        }

    }

    override fun getItemCount() = list.size


    class SearchViewHolder(val binding: SearchItemListBinding) : RecyclerView.ViewHolder(binding.root)


}