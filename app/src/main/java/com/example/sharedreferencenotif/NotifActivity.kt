package com.example.sharedreferencenotif

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sharedreferencenotif.databinding.ActivityNotifBinding

class NotifActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotifBinding
    private val channelId = "TEST_NOTIF"

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNotifBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        with(binding) {
            btnNotif.setOnClickListener {
                val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    PendingIntent.FLAG_IMMUTABLE
                } else {
                    0
                }

//                 val intent = Intent(this@NotifActivity, NotifReceiver::class.java).putExtra("MESSAGE", "Baca selengkapnya ...")
//
//                 val pendingIntent = PendingIntent.getBroadcast(
//                     this@NotifActivity,
//                     0,
//                     intent,
//                     flag
//                 )

                val intent = Intent(this@NotifActivity, MainActivity::class.java)

                val pendingIntent = PendingIntent.getActivity(
                    this@NotifActivity,
                    0,
                    intent,
                    flag
                )

                val builder = NotificationCompat.Builder(this@NotifActivity, channelId)
                    // Use channelId here
                    .setSmallIcon(R.drawable.baseline_notifications_24).setContentTitle("Notification")
                    .setContentText("Toko Pak Haji")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .addAction(0, "Baca Notif", pendingIntent)
                    .setContentIntent(pendingIntent)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val notifChannel = NotificationChannel(
                        channelId, // Use channelId here
                        "Notifku",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    with(notifManager) {
                        createNotificationChannel(notifChannel)
                        notify(0, builder.build())
                    }
                } else {
                    notifManager.notify(0, builder.build())
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}