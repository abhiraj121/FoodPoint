package com.abhirajsharma.commerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.abhirajsharma.commerceapp.adapter.TempOrderAdapter
import com.abhirajsharma.commerceapp.model.Order
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_order_check_out.*

class OrderCheckOut : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_check_out)

        val url = "http://192.168.43.180/ecom/get_temp.php?mobile="+UserInfo.mobile

        val rq = Volley.newRequestQueue(this)!!
        val req = StringRequest(Request.Method.GET,url,Response.Listener<String> { response ->
            val gsonBuilder = GsonBuilder()
            val gson = gsonBuilder.create()!!
            val con = gson.fromJson(response, Order::class.java)!!
            val thisAdapter = TempOrderAdapter(this,con)
            order_lv.adapter = thisAdapter
        }, Response.ErrorListener { error ->
            Toast.makeText(this,error.message.toString(),Toast.LENGTH_SHORT).show()
        })
        rq.add(req)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.item_cancel){
            val url = "http://192.168.43.180/ecom/cancel_order.php?mobile="+UserInfo.mobile
            val rq = Volley.newRequestQueue(this)
            val req = StringRequest(Request.Method.GET,url,Response.Listener {
                val i = Intent(this, Home::class.java)
                startActivity(i)
            },Response.ErrorListener { error ->
                Toast.makeText(this,error.message.toString(),Toast.LENGTH_SHORT).show()
            })
            rq.add(req)
            Toast.makeText(this,"Order Canceled",Toast.LENGTH_SHORT).show()
        }
        if (item.itemId==R.id.item_menu){
            val i = Intent(this, Home::class.java)
            startActivity(i)
        }
        if (item.itemId==R.id.item_confirm)  {
            val url = "http://192.168.43.180/ecom/confirm_order.php?mobile=" + UserInfo.mobile
            val rq = Volley.newRequestQueue(this)
            val req = StringRequest(Request.Method.GET, url, Response.Listener<String> {
                val i = Intent(this, Total::class.java)
                i.putExtra("lmao",it.toString())
                startActivity(i)
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
            })
            rq.add(req)
        }
        return super.onOptionsItemSelected(item)
    }

}