package com.paulo.obrigacoes

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build

object NotificationScheduler {

    fun agendar(context: Context, obligationId: Long, label: String) {
        val obligation = ObligationRepository.getById(obligationId) ?: return

        val diasAntes = when (label) {
            context.getString(R.string.opcao_no_dia) -> 0
            context.getString(R.string.opcao_3_dias_antes) -> 3
            else -> 1
        }
        val disparoMillis = obligation.vencimentoMillis - diasAntes * 24 * 60 * 60 * 1000

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("descricao", obligation.descricao)
            putExtra("id", obligationId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, obligationId.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (disparoMillis > System.currentTimeMillis()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, disparoMillis, pendingIntent)
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, disparoMillis, pendingIntent)
            }
        }
    }

    fun cancelar(context: Context, obligationId: Long) {
        val intent = Intent(context, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context, obligationId.toInt(), intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
