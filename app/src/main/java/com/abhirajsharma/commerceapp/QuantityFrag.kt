package com.abhirajsharma.commerceapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class QuantityFrag : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.fragment_quantity, container, false)!!
        val addQuantity:EditText = v.findViewById(R.id.et_qty)
        val addButton:Button = v.findViewById(R.id.btn_qty)
        addButton.setOnClickListener {
            val quantity = addQuantity.text.toString().toInt()
            if (quantity>=1) {
                val mobileNum = UserInfo.mobile
                val itemID = UserInfo.itemId
                val rq = Volley.newRequestQueue(activity)!!
                val url = "http://192.168.43.180/ecom/add_temp.php?mobile=$mobileNum&itemID=$itemID&quantity=$quantity"
                val req = StringRequest(Request.Method.GET, url, Response.Listener {
                    val i = Intent(activity, OrderCheckOut::class.java)
                    startActivity(i)
                }, Response.ErrorListener {
                    Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT).show()
                })
                rq.add(req)
            }else{
                Toast.makeText(activity,"Enter some quantity.",Toast.LENGTH_SHORT).show()
            }
        }
        return v
    }

}