package com.ajouton.tortee

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


class Login : AppCompatActivity() {
    private var login_email: EditText? = null
    private var login_password: EditText? = null
    private var login_button: Button? = null
    private var join_button: Button? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)
        login_email = findViewById(R.id.login_email)
        login_password = findViewById(R.id.login_password)
        join_button = findViewById(R.id.join_button)
        join_button!!.setOnClickListener {
            val intent = Intent(this@Login, MainActivity::class.java)
            ContextCompat.startActivity(intent)
        }
        login_button = findViewById(R.id.login_button)
        login_button!!.setOnClickListener {
            val UserEmail = login_email!!.text.toString()
            val UserPwd = login_password!!.text.toString()
            val responseListener: Any =
                object : Listener<String?>() {
                    fun onResponse(response: String?) {
                        try {
                            val jsonObject = JSONObject(response)
                            val success = jsonObject.getBoolean("success")
                            if (success) { //로그인 성공시
                                val UserEmail = jsonObject.getString("UserEmail")
                                val UserPwd = jsonObject.getString("UserPwd")
                                val UserName = jsonObject.getString("UserName")
                                Toast.makeText(
                                    ApplicationProvider.getApplicationContext<Context>(),
                                    String.format("%s님 환영합니다.", UserName),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@Login, MainActivity::class.java)
                                intent.putExtra("UserEmail", UserEmail)
                                intent.putExtra("UserPwd", UserPwd)
                                intent.putExtra("UserName", UserName)
                                ContextCompat.startActivity(intent)
                            } else { //로그인 실패시
                                Toast.makeText(
                                    ApplicationProvider.getApplicationContext<Context>(),
                                    "로그인에 실패하셨습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                return
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }
            val loginRequest = LoginRequest(UserEmail, UserPwd, responseListener)
            val queue: RequestQueue = Volley.newRequestQueue(this@Login)
            queue.add(loginRequest)
        }
    }
}