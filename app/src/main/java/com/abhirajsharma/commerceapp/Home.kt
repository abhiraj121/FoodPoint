package com.abhirajsharma.commerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.abhirajsharma.commerceapp.model.CategoryResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_home.*

class Home : AppCompatActivity() {

    val url = "http://192.168.43.180/ecom/get_cat.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val list = ArrayList<String>()
//        val jar = JsonArrayRequest(Request.Method.GET,url,null,Response.Listener { response ->
//            for (x in 0 until response.length()){
//                list.add(response.getJSONObject(x).getString("category"))
//            }
//            val arrayAdp = ArrayAdapter(this,R.layout.category_view,list)
//            home_cat.adapter = arrayAdp
//        },Response.ErrorListener {error->
//            Log.d("url", error.message.toString())
//        })

        val myList = ArrayList<String>()
        val rq = Volley.newRequestQueue(this)!!
        val req = StringRequest(Request.Method.GET,url,Response.Listener<String> { response ->
            val gsonBuilder = GsonBuilder()
            val gson = gsonBuilder.create()!!
            val cats: CategoryResponse = gson.fromJson(response, CategoryResponse::class.java)
            for (x in 0 until cats.size){
                myList.add(cats[x].category)
            }
            val arrayAdp = ArrayAdapter(this,R.layout.category_view,myList)
            home_cat.adapter = arrayAdp
        }, Response.ErrorListener { error ->
            Log.d("url",error.message.toString())
        })
        rq.add(req)

        home_cat.setOnItemClickListener { parent, view, position, id ->
            val catName = myList.get(position)
            val i = Intent(this,CategoryItems::class.java)
            i.putExtra("message",catName)
            startActivity(i)
        }
    }
}