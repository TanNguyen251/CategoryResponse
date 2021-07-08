package com.example.categoryresponse.managers

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.activities.GroceryActivity
import com.example.categoryresponse.activities.MainActivity
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import com.example.categoryresponse.databases.DBHelper
import org.json.JSONObject

class LoginManager (var mContext: Context) {
    private var sharedPreferences = mContext.getSharedPreferences(KeyConfig.MY_PROFILE, Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    fun logIn(email: String, password: String) {
        var jsonObjects = JSONObject()
        jsonObjects.put(KeyConfig.EMAIL, email)
        jsonObjects.put(KeyConfig.PASSWORD, password)

        var requestQueue = Volley.newRequestQueue(mContext)
        var request = JsonObjectRequest(
            Request.Method.POST,
            EndpointsConfig.getLoginUrl(),
            jsonObjects,
            {
                var userObject = it.getJSONObject(KeyConfig.KEY_USER)
                var id = userObject.getString(KeyConfig.USER_ID)
                var name = userObject.getString(KeyConfig.FIRST_NAME)
                var email = userObject.getString(KeyConfig.EMAIL)
                var mobile = userObject.getString(KeyConfig.MOBILE)
                editor.putString(KeyConfig.USER_ID, id)
                editor.putString(KeyConfig.FIRST_NAME, name)
                editor.putString(KeyConfig.EMAIL, email)
                editor.putString(KeyConfig.MOBILE, mobile)
                editor.commit()
                var intent = Intent(mContext, GroceryActivity::class.java)
                intent.putExtra(KeyConfig.USER_ID, id)
                mContext.startActivity(intent)
            },
            {
                Toast.makeText(mContext, "Login Failed", Toast.LENGTH_SHORT).show()
            }

        )
        requestQueue.add(request)

    }

    fun logout(){
        var intent = Intent(mContext, MainActivity::class.java)
        var dbHelper = DBHelper(mContext)
        var addressPreference = mContext.getSharedPreferences(KeyConfig.MY_ADDRESS, Context.MODE_PRIVATE)
        var orderPreference = mContext.getSharedPreferences(KeyConfig.MY_ORDER, Context.MODE_PRIVATE)
        addressPreference.edit().clear().commit()
        orderPreference.edit().clear().commit()
        editor.clear().commit()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        dbHelper.deleteAllProduct()
        mContext.startActivity(intent)
    }
}