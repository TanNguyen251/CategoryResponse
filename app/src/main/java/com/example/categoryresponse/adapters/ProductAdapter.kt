package com.example.categoryresponse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class ProductAdapter(var mContext: Context, var mList: ArrayList<Product>): RecyclerView.Adapter<ProductAdapter.MyProductViewHolder>() {
    private var listener: OnAdapterListener? = null
    inner class MyProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(product: Product){
                Picasso
                    .get()
                    .load(EndpointsConfig.getImageUrl(product.image))
                    .placeholder(R.drawable.ic_baseline_image_search_100)
                    .error(R.drawable.ic_baseline_image_not_supported_100)
                    .into(itemView.product_image)
                itemView.product_name.text = product.productName
                itemView.product_unit.text = product.unit
                itemView.product_sale_price.text = product.price.toString()
                itemView.product_price.text = product.mrp.toString()
                itemView.setOnClickListener{
                    listener?.onItemClick(product)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.MyProductViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_product_adapter, parent, false)
        return MyProductViewHolder(view)
    }


    fun setOnAdapterListener(onAdapterListener: OnAdapterListener){
        listener = onAdapterListener
    }

    interface OnAdapterListener{
        fun onItemClick(product: Product)
    }

    override fun onBindViewHolder(holder: MyProductViewHolder, position: Int) {
        var product = mList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}