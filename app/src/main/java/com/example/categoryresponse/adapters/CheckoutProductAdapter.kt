package com.example.categoryresponse.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.categoryresponse.R
import com.example.categoryresponse.activities.CheckoutActivity
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.databases.DBHelper
import com.example.categoryresponse.models.CheckoutProduct
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_checkout_adapter.view.*

class CheckoutProductAdapter(var mContext: Context, var mList: ArrayList<CheckoutProduct>): RecyclerView.Adapter<CheckoutProductAdapter.MyCheckOutViewHolder>() {

    inner class MyCheckOutViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(checkoutProduct: CheckoutProduct){
            itemView.checkout_name.text = checkoutProduct.productName
            itemView.checkout_count.text = checkoutProduct.quantity.toString()
            itemView.checkout_price.text = checkoutProduct.mrp.toString()
            itemView.checkout_sale_price.text = checkoutProduct.price.toString()
            Picasso
                .get()
                .load(EndpointsConfig.getImageUrl(checkoutProduct.image))
                .fit()
                .placeholder(R.drawable.ic_baseline_image_search_100)
                .error(R.drawable.ic_baseline_image_not_supported_100)
                .into(itemView.checkout_image)
            var dbHelper = DBHelper(mContext)
            var intent = Intent(mContext, CheckoutActivity::class.java)
            itemView.checkout_button_add.setOnClickListener{
                dbHelper.increaseCountProduct(checkoutProduct._id, checkoutProduct.quantity)
                (mContext as CheckoutActivity).finish()
                (mContext as CheckoutActivity).overridePendingTransition(0,0)
                mContext.startActivity(intent)
                (mContext as CheckoutActivity).overridePendingTransition(0,0)
            }
            itemView.checkout_button_remove_one.setOnClickListener{
                dbHelper.decreaseCountProduct(checkoutProduct._id, checkoutProduct.quantity)
                (mContext as CheckoutActivity).finish()
                (mContext as CheckoutActivity).overridePendingTransition(0,0)
                mContext.startActivity(intent)
                (mContext as CheckoutActivity).overridePendingTransition(0,0)
            }
            itemView.checkout_button_remove_all.setOnClickListener{
                dbHelper.deleteProduct(checkoutProduct._id)
                (mContext as CheckoutActivity).finish()
                (mContext as CheckoutActivity).overridePendingTransition(0,0)
                mContext.startActivity(intent)
                (mContext as CheckoutActivity).overridePendingTransition(0,0)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCheckOutViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_checkout_adapter, parent, false)
        return MyCheckOutViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyCheckOutViewHolder, position: Int) {
        var checkoutProduct = mList[position]
        holder.bind(checkoutProduct)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}