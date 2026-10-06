package com.paulo.obrigacoes.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.paulo.obrigacoes.data.repository.ObligationRepository

/**
 * Infraestrutura Android responsável por criar/cancelar os alarmes.
 * Não contém regra de tela.
 */
object NotificationScheduler {

    fun agendar(context: Context, obligationId: Long) {
        val obligation = ObligationRepository.getById(obligationId) ?: return

        // Remove um alarme anterior desta obrigação (ex.: ao trocar a opção)
        cancelar(context, obligationId)

        val disparoMillis =
            obligation.vencimentoMillis - obligation.notificacaoDiasAntes * 24L * 60 * 60 * 1000

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("descricao", obligation.descricao)
            putExtra("id", obligationId)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            obligationId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (disparoMillis > System.currentTimeMillis()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        disparoMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    disparoMillis,
                    pendingIntent
                )
            }
        }
    }

    fun cancelar(context: Context, obligationId: Long) {
        val intent = Intent(context, NotificationReceiver::class.java)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            obligationId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)
    }
}
