package com.example.runningnotificationnotshit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    var startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {


            val bemelegitesxd = bemelegitesTime.text.toString()
            var sprint = sprintTime.text.toString()
            var hanyperc = hanyPercTime.text.toString()

            val intent = Intent(startButton.context, timer::class.java)
            intent.putExtra("bemelegitesName", bemelegitesxd)
            intent.putExtra("sprintName", sprint)
            intent.putExtra("hanypercName", hanyperc)
            startButton.context.startActivity(intent)
        }

    }
}