package com.paulo.obrigacoes.ui.datetime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.paulo.obrigacoes.databinding.ActivityDateTimeBinding

class DateTimeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDateTimeBinding
    private lateinit var viewModel: DateTimeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDateTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[DateTimeViewModel::class.java]

        configurarEventos()
        observarEstado()
    }

    private fun configurarEventos() {
        binding.toolbar.setNavigationOnClickListener { finish() }
        binding.timePicker.setIs24HourView(true)

        binding.btnCancelar.setOnClickListener {
            finish()
        }

        binding.btnConfirmar.setOnClickListener {
            viewModel.confirmar(
                dataMillis = binding.calendarView.date,
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
