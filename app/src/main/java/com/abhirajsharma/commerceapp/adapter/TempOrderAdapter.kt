package com.abhirajsharma.commerceapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.abhirajsharma.commerceapp.R
import com.abhirajsharma.commerceapp.model.Order

class TempOrderAdapter(var context: Context, var myList: Order): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.order_view,parent,false)!!
        val current = myList[position]
        val priceInLong = current.price.toDouble().times(current.quantity)
        val priceUpToTwoDigits:Double = String.format("%.3f", priceInLong).toDouble()
        val finalPrice:Double = String.format("%.2f", priceUpToTwoDigits).toDouble()
        view.findViewById<TextView>(R.id.order_name)?.text = current.name
        view.findViewById<TextView>(R.id.order_quantity)?.text = current.quantity.toString()
        view.findViewById<TextView>(R.id.order_price)?.text = finalPrice.toString()
        return view
    }
    override fun getItem(position: Int): Any {
        return myList[position]
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getCount(): Int = myList.size
}