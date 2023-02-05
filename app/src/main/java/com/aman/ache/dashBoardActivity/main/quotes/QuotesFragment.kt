package com.aman.ache.dashBoardActivity.main.quotes


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aman.ache.dashBoardActivity.main.quotes.viewmodel.QuoteViewModel
import com.aman.ache.databinding.FragmentQuotesBinding



class QuotesFragment : Fragment() {
   private lateinit var binding:FragmentQuotesBinding
   private val viewModel:QuoteViewModel by activityViewModels()

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      val view = FragmentQuotesBinding.inflate(inflater,container,false)
      binding = view
      return view.root
   }


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      if(viewModel.list.isEmpty()){
         binding.lottie.isVisible = true
         binding.textView.isVisible = true
         binding.textView.text = "Loading your Quotes..."
      }


      binding.recyclerView.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)

      getQuotes()
   }


   private fun getQuotes() {
      viewModel.loadQuotes(binding,requireActivity())
   }


}