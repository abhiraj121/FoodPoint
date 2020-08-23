package com.abhirajsharma.commerceapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.abhirajsharma.commerceapp.adapter.ItemsAdapter
import com.abhirajsharma.commerceapp.model.CategoryItemsRespose
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_category_items.*

class CategoryItems : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_items)

        items_cat.layoutManager = LinearLayoutManager(this)

        val bundle = intent.extras!!
        val thisCategory = bundle.getString("message")!!
        val url = "http://192.168.43.180/ecom/get_cat_items.php?category=${thisCategory}"
        val rq = Volley.newRequestQueue(this)!!
        val req = StringRequest(Request.Method.GET,url,Response.Listener<String> { response->
            val gsonBuilder = GsonBuilder()
            val gson = gsonBuilder.create()
            val myItems:CategoryItemsRespose = gson.fromJson(response,CategoryItemsRespose::class.java)
            items_cat.adapter = ItemsAdapter(this,myItems)
        }, Response.ErrorListener { error ->
            Log.d("url",error.message.toString())
        })
        rq.add(req)
    }
}