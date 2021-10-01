package com.example.Company

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.Pojo.Companies
import com.example.Products.Products_activity
import com.example.phons.R

class CompanyAdapter(val context:Context): RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    private var arraylist = arrayListOf<Companies>()
    private var listener: OnclicCompanyInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {

        return CompanyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_company,parent,false))

    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.tv_company_id.text = arraylist[position].compid
        holder.tv_company_title.text = arraylist[position].company_title
        holder.tv_company_lastversion.text = arraylist[position].lastversion

        holder.image_company.layoutParams.height = 300
        holder.image_company.layoutParams.width = 300

        val url = "https://app.mytasks.click/${arraylist[position].image_link}"

        Glide.with(context).load(url).placeholder(R.drawable.placeholderimg).into(holder.image_company)

        holder.itemView.setOnClickListener {
            val intent = Intent(context ,Products_activity::class.java)
            context.startActivities(arrayOf(intent))
            listener?.onclickcompany(arraylist[position])
        }
    }

    override fun getItemCount(): Int {
        return arraylist.size
    }

    class CompanyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val tv_company_id = itemView.findViewById<TextView>(R.id.tv_company_id)
        val tv_company_title = itemView.findViewById<TextView>(R.id.tv_company_title)
        val tv_company_lastversion = itemView.findViewById<TextView>(R.id.tv_company_lastversion)
        val image_company = itemView.findViewById<ImageView>(R.id.image_company)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(ArrayList:ArrayList<Companies>)
    {
        this.arraylist = ArrayList
        notifyDataSetChanged()
    }

    fun onclickproduct(listiner:OnclicCompanyInterface)
    {
        this.listener = listiner
    }
}