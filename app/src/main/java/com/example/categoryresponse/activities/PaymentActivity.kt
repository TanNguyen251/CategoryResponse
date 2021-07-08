package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.databases.DBHelper
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.grocery_toolbar.*
import org.json.JSONArray
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        init()
        grocery_toolbar.title = "Confirm Order"
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
        var profilePreference = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE)
        var addressPreference = getSharedPreferences(KeyConfig.MY_ADDRESS, Context.MODE_PRIVATE)
        var orderPreference = getSharedPreferences(KeyConfig.MY_ORDER, Context.MODE_PRIVATE)
        payment_edit_name.setText(profilePreference.getString(KeyConfig.FIRST_NAME, ""))
        payment_edit_house.setText(addressPreference.getString(KeyConfig.ADDRESS_HOUSE, ""))
        payment_edit_street.setText(addressPreference.getString(KeyConfig.ADDRESS_STREET,""))
        payment_edit_city.setText(addressPreference.getString(KeyConfig.ADDRESS_CITY, ""))
        payment_edit_pincode.setText(addressPreference.getInt(KeyConfig.ADDRESS_ZIP, 1).toString())
        payment_text_total.text = "Total: ${orderPreference.getFloat(KeyConfig.ORDER_TOTAL, 0f)}"
        payment_text_pay.text = "Pay: ${orderPreference.getFloat(KeyConfig.ORDER_PAY, 0f)}"
        payment_edit_email.setText(profilePreference.getString(KeyConfig.EMAIL, ""))
        payment_edit_mobile.setText(profilePreference.getString(KeyConfig.MOBILE, ""))
        payment_button_update.setOnClickListener(this)
        payment_button_cash.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v){
            payment_button_update ->{
                updateAddress()
            }
            payment_button_cash ->{
                sendOrder("Cash")
            }
        }
    }

    private fun updateAddress(){
        var addressPreference = getSharedPreferences(KeyConfig.MY_ADDRESS, Context.MODE_PRIVATE)
        var addressId = addressPreference.getString(KeyConfig.ADDRESS_ID,"")
        var editor = addressPreference.edit()
        editor.putString(KeyConfig.ADDRESS_HOUSE, payment_edit_house.text.toString())
        editor.putString(KeyConfig.ADDRESS_STREET, payment_edit_street.text.toString())
        editor.putString(KeyConfig.ADDRESS_CITY, payment_edit_city.text.toString())
        editor.putInt(KeyConfig.ADDRESS_ZIP, payment_edit_pincode.text.toString().toInt())
        editor.commit()
        var jsonObject = JSONObject()
        jsonObject.put(KeyConfig.ADDRESS_HOUSE, payment_edit_house.text.toString())
        jsonObject.put(KeyConfig.ADDRESS_STREET, payment_edit_street.text.toString())
        jsonObject.put(KeyConfig.ADDRESS_CITY, payment_edit_city.text.toString())
        jsonObject.put(KeyConfig.ADDRESS_ZIP, payment_edit_pincode.text.toString().toInt())
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.PUT,
            EndpointsConfig.getAddressUpdateUrl(addressId),
            jsonObject,
            {
                Log.d("abcd", "Address update successful")
            },
            {
                Log.d("abcd", "Address update failed")
            }
        )
        requestQueue.add(request)
    }

    private fun sendOrder(paymentType: String){
        var orderPreference = getSharedPreferences(KeyConfig.MY_ORDER, Context.MODE_PRIVATE)
        var total = orderPreference.getFloat(KeyConfig.ORDER_TOTAL, 0f)
        var pay = orderPreference.getFloat(KeyConfig.ORDER_PAY, 0f)
        var discount = orderPreference.getFloat(KeyConfig.ORDER_DISCOUNT, 0f)
        var deliveryCharge = 20
        if (pay>300f){
            deliveryCharge = 0
        }
        var orderSummaryJson = JSONObject()
        orderSummaryJson
            .put("totalAmount", total)
            .put("ourPrice", pay)
            .put("discount", discount)
            .put("deliveryCharges", deliveryCharge)
            .put("orderAmount", total)

        var profilePreference = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE)
        var name = profilePreference.getString(KeyConfig.FIRST_NAME, "")
        var email = profilePreference.getString(KeyConfig.EMAIL, "")
        var mobile = profilePreference.getString(KeyConfig.MOBILE, "")
        var userJson = JSONObject()
        userJson
            .put(KeyConfig.NAME, name)
            .put(KeyConfig.EMAIL, email)
            .put(KeyConfig.MOBILE, mobile)

        var addressPreference = getSharedPreferences(KeyConfig.MY_ADDRESS, Context.MODE_PRIVATE)
        var houseNo = addressPreference.getString(KeyConfig.ADDRESS_HOUSE, "")
        var streetName = addressPreference.getString(KeyConfig.ADDRESS_STREET, "")
        var city = addressPreference.getString(KeyConfig.ADDRESS_CITY, "")
        var pincode = addressPreference.getInt(KeyConfig.ADDRESS_ZIP, 0)
        var shippingAddressJson = JSONObject()
        shippingAddressJson
            .put(KeyConfig.ADDRESS_HOUSE, houseNo)
            .put(KeyConfig.ADDRESS_STREET, streetName)
            .put(KeyConfig.ADDRESS_CITY, city)
            .put(KeyConfig.ADDRESS_ZIP, pincode)

        var paymentJson = JSONObject()
        paymentJson
            .put("paymentMode", paymentType)
            .put("paymentStatus", "completed")

        var dbHelper = DBHelper(this)
        var productList = dbHelper.readProduct()
        var productJsonArray = JSONArray(Gson().toJson(productList))


        Log.d("abcd", productJsonArray.toString())
        var userId = addressPreference.getString(KeyConfig.ADDRESS_USER, "")
        Log.d("abcd", userId.toString())
        var jsonObject = JSONObject()
        jsonObject
            .put(KeyConfig.ADDRESS_USER, userId)
            .put("orderStatus", "Confirmed")
            .put("orderSummary", orderSummaryJson)
            .put("user", userJson)
            .put("shippingAddress", shippingAddressJson)
            .put("payment", paymentJson)
            .put("products", productJsonArray)
        Log.d("abcd", jsonObject.toString())
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.POST,
            EndpointsConfig.getOrdersRegisterUrl(),
            jsonObject,
            {
                Log.d("abcd","Order successfully sent")
                dbHelper.deleteAllProduct()
            },
            {
                Log.d("abcd", it.message.toString())
            }
        )
        requestQueue.add(request)

        startActivity(Intent(this, GroceryActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

}