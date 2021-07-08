package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.adapters.OrderHistoryAdapter
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.managers.LoginManager
import com.example.categoryresponse.models.OrderHistory
import com.example.categoryresponse.models.OrderResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.grocery_toolbar.*

class OrderHistoryActivity : AppCompatActivity() {
    private var orderHistoryList: ArrayList<OrderHistory> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        init()
        grocery_toolbar.title = "My Orders"
        setSupportActionBar(grocery_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.grocery_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.grocery_cart -> {
                startActivity(Intent(this, CheckoutActivity::class.java))
            }
            R.id.grocery_profile ->{
                var intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.grocery_logout -> {
                var loginManager = LoginManager(this)
                loginManager.logout()
            }
            R.id.grocery_setting -> {

            }
        }
        return true
    }

    private fun init(){
        var profilePreferences = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE)
        var userId = profilePreferences.getString(KeyConfig.USER_ID, "")
        var orderHistoryUrl = EndpointsConfig.getOrderHistoryUrl(userId)
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            orderHistoryUrl,
            null,
            {
                var gson = Gson()
                var orderResponse = gson.fromJson(it.toString(), OrderResponse::class.java)
                orderHistoryList = orderResponse.data as ArrayList<OrderHistory>
                var orderHistoryAdapter = OrderHistoryAdapter(this, orderHistoryList)
                order_history_recycler_view.adapter = orderHistoryAdapter
                order_history_recycler_view.layoutManager = LinearLayoutManager(this)
            },
            {

            }
        )
        requestQueue.add(request)
    }
}