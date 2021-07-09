package com.example.categoryresponse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.adapters.OrderDetailAdapter
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.models.OrderHistory
import com.example.categoryresponse.models.OrderProduct
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.grocery_toolbar.*
import org.json.JSONArray
import org.json.JSONObject

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        init()
        grocery_toolbar.title = "Order Details"
        setSupportActionBar(grocery_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home ->{
                finish()
            }
        }
        return true
    }

    private fun init(){
        var orderHistory = intent.getSerializableExtra("order history") as OrderHistory
        order_detail_orderNo.text = "Order number: ${orderHistory._id}"
        order_detail_user_name.text = "Recipient's name: ${orderHistory.user.name}"
        order_detail_user_address.text = "Shipping Address: ${orderHistory.shippingAddress.houseNo} ${orderHistory.shippingAddress.streetName}, \n" +
                "${orderHistory.shippingAddress.city}, ${orderHistory.shippingAddress.pincode}"
        order_detail_total.text = "Total: ${orderHistory.orderSummary.totalAmount}"
        order_detail_pay.text = "Paid: ${orderHistory.orderSummary.ourPrice}"
        var orderDetailAdapter = OrderDetailAdapter(this, orderHistory.products as ArrayList<OrderProduct>)
        order_detail_rv_product.adapter = orderDetailAdapter
        order_detail_rv_product.layoutManager = LinearLayoutManager(this)
        order_detail_rv_product.addItemDecoration(
            DividerItemDecoration(
                order_detail_rv_product.context,
                LinearLayoutManager(this).orientation)
        )
        order_detail_button_reorder.setOnClickListener{
            reorder(orderHistory)
        }
    }

    private fun reorder(orderHistory: OrderHistory){
        var jsonObject = JSONObject()
        jsonObject
            .put(KeyConfig.ADDRESS_USER, orderHistory.userId)
            .put("orderStatus", orderHistory.orderStatus)
            .put("orderSummary", JSONObject(Gson().toJson(orderHistory.orderSummary)))
            .put("user", JSONObject(Gson().toJson(orderHistory.user)))
            .put("shippingAddress", JSONObject(Gson().toJson(orderHistory.shippingAddress)))
            .put("payment", JSONObject(Gson().toJson(orderHistory.payment)))
            .put("products", JSONArray(Gson().toJson(orderHistory.products)))
        Log.d("abcd", jsonObject.toString())
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.POST,
            EndpointsConfig.getOrdersRegisterUrl(),
            jsonObject,
            {
                Log.d("abcd", "Re-order successfully")
            },
            {
                Log.d("abcd", it.message.toString())
            }
        )
        requestQueue.add(request)
        startActivity(Intent(this,OrderHistoryActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        finish()
    }
}