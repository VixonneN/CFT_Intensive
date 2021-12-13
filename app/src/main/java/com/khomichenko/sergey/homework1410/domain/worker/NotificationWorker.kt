package com.khomichenko.sergey.homework1410.domain.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.khomichenko.sergey.homework1410.R

class NotificationWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val data = inputData.getString("fio_loan")
        val amount = inputData.getInt("amount_loan", 0)
        displayNotification("Займы онлайн",
            "Уважаемый $data, не забудьте погасить займ в размере $amount")
        return Result.success()
    }

    private fun displayNotification(title: String, task: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                applicationContext.getString(R.string.notificationWorker),
                applicationContext.getString(R.string.notificationWorker),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
        val notification =
            NotificationCompat.Builder(applicationContext,
                applicationContext.getString(R.string.notificationWorker))
                .setContentTitle(title)
                .setContentText(task)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
        notificationManager.notify(1, notification.build())
    }
}