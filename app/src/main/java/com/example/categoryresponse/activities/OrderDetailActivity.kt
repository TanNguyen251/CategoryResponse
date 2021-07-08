package com.example.categoryresponse.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.categoryresponse.R
import com.example.categoryresponse.models.OrderHistory
import kotlinx.android.synthetic.main.activity_order_detail.*
import kotlinx.android.synthetic.main.grocery_toolbar.*

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
    }
}