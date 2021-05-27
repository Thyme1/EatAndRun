package com.thyme.eatandrun.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thyme.todolist.R


public class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.toolbar))

//        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        supportActionBar!!.setDisplayShowTitleEnabled(false)
    }
}