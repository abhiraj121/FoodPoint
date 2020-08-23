package com.abhirajsharma.commerceapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.activity_total.*
import java.math.BigDecimal

class Total : AppCompatActivity() {

    var config:PayPalConfiguration?=null
    var payPalAmout:Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total)
        
        val bill:String = intent.getStringExtra("lmao")!!
        val url = "http://192.168.43.180/ecom/get_total.php?bill_no=$bill"

        val rq = Volley.newRequestQueue(this)
        val req = StringRequest(Request.Method.GET, url, Response.Listener<String> {response->
            total_et.text = response.toString()
        }, Response.ErrorListener { error ->
            Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
        })
        rq.add(req)

        config = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(UserInfo.client_id)
        val i = Intent(this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
        startService(i)

        paypal_btn.setOnClickListener {
            payPalAmout = total_et.text.toString().toDouble()
            val payment = PayPalPayment(BigDecimal.valueOf(payPalAmout),"USD","Commerce App",PayPalPayment.PAYMENT_INTENT_SALE)
            val intent = Intent(this, PaymentActivity::class.java)
            intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config)
            intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payment)
            startActivityForResult(intent,123)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode==123){
            if (resultCode==Activity.RESULT_OK){
                val obj = Intent(this, Confirm::class.java)
                startActivity(obj)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        stopService(Intent(this,PayPalService::class.java))
        super.onDestroy()
    }
}