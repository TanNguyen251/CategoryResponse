package com.example.categoryresponse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.categoryresponse.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.grocery_toolbar.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){
        main_button_register.setOnClickListener(this)
        main_button_login.setOnClickListener(this)
        grocery_toolbar.title = "Home"
    }

    override fun onClick(v: View) {
        when (v){
            main_button_register -> {
                var intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            main_button_login -> {
                var intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}