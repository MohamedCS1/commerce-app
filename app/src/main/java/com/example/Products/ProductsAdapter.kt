package com.example.Products

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.Pojo.Product
import com.example.phons.R
import com.squareup.picasso.Picasso
import java.io.File

class ProductsAdapter(val context: Context):RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private var arraylist = arrayListOf<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        return ProductViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_product,parent,false))

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.tv_product_title.text = arraylist[position].title
        holder.tv_product_id.text = arraylist[position].id
        holder.tv_product_price.text = arraylist[position].price
        holder.tv_product_lastversion.text = arraylist[position].lastversion

        val url = "https://app.mytasks.click/${arraylist[position].image}"

        Picasso.with(context).load( File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),"Samco/${arraylist[position].lastversion}.jpg")).placeholder(R.drawable.placeholderimg).into(holder.image_product)

    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tv_product_title = itemView.findViewById<TextView>(R.id.tv_product_title)
        val tv_product_id = itemView.findViewById<TextView>(R.id.tv_product_id)
        val tv_product_price = itemView.findViewById<TextView>(R.id.tv_product_price)
        val tv_product_lastversion = itemView.findViewById<TextView>(R.id.tv_product_lastversion)
        val image_product = itemView.findViewById<ImageView>(R.id.image_product)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(ArrayList: ArrayList<Product>)
    {
        this.arraylist = ArrayList
        notifyDataSetChanged()
    }
}