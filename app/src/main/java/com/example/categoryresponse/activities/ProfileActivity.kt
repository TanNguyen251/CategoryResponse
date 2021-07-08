package com.example.categoryresponse.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.managers.LoginManager
import com.example.categoryresponse.models.UserResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.grocery_toolbar.*
import org.json.JSONObject

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
        grocery_toolbar.title = "Your Profile"
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
                onRestart()
                //startActivity(Intent(this, AddAddressActivity::class.java))
            }
            R.id.grocery_logout -> {
                finish()
                var loginManager = LoginManager(this)
                loginManager.logout()
            }
            R.id.grocery_setting -> {

            }
        }
        return true
    }


    private fun init(){
        var jsonObject = JSONObject()
        var userId = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE).getString(KeyConfig.USER_ID, "")
        Log.d("abcd", "$userId")
        var requestQueue = Volley.newRequestQueue(this)
        Log.d("abcd", EndpointsConfig.getUserUrl(userId))
        var request = JsonObjectRequest(
            Request.Method.GET,
            EndpointsConfig.getUserUrl(userId),
            jsonObject,
            {
                var gson = Gson()
                var userResponse = gson.fromJson(it.toString(), UserResponse::class.java)
                var user = userResponse.data
                profile_name.text = "Name: ${user.firstName}"
                profile_email.text = "Email: ${user.email}"
                profile_mobile.text = "Phone: ${user.mobile}"
                var sharedPreferences = getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE)
                var editor = sharedPreferences.edit()
                editor.putString(KeyConfig.FIRST_NAME, user.firstName)
                editor.putString(KeyConfig.EMAIL, user.email)
                editor.putString(KeyConfig.MOBILE, user.mobile)
                editor.commit()

            },
            {
                Log.d("abcd", "Unable to request")
            }
        )
        requestQueue.add(request)

    }
}