package com.example.runningnotificationnotshit

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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

        fulltimer(warmup = warmupGlobal.toInt(), sprint = sprintGlobal.toInt(),howManySprint = hanypercGlobal.toInt())
        createNotificationChannel()
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
            fulltimer(warmup = warmupGlobal.toInt(), sprint = sprintGlobal.toInt(),howManySprint = hanypercGlobal.toInt())
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
                    sendNotification()
                }
                if (tasksCompleted == 2 + 2*y) {

                    //jogging
                    sendNotification()
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(R.string.channel_id.toString(), name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun sendNotification(){
        val intent = Intent(this,timer::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this, R.string.channel_id.toString())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("TestTitle")
            .setContentText("TestText")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(2, builder.build())
        }


    }
}
