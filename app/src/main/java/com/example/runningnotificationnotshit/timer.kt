package com.example.runningnotificationnotshit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer
import kotlinx.android.synthetic.main.activity_timer.*

class timer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)
        startTimer(1,1,1)
    }
 //   var view_timerxd = findViewById<Chronometer>(R.id.view_timer)
    fun startTimer(numberOfPeriods: Int, sprintLength: Int, warmupLength: Int) {

        view_timer.start()
        view_timer.isCountDown = true
        view_timer.base = SystemClock.elapsedRealtime() + 6000
        var elapsedMillis = SystemClock.elapsedRealtime()
        view_timer.setOnChronometerTickListener {
            //ez nem mukodik nem ertem viszont hogy miert nem, lol
            Log.e("alma ", elapsedMillis.toString())
        }




    }
}