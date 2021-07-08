package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.grocery_toolbar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    val houseType = arrayOf("Home","Office","Others")
    lateinit var type: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        init()
        grocery_toolbar.title = "Add Address"
        setSupportActionBar(grocery_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
        var arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, houseType)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        add_address_spinner_type.adapter = arrayAdapter
        add_address_spinner_type.onItemSelectedListener = this
        add_address_button_save.setOnClickListener{
            var userId = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE).getString(KeyConfig.USER_ID,"")
            var houseNo = add_address_house.text.toString()
            var street = add_address_street.text.toString()
            var city = add_address_city.text.toString()
            var pincode = add_address_pincode.text.toString().toInt()
            var jsonObject = JSONObject()
            jsonObject.put(KeyConfig.ADDRESS_HOUSE, houseNo)
            jsonObject.put(KeyConfig.ADDRESS_STREET, street)
            jsonObject.put(KeyConfig.ADDRESS_CITY, city)
            jsonObject.put(KeyConfig.ADDRESS_ZIP, pincode)
            jsonObject.put(KeyConfig.ADDRESS_TYPE, type)
            jsonObject.put(KeyConfig.ADDRESS_USER, userId)
            var requestQueue = Volley.newRequestQueue(this)
            var request = JsonObjectRequest(
                Request.Method.POST,
                EndpointsConfig.getAddressRegisterUrl(),
                jsonObject,
                {

                },
                {
                    Log.d("abcd","Unable to register Address")
                }
            )
            requestQueue.add(request)
            finish()
            startActivity(Intent(this, AddressActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY))
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        type = houseType[position]
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

}