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
//csinalj ezekkel a nevekkel valamit pls

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
        } else {
            tasksCompleted += 1
            x = 0
            fulltimer(warmup = warmupGlobal.toInt(), sprint = sprintGlobal.toInt(),howManySprint = hanypercGlobal.toInt())
        }
    }

    fun fulltimer (warmup: Int, sprint: Int, howManySprint: Int ) {
        if (tasksCompleted == 0) {
            sendNotification("warmup",false)
            cdFrom = warmup
            timerke()
            //warmup
        }
        for (y in 0 until (howManySprint - 1)) {
            if (tasksCompleted == 1 + 2*y) {
                sendNotification("sprint",false)
                cdFrom = sprint
                timerke()
                //sprint
            }
            if (tasksCompleted == 2 + 2*y) {
                sendNotification("jog",false)
                cdFrom = (10 - sprint)
                timerke()
                //jogging
            }
        }
        if (tasksCompleted == (2*howManySprint+1)) {
            sendNotification("cooldown",true)
            cdFrom = warmup
            timerke()
            //cooldown
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "ActivityChannel"
            val descriptionText = "Activity channel, displays what you should be doing."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(R.string.ac_id.toString(), name, importance).apply {description = descriptionText}
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun sendNotification(displayedText: String, isover: Boolean){
        /*val intent = Intent(this,timer::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)*/
        val builder = NotificationCompat.Builder(this, R.string.ac_id.toString())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Athleanx timed running")
            .setContentText(displayedText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            if (!isover) {
                builder.setOngoing(true)
            } else {
                builder.setOngoing(false)
            }
        with(NotificationManagerCompat.from(this)) {
            notify(2, builder.build())
        }
    }
}
