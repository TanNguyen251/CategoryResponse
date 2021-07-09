package com.example.categoryresponse.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.categoryresponse.R
import com.example.categoryresponse.models.OrderHistory
import com.example.categoryresponse.models.OrderProduct
import kotlinx.android.synthetic.main.row_order_detail_adapter.view.*

class OrderDetailAdapter(var mContext: Context, var mList: ArrayList<OrderProduct>): RecyclerView.Adapter<OrderDetailAdapter.MyOrderDetailViewHolder>() {
    inner class MyOrderDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(orderProduct: OrderProduct){
            itemView.order_detail_product_name.text = orderProduct.productName
            itemView.order_detail_product_quantity.text = orderProduct.quantity.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOrderDetailViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_detail_adapter, parent, false)
        return MyOrderDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyOrderDetailViewHolder, position: Int) {
        var orderProduct = mList[position]
        holder.bind(orderProduct)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}