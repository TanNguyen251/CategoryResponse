package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.categoryresponse.R
import com.example.categoryresponse.adapters.CheckoutProductAdapter
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.databases.DBHelper
import com.example.categoryresponse.managers.LoginManager
import com.example.categoryresponse.models.CheckoutProduct
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.grocery_toolbar.*

class CheckoutActivity : AppCompatActivity() {
    private var checkoutProductList: ArrayList<CheckoutProduct> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        init()
        grocery_toolbar.title = "Your Cart"
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
            R.id.grocery_profile ->{
                var sharedPreferences = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE)
                var id = sharedPreferences.getString(KeyConfig.USER_ID, "")
                var intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra(KeyConfig.USER_ID, id)
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
        var dbHelper = DBHelper(this)
        checkoutProductList = dbHelper.readProduct()
        var checkoutProductAdapter = CheckoutProductAdapter(this, checkoutProductList)
        checkout_recycler_view.adapter = checkoutProductAdapter
        checkout_recycler_view.layoutManager = LinearLayoutManager(this)
        var total = 0f
        var pay = 0f
        for (i in checkoutProductList){
            total += i.mrp * i.quantity
            pay += i.price * i.quantity
        }
        if (total == 0f){
            checkout_text_total.text = ""
            checkout_text_pay.text = ""
        } else {
            checkout_text_total.text = "Total: $total"
            checkout_text_pay.text = "Pay: $pay"
        }
        var discount = total - pay
        var sharedPreferences = getSharedPreferences(KeyConfig.MY_ORDER, Context.MODE_PRIVATE)
        var editor = sharedPreferences.edit()
        editor.putFloat(KeyConfig.ORDER_TOTAL, total)
        editor.putFloat(KeyConfig.ORDER_PAY, pay)
        editor.putFloat(KeyConfig.ORDER_DISCOUNT,discount)
        editor.commit()

        checkout_button_continue.setOnClickListener{
            finish()
            startActivity(Intent(this, GroceryActivity::class.java))
        }
        checkout_button_checkout.setOnClickListener{
            startActivity(Intent(this, AddressActivity::class.java))
        }
    }

}