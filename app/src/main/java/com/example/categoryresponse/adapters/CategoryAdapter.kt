package com.example.categoryresponse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class CategoryAdapter(var mContext: Context, var mList: ArrayList<Category>): RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    private var listener: OnAdapterListener? = null
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(category: Category){
            Picasso
                .get()
                .load(EndpointsConfig.getImageUrl(category.catImage))
                .fit()
                .placeholder(R.drawable.ic_baseline_image_search_100)
                .error(R.drawable.ic_baseline_image_not_supported_100)
                .into(itemView.category_image)
            itemView.category_text_name.text = category.catName
            itemView.setOnClickListener{
                listener?.onItemClick(category)
            }
        }
    }

    fun setOnAdapterListener(onAdapterListener: OnAdapterListener){
        listener = onAdapterListener
    }

   interface OnAdapterListener{
        fun onItemClick(category: Category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}