package com.aman.ache.dashBoardActivity.main.quotes.viewmodel


import androidx.core.view.isInvisible
import androidx.lifecycle.ViewModel

import com.aman.ache.dashBoardActivity.main.quotes.adapter.QuoteAdapter
import com.aman.ache.dashBoardActivity.main.quotes.model.QuoteModel
import com.aman.ache.dashBoardActivity.main.quotes.network.QuoteEndpoint
import com.aman.ache.dashBoardActivity.main.quotes.network.RetroFitService
import com.aman.ache.databinding.FragmentQuotesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.aman.ache.R
import com.aman.ache.dashBoardActivity.main.quotes.model.QuoteImages
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope


class QuoteViewModel:ViewModel() {

    var list:List<QuoteModel> = listOf()
    var imageList = mutableListOf<QuoteImages>()
    private val db = Firebase.firestore

    private lateinit var adapter:QuoteAdapter

    fun loadQuotes(binding: FragmentQuotesBinding, s:FragmentActivity) {

        if(list.isEmpty()){
                CoroutineScope(Dispatchers.IO).launch {

                    try{
                    list = RetroFitService().getInstance().create(QuoteEndpoint::class.java).getQuote()

                      val ref = db.collection("Quoteimages")
                        ref.get()
                            .addOnSuccessListener { res ->
                                    for(doc in res){
                                        val tempObj = doc.toObject(QuoteImages::class.java)
                                        imageList.add(tempObj)
                                    }
                                s.runOnUiThread {// Stuff that updates the UI
                                    imageList.shuffle()
                                    adapter = QuoteAdapter(list,s,imageList)
                                    binding.recyclerView.adapter = adapter
                                    binding.lottie.isInvisible = true
                                    binding.textView.isInvisible = true
                                }
                            }

                    }catch (e:Exception){
                        s.runOnUiThread {
                            binding.lottie.setAnimation(R.raw.internet)
                            binding.textView.isVisible = true
                            binding.textView.text = "please check your internet connection"
                        }
                }
            }
        }else{
            binding.recyclerView.adapter = adapter
        }

    }

}