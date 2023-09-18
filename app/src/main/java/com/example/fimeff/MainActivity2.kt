package com.example.fimeff

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity2 : AppCompatActivity() {

    private lateinit var myButton: Button
    private val replyInputKey = "com.example.fimeff"
    private val notificationChannelId = "1010"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_biblioteca)

        myButton = findViewById(R.id.myButton)

        myButton.setOnClickListener {
            createNotification("Notification Title", "Notification Message")
        }

        receiveReplyInput()
    }

    private fun receiveReplyInput() {
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)?.getCharSequence(replyInputKey)

        if (remoteInput != null) {
            val inputReplyString = remoteInput.toString()
            myButton.text = inputReplyString

            val notificationId = 1010
            val updateCurrentNotification =
                NotificationCompat.Builder(this@MainActivity2, notificationChannelId)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            resources,
                            android.R.drawable.ic_dialog_info
                        )
                    )
                    .setContentTitle("Mensagem com Sucesso")
                    .setContentText("Atualização de Mensagem")
                    .build()

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, updateCurrentNotification)
        }
    }

    private fun createNotification(title: String, message: String) {

    }
}
