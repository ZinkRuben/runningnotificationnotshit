package com.example.runningnotificationnotshit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_timer.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.reflect.typeOf

class timer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)


        fulltimer(12,6,3)

    }
    var x = 0
    var cdFrom = 12
    var tasksCompleted = 0

    fun timerke() {

        if (x<cdFrom) {
            Timer("c", false).schedule(1000) {
                var value = cdFrom - x
                x += 1
                runOnUiThread(java.lang.Runnable {
                    countdownTextView.text = value.toString()
                })

                timerke()
            }
        }
        else{
            tasksCompleted += 1
            x = 0
            fulltimer(12,6,3)
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









}


    }
/*
    fun startTimer(numberOfPeriods: Int, sprintLength: Int, warmupLength: Int) {


        view_timer.start()
        view_timer.base = SystemClock.elapsedRealtime() + 6000
        view_timer.setCountDown(true)
        view_timer.setOnChronometerTickListener {
            var elapsedMillis = SystemClock.elapsedRealtime()
            //ez nem mukodik nem ertem viszont hogy miert nem, lol
            Log.e("alma ", elapsedMillis.toString())
            timer
        }

    }


    */
