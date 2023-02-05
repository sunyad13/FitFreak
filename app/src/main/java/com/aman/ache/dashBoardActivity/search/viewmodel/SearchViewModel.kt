package com.aman.ache.dashBoardActivity.search.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import com.aman.ache.dashBoardActivity.search.adapter.SearchAdapter
import com.aman.ache.dashBoardActivity.secondary.model.DetailDataClass
import com.aman.ache.databinding.FragmentSearchBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchViewModel : ViewModel() {

    val list = mutableListOf<DetailDataClass>()
    private val db = Firebase.firestore
    private lateinit var adapter:SearchAdapter


    fun getAllExercises(context: Context,binding: FragmentSearchBinding){

       val ref = db.collection("Exercises")

       ref.get()
           .addOnSuccessListener { result->
               for(doc in result){
                   val tempObj = doc.toObject(DetailDataClass::class.java)
                   list.add(tempObj)
               }
               loadRec(context,binding)
           }
    }


    fun loadRec(context: Context, binding: FragmentSearchBinding){
       list.shuffle()
       binding.searchShimmerLay.stopShimmer()
       binding.searchShimmerLay.isVisible = false
       binding.recyclerView.isVisible = true
       adapter = SearchAdapter(context,list)
       binding.recyclerView.adapter = adapter
   }



    fun searchExercise(context: Context,query: String,binding: FragmentSearchBinding) {
        val tempList = mutableListOf<DetailDataClass>()
        for(obj in list){
            if(obj.name.lowercase().contains(query.lowercase())){
                if(!tempList.contains(obj)){
                    tempList.add(obj)
                }
            }
        }
        if(tempList.isNotEmpty()){
            binding.text.text = "Search results..."
            binding.recyclerView.isVisible = true
            binding.lottie.isVisible = false
            adapter = SearchAdapter(context,tempList)
            binding.recyclerView.adapter = adapter
            adapter.notifyDataSetChanged()
        }else{
            binding.recyclerView.isVisible = false
            binding.lottie.isVisible = true
            binding.text.text = "No results found!!"
        }

    }

    fun loadAllExercises(context: Context,binding: FragmentSearchBinding) {
        list.shuffle()
        adapter = SearchAdapter(context,list)
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}


// // getting from beginner
//
//
//        fun get(context: Context,binding: FragmentSearchBinding){
//        bodyPart = db.collection("beginner").document("choicesdocument").collection("choices")
//            .document("withequipment").collection("bodyparts")
//
//            var ref = bodyPart.document("achest").collection("chestbodyparts")
//
//        ref.get()
//            .addOnSuccessListener { docs->
//                for(d in docs){
//                    val tempObj = d.toObject(DetailDataClass::class.java)
//                    list.add(tempObj)
//                }
//                Toast.makeText(context, list.size.toString(), Toast.LENGTH_SHORT).show()
//
//            }
//
//         ref = bodyPart.document("bshoulder").collection("shoulderbodyparts")
//
//        ref.get()
//            .addOnSuccessListener { docs->
//                for(d in docs){
//                    val tempObj = d.toObject(DetailDataClass::class.java)
//                    list.add(tempObj)
//                }
//
//            }
//
//
//         ref = bodyPart.document("carm").collection("armbodyparts")
//
//        ref.get()
//            .addOnSuccessListener { docs->
//                for(d in docs){
//                    val tempObj = d.toObject(DetailDataClass::class.java)
//                    list.add(tempObj)
//                }
//
//            }
//
//
//
//         ref = bodyPart.document("dback").collection("backbodyparts")
//
//        ref.get()
//            .addOnSuccessListener { docs->
//                for(d in docs){
//                    val tempObj = d.toObject(DetailDataClass::class.java)
//                    list.add(tempObj)
//
//                }
//                Toast.makeText(context, list.size.toString(), Toast.LENGTH_SHORT).show()
//
//            }
//
//
//         ref = bodyPart.document("eleg").collection("legbodyparts")
//
//        ref.get()
//            .addOnSuccessListener { docs->
//                for(d in docs){
//                    val tempObj = d.toObject(DetailDataClass::class.java)
//                    list.add(tempObj)
//                }
//
//            }
//
//
//
//        // for intermediate
//        bodyPart = db.collection("intermediate").document("choicesdocument").collection("choices")
//            .document("withequipment").collection("bodyparts")
//
//         ref = bodyPart.document("achest").collection("chestbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//                    Toast.makeText(context, list.size.toString(), Toast.LENGTH_SHORT).show()
//
//                }
//        },4000)
//
//
//        ref = bodyPart.document("bshoulder").collection("shoulderbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//
//                }
//        },4000)
//
//
//        ref = bodyPart.document("carm").collection("armbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//
//                }
//        },4000)
//
//
//
//        ref = bodyPart.document("dback").collection("backbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//
//                }
//        },4000)
//
//
//        ref = bodyPart.document("eleg").collection("legbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//                    Toast.makeText(context, list.size.toString(), Toast.LENGTH_SHORT).show()
//
//                }
//        },4000)
//
//
//
//        //advance
//
//        bodyPart = db.collection("advance").document("choicesdocument").collection("choices")
//            .document("withequipment").collection("bodyparts")
//
//        ref = bodyPart.document("achest").collection("chestbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//
//                }
//        },4000)
//
//        ref = bodyPart.document("bshoulder").collection("shoulderbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//
//                }
//        },4000)
//
//
//        ref = bodyPart.document("carm").collection("armbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//                    Toast.makeText(context, list.size.toString(), Toast.LENGTH_SHORT).show()
//
//                }
//        },4000)
//
//
//
//        ref = bodyPart.document("dback").collection("backbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//                    }
//                    Toast.makeText(context, list.size.toString(), Toast.LENGTH_SHORT).show()
//
//                }
//        },4000)
//
//
//        ref = bodyPart.document("eleg").collection("legbodyparts")
//
//        Handler(Looper.getMainLooper()).postDelayed({
//            ref.get()
//                .addOnSuccessListener { docs ->
//                    for (d in docs) {
//                        val tempObj = d.toObject(DetailDataClass::class.java)
//                        var i = 0
//                        var j=0
//                        while(i<list.size){
//                            if (list[i].name.lowercase() == tempObj.name.lowercase()) {
//                                j=1
//                                break
//                            }
//                            i++
//                        }
//                        if(j==0){
//                            list.add(tempObj)
//                        }
//
//
//                    }
//                    Toast.makeText(context, list.size.toString(), Toast.LENGTH_SHORT).show()
//                    binding.searchShimmerLay.stopShimmer()
//                    binding.searchShimmerLay.isVisible = false
//                    binding.recyclerView.isVisible = true
//                    adapter = SearchAdapter(context,list)
//                    binding.recyclerView.adapter = adapter
//
//                    upload()
//                }
//        },4000)
//
//        }
//
//    private fun upload() {
//        var i = 0
//        while(i<list.size){
//        db.collection("Exercises").document(list[i].name).set(list[i])
//            i++
//        }
//
//    }


