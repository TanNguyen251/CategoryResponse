package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.adapters.AddressAdapter
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.models.Address
import com.example.categoryresponse.models.AddressResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.grocery_toolbar.*

class AddressActivity : AppCompatActivity() {
    var addressList: ArrayList<Address> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        grocery_toolbar.title = "Address"
        setSupportActionBar(grocery_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        init()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return true
    }
    private fun init(){
        var userId = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE).getString(KeyConfig.USER_ID,"")
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            EndpointsConfig.getUserAddressUrl(userId),
            null,
            {
                var gson = Gson()
                var addressResponse = gson.fromJson(it.toString(), AddressResponse::class.java)
                addressList = addressResponse.data as ArrayList<Address>
                var addressAdapter = AddressAdapter(this, addressList)
                address_recycler_view.adapter = addressAdapter
                address_recycler_view.layoutManager = LinearLayoutManager(this)
            },
            {
                Log.d("abcd","Unable to get user address")
            }
        )
        requestQueue.add(request)
        address_button_add.setOnClickListener{
            startActivity(Intent(this, AddAddressActivity::class.java))
        }
    }
}