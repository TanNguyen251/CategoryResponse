package com.example.categoryresponse.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.categoryresponse.R
import com.example.categoryresponse.activities.OrderDetailActivity
import com.example.categoryresponse.models.OrderHistory
import kotlinx.android.synthetic.main.row_order_history_adapter.view.*

class OrderHistoryAdapter(var mContext: Context, var mList: ArrayList<OrderHistory>): RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder>() {
    inner class OrderHistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind (orderHistory: OrderHistory){
            itemView.order_id.text = "Order #${orderHistory._id}"
            itemView.order_summary_total.text = "Total: ${orderHistory.orderSummary.totalAmount}"
            itemView.order_summary_pay.text = "Payment: ${orderHistory.orderSummary.ourPrice}"
            itemView.order_status.text = "Status: ${orderHistory.orderStatus}"
            itemView.order_button_detail.setOnClickListener{
                var intent = Intent(mContext, OrderDetailActivity::class.java)
                intent.putExtra("order history", orderHistory)
                mContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.row_order_history_adapter, parent, false)
        return OrderHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderHistoryViewHolder, position: Int) {
        var orderHistory = mList[position]
        holder.bind(orderHistory)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}