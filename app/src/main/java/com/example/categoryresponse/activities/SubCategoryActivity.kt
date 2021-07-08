package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.adapters.SubCategoryPagerAdapter
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.managers.LoginManager
import com.example.categoryresponse.models.SubCategory
import com.example.categoryresponse.models.SubCategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.grocery_toolbar.*
import org.json.JSONObject

class SubCategoryActivity : AppCompatActivity() {
    var mList: ArrayList<SubCategory> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
        grocery_toolbar.title = "Product List"
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
        var catId = intent.getIntExtra(KeyConfig.CATEGORY_ID, 0)
        var jsonObject = JSONObject()
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            EndpointsConfig.getSubCategoryUrlByCatId(catId),
            jsonObject,
            {
                var gson = Gson()
                var subCategoryResponse = gson.fromJson(it.toString(), SubCategoryResponse::class.java)
                mList = subCategoryResponse.data as ArrayList<SubCategory>
                var subCategoryPagerAdapter = SubCategoryPagerAdapter(supportFragmentManager)
                for(i in mList){
                    subCategoryPagerAdapter.addFragment(i)
                }
                subcategory_view_pager.adapter = subCategoryPagerAdapter
                subcategory_tab_layout.setupWithViewPager(subcategory_view_pager)
            },
            {

            }
        )
        requestQueue.add(request)
    }
}