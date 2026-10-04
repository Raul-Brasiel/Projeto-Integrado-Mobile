package com.paulo.obrigacoes.ui.datetime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.paulo.obrigacoes.databinding.ActivityDateTimeBinding
import java.util.Calendar

class DateTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateTimeBinding
    private lateinit var viewModel: DateTimeViewModel

    // Guarda o dia que o usuário tocou no calendário (começa em hoje)
    private var dataEscolhida: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDateTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DateTimeViewModel::class.java]

        dataEscolhida = binding.calendarView.date

        configurarEventos()
        observarEstado()
    }

    private fun configurarEventos() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.timePicker.setIs24HourView(true)

        binding.calendarView.setOnDateChangeListener { _, ano, mes, dia ->
            val cal = Calendar.getInstance().apply {
                set(ano, mes, dia, 0, 0, 0)
                set(Calendar.MILLISECOND, 0)
            }
            dataEscolhida = cal.timeInMillis
        }

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnConfirmar.setOnClickListener {
            viewModel.confirmar(
                dataMillis = dataEscolhida,
                hora = binding.timePicker.hour,
                minuto = binding.timePicker.minute
            )
        }
    }

    private fun observarEstado() {
        viewModel.dataSelecionada.observe(this) { millis ->
            val resultIntent = Intent().apply {
                putExtra(EXTRA_MILLIS, millis)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_MILLIS = "extra_millis"
    }
}