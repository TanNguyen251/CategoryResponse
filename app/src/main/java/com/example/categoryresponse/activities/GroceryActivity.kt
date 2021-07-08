package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.adapters.CategoryAdapter
import com.example.categoryresponse.adapters.ImagePagerAdapter
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.managers.LoginManager
import com.example.categoryresponse.models.Category
import com.example.categoryresponse.models.CategoryResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_grocery.*
import kotlinx.android.synthetic.main.grocery_toolbar.*
import org.json.JSONObject

class GroceryActivity : AppCompatActivity(), CategoryAdapter.OnAdapterListener{
    private var mList: ArrayList<Category> = ArrayList()
    private var imageList: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grocery)
        init()
        grocery_toolbar.title = "Grocery"
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
                var loginManager = LoginManager(this)
                loginManager.logout()
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
            R.id.grocery_order_history -> {
                startActivity(Intent(this, OrderHistoryActivity::class.java))
            }
        }
        return true
    }

    private fun init(){
        var jsonObject = JSONObject()
        var requestQueue = Volley.newRequestQueue(this)
        var request = JsonObjectRequest(
            Request.Method.GET,
            EndpointsConfig.getCategoryUrl(),
            jsonObject,
            {
                var gson = Gson()
                var categoryResponse = gson.fromJson(it.toString(), CategoryResponse::class.java)
                mList = categoryResponse.data as ArrayList<Category>
                for(i in 0 until mList.size){
                    imageList.add(mList[i].catImage)
                    println(imageList[i])
                }
                var imagePagerAdapter = ImagePagerAdapter(this, imageList)
                main_view_pager.adapter = imagePagerAdapter
                var categoryAdapter = CategoryAdapter(this, mList)
                categoryAdapter.setOnAdapterListener(this)
                main_recycler_view.adapter = categoryAdapter
                main_recycler_view.layoutManager = GridLayoutManager(this, 2)

            },
            {

            }
        )
        requestQueue.add(request)
    }

    override fun onItemClick(category: Category) {
        var intent = Intent(this, SubCategoryActivity::class.java)
        intent.putExtra(KeyConfig.CATEGORY_ID, category.catId)
        startActivity(intent)
    }

}