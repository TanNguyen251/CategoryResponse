package com.example.categoryresponse.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.categoryresponse.R
import com.example.categoryresponse.models.OrderHistory
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        init()
    }

    private fun init(){
        var orderHistory = intent.getSerializableExtra("order history") as OrderHistory
        order_detail_date.text = orderHistory.date
        order_detail_user_name.text = orderHistory.user.name
        order_detail_user_address.text = "${orderHistory.shippingAddress.houseNo} ${orderHistory.shippingAddress.streetName}, \n" +
                "${orderHistory.shippingAddress.city}, ${orderHistory.shippingAddress.pincode}"
    }
}