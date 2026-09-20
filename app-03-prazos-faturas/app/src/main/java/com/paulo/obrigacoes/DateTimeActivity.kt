package com.paulo.obrigacoes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.paulo.obrigacoes.databinding.ActivityDateTimeBinding
import java.util.Calendar

class DateTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateTimeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.timePicker.setIs24HourView(true)

        binding.btnCancelar.setOnClickListener { finish() }

        binding.btnConfirmar.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.timeInMillis = binding.calendarView.date
            cal.set(Calendar.HOUR_OF_DAY, binding.timePicker.hour)
            cal.set(Calendar.MINUTE, binding.timePicker.minute)
            cal.set(Calendar.SECOND, 0)

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_MILLIS, cal.timeInMillis)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_MILLIS = "extra_millis"
    }
}
