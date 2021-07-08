package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.databases.DBHelper
import com.example.categoryresponse.managers.LoginManager
import com.example.categoryresponse.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.grocery_toolbar.*

class ProductDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        init()
        grocery_toolbar.title = "Product detail"
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
        var product = intent.getSerializableExtra(KeyConfig.PRODUCT) as Product
        product_detail_name.text = product.productName
        product_detail_unit.text = product.unit
        product_detail_sale_price.text = product.price.toString()
        product_detail_price.text = product.mrp.toString()
        Picasso
            .get()
            .load(EndpointsConfig.getImageUrl(product.image))
            .fit()
            .placeholder(R.drawable.ic_baseline_image_search_100)
            .error(R.drawable.ic_baseline_image_not_supported_100)
            .into(product_detail_image)
        product_detail_add_to_cart.setOnClickListener{
            var dbHelper = DBHelper(this)
            dbHelper.addProduct(product)
            startActivity(Intent(this, CheckoutActivity::class.java))
        }
    }
}