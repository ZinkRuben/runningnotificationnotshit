package com.example.runningnotificationnotshit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.reflect.typeOf

var warmupGlobal = ""
var sprintGlobal = ""
var hanypercGlobal = ""

class timer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        //get data from mainActivity
        var data1 = intent.getStringExtra("bemelegitesName").toString()
        var data2 = intent.getStringExtra("sprintName").toString()
        var data3 = intent.getStringExtra("hanypercName").toString()
        warmupGlobal = data1
        sprintGlobal = data2
        hanypercGlobal = data3

        fulltimer(warmup = (warmupGlobal.toInt()*60), sprint = sprintGlobal.toInt(),howManySprint = hanypercGlobal.toInt())

    }
    var x = 0
    var cdFrom = 12
    var tasksCompleted = 0

    fun timerke() {
        if (x<cdFrom) {
            Timer("c", false).schedule(1000) {
                var value = cdFrom - x
                var timeMin = value / 60
                var timeSec = value % 60


                x += 1
                runOnUiThread(java.lang.Runnable {
                    countdownTextView.text = "$timeMin:$timeSec"
                })

                timerke()
            }
        }
        else{
            tasksCompleted += 1
            x = 0
            fulltimer(warmup = (warmupGlobal.toInt()*60), sprint = sprintGlobal.toInt(),howManySprint = hanypercGlobal.toInt())
        }
    }


    fun fulltimer(warmup: Int, sprint: Int, howManySprint: Int ) {

    if (tasksCompleted == 0) {
    //warmup
        cdFrom = warmup
        timerke()
    }

            //sprinting parts
            for(y in 0..(howManySprint-1)) {
                //sprint
                if (tasksCompleted == 1 + 2*y) {

                    cdFrom = sprint
                    timerke()
                }
                if (tasksCompleted == 2 + 2*y) {

                    //jogging

                    cdFrom = (10 - sprint)
                    timerke()
                }
            }
        //cooldown
        if (tasksCompleted == (2*howManySprint+1)) {
            cdFrom = warmup
            timerke()
        }


}


    }
