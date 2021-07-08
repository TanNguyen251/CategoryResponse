package com.example.categoryresponse.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.categoryresponse.R
import com.example.categoryresponse.managers.LoginManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init() {
        login_button_login.setOnClickListener(this)
        login_text_signup.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v){
            login_button_login -> {
                if (login_email.text.isEmpty()){
                    login_email.error = "Please enter your email"
                    login_email.requestFocus()
                    return
                }
                if (login_password.text.isEmpty()){
                    login_password.error = "Please enter your password"
                    login_password.requestFocus()
                    return
                }
                var loginEmail = login_email.text.toString()
                var loginPassword = login_password.text.toString()
                var loginManager = LoginManager(this)
                loginManager.logIn(loginEmail, loginPassword)
            }

            login_text_signup -> {
                var intent = Intent (this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

}