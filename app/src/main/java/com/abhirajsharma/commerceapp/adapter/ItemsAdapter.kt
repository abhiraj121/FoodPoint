package com.abhirajsharma.commerceapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.abhirajsharma.commerceapp.QuantityFrag
import com.abhirajsharma.commerceapp.R
import com.abhirajsharma.commerceapp.UserInfo
import com.abhirajsharma.commerceapp.model.CategoryItemsResposeItem
import com.bumptech.glide.Glide

class ItemsAdapter(private var context: Context, private var list: ArrayList<CategoryItemsResposeItem>):
    RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {

    class ItemsViewHolder(view: View):RecyclerView.ViewHolder(view){
        val thisName: TextView = view.findViewById(R.id.items_name)
        val category: TextView = view.findViewById(R.id.items_category)
        val price: TextView = view.findViewById(R.id.items_price)
        val foodImage: ImageView = view.findViewById(R.id.items_imgView)
        val saveImage:ImageView = view.findViewById(R.id.items_save)
        val obj = QuantityFrag()
        val manager = (view.context as FragmentActivity).supportFragmentManager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        val la = LayoutInflater.from(context)
        val myView = la.inflate(R.layout.category_items_view,parent,false)
        return ItemsViewHolder(myView)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val element = list[position]
        holder.thisName.text = element.name
        holder.category.text = element.category
        holder.price.text = "₹${element.price}"
        val url = "http://192.168.43.180/ecom/images/"+element.photo
        Glide.with(context).load(url).into(holder.foodImage)
        holder.saveImage.setOnClickListener {
            UserInfo.itemId = element.id
            holder.obj.show(holder.manager,"Qty")
        }
    }
}