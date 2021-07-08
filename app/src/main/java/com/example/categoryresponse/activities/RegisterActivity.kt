package com.example.categoryresponse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.categoryresponse.R
import com.example.categoryresponse.configs.EndpointsConfig
import com.example.categoryresponse.configs.KeyConfig
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject

class RegisterActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
    }

    private fun init(){
        register_button_signup.setOnClickListener(this)
        register_text_login.setOnClickListener(this)
    }
    private fun register(name: String, email: String, password: String, mobile: String){
        var jsonObject = JSONObject()
        jsonObject.put(KeyConfig.FIRST_NAME, name)
        jsonObject.put(KeyConfig.EMAIL, email)
        jsonObject.put(KeyConfig.PASSWORD,password)
        jsonObject.put(KeyConfig.MOBILE,mobile)

        var requestQueue = Volley.newRequestQueue(this)

        var request = JsonObjectRequest(
            Request.Method.POST,
            EndpointsConfig.getRegisterUrl(),
            jsonObject,
            {
                Log.d("tan nguyen", it.toString())
            },
            {
                Log.e("tan nguyen", it.message.toString())
            }
        )

        requestQueue.add(request)

    }

    override fun onClick(v: View) {
        when (v){
            register_button_signup -> {
                if (register_name.text.isNotEmpty() && register_email.text.isNotEmpty() && register_password.text.isNotEmpty()) {

                    var name = register_name.text.toString()
                    var email = register_email.text.toString()
                    var password = register_password.text.toString()
                    var mobile = register_mobile.text.toString()
                    register(name, email, password, mobile)

                    var intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Failed to register", Toast.LENGTH_LONG).show()
                }
            }
            register_text_login -> {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}