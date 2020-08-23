package com.abhirajsharma.commerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_registration.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        http://192.168.43.180/ecom/login_user.php?mobile=46276756&password=11111

        signup_btn.setOnClickListener {
            val i = Intent(this, Registration::class.java)
            startActivity(i)
        }

        login_btn.setOnClickListener {
            if (login_mobile.text.toString().trim() == ""){
                login_mobile.error = "This field cannot be blank"
            }
            if (login_password.text.toString().trim() == ""){
                login_password.error = "This field cannot be blank"
            }
            val mobileNum = login_mobile.text.toString()
            val password = login_password.text.toString()
            val url = "http://192.168.43.180/ecom/login_user.php"
            val rq = Volley.newRequestQueue(this)
            val req = object : StringRequest(Request.Method.POST,url,Response.Listener <String>{ response ->
                if (response == "0"){
                    if (mobileNum.isNotEmpty() && password.isNotEmpty()) {
                        Toast.makeText(this, "User not registered", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    UserInfo.mobile = mobileNum
                    UserInfo.access_token = response
                    Toast.makeText(this,"Welcome",Toast.LENGTH_SHORT).show()
                    val i = Intent(this,Home::class.java)
                    startActivity(i)
                }
            }, Response.ErrorListener {error->
                Toast.makeText(this,error.message,Toast.LENGTH_SHORT).show()
            }){
                override fun getParams(): MutableMap<String, String> {
                    val map = HashMap<String,String>()
                    map["mobile"] = mobileNum
                    map["password"] = password
                    return map
                }
            }
            rq.add(req)
        }

    }

}