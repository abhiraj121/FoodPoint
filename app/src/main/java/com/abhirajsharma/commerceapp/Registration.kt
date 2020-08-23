package com.abhirajsharma.commerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_registration.*

class Registration:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        signup_reg_btn.setOnClickListener {
            if (reg_name.text.toString().trim() == "") {
                reg_name.error = "This field cannot be blank"
            }
            if (reg_mobile.text.toString().trim() == "") {
                reg_mobile.error = "This field cannot be blank"
            }
            if (reg_address.text.toString().trim() == "") {
                reg_address.error = "This field cannot be blank"
            }
            if (reg_password.text.toString().trim() == "") {
                reg_password.error = "This field cannot be blank"
            }
            if(reg_password.text.toString()==reg_password_confirm.text.toString()) {
                val myName = reg_name.text.toString().replace(" ", "%20")
                val myAddress = reg_address.text.toString().replace(" ", "%20")
                val url = "http://192.168.43.180/ecom/add_user.php?mobile=" + reg_mobile.text +
                        "&password=" + reg_password.text + "&name=" + myName + "&address=" + myAddress
                val rq = Volley.newRequestQueue(this)
                val req = StringRequest(Request.Method.GET,url,Response.Listener<String> { response ->
                    if (response == "0"){
                        Toast.makeText(this, "User already registered.", Toast.LENGTH_SHORT).show()
                    }else {
                        UserInfo.mobile = reg_mobile.text.toString()
                        Toast.makeText(this, "Welcome ${reg_name.text}.", Toast.LENGTH_SHORT).show()
                        val i = Intent(this,Home::class.java)
                        startActivity(i)
                    }
                },Response.ErrorListener {error->
                    Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
                })
                rq.add(req)
            }else {
                reg_password_confirm.error = "Password doesn't match"
            }
        }
    }
}