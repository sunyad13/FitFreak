package com.aman.ache.dashBoardActivity.main.quotes.network

import com.aman.ache.dashBoardActivity.main.quotes.model.QuoteModel
import retrofit2.Call
import retrofit2.http.GET

interface QuoteEndpoint {
    @GET("quotes")
   suspend fun getQuote():List<QuoteModel>
}