package com.aman.ache.dashBoardActivity.main.quotes.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.main.quotes.model.QuoteImages
import com.aman.ache.dashBoardActivity.main.quotes.model.QuoteModel
import com.aman.ache.databinding.QuoteItemBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog


class QuoteAdapter(val list: List<QuoteModel>, val context: FragmentActivity, private val images: List<QuoteImages>) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val binding = QuoteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return QuoteViewHolder(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {

//        holder.binding.textView.background = randList[position%10]
        val content = list[position].text
        holder.binding.textView.text = content

        if(images[position].color == "black"){
            holder.binding.quotell.setBackgroundResource(R.drawable.quote_gradient0)
            holder.binding.textView.setTextColor(context.resources.getColor(R.color.black))
            Glide.with(context).load(images[position].url).into(holder.binding.bgImage)
            holder.binding.imageView.setImageResource(R.drawable.ic_quotes)
        }else{
            holder.binding.quotell.setBackgroundResource(R.drawable.quote_gradient1)
            holder.binding.textView.setTextColor(context.resources.getColor(R.color.white))
            Glide.with(context).load(images[position].url).into(holder.binding.bgImage)
            holder.binding.imageView.setImageResource(R.drawable.ic_quotes_white)
        }

        holder.itemView.setOnClickListener {

            val bottomSheetDialog = BottomSheetDialog(context,R.style.BottomSheetTheme)
            val sheetView = LayoutInflater.from(context).inflate(R.layout.quote_bottom_sheet,
                context.findViewById(R.id.bottom_sheet)
            )

            bottomSheetDialog.setContentView(sheetView)
            bottomSheetDialog.show()

            val send = sheetView.findViewById<Button>(R.id.share_btn)
            val copy = sheetView.findViewById<Button>(R.id.copy_btn)
            val text = sheetView.findViewById<TextView>(R.id.text)

            text.text = content

            send.setOnClickListener {

                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, content)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                context.startActivity(shareIntent)

                bottomSheetDialog.dismiss()
            }

            copy.setOnClickListener {
                val clipboard = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText("label", content)
                clipboard!!.setPrimaryClip(clip)
                Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismiss()
            }

        }
    }

    override fun getItemCount()  = list.size


    class QuoteViewHolder(val binding:QuoteItemBinding) : RecyclerView.ViewHolder(binding.root)

}