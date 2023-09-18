package com.example.fimeff

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment

class BibliotecaFragment : Fragment() {

   companion object {
      const val CHANNEL_ID = "com.example.fimeff"
      const val NOTIFICATION_ID = 1010
      const val REPLY = "reply_action"
   }

   fun create(title: String, manager: String) {

      val intent = Intent(context, MainActivity2::class.java).apply {
         flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
      }

      val pendingIntent = PendingIntent.getActivity(
         context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
      )

      val notificationManager =
         requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

      val builder: NotificationCompat.Builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

         val notificationChannel = NotificationChannel(
            CHANNEL_ID, "channel_name", NotificationManager.IMPORTANCE_DEFAULT
         )
         notificationManager.createNotificationChannel(notificationChannel)
         NotificationCompat.Builder(requireContext(), notificationChannel.id)
      } else {
         NotificationCompat.Builder(requireContext())
      }

      builder
         .setLargeIcon(
            BitmapFactory.decodeResource(
               resources,
               android.R.drawable.ic_dialog_info
            )
         )
         .setSmallIcon(android.R.drawable.ic_dialog_info)
         .setContentTitle(title)
         .setContentText(manager)
         .setDefaults(NotificationCompat.DEFAULT_ALL)
         .setAutoCancel(true)
         .setContentIntent(pendingIntent)

      notificationManager.notify(NOTIFICATION_ID, builder.build())
   }
}





