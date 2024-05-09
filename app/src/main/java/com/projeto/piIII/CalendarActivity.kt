// package com.projeto.piIII

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.projeto.piIII.databinding.ActivityCalendarBinding
class CalendarActivity : AppCompatActivity() {
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var registerButton: Button
    private lateinit var reminderText: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCalendarBinding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datePicker = binding.datePicker1
        timePicker = binding.timePicker1
        registerButton = binding.registerButton
        reminderText = binding.Lembrete

        timePicker.setIs24HourView(true) // Define o formato de 24 horas para o TimePicker

        // Adicionando um ouvinte de foco para o TextView
        reminderText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                reminderText.text = ""
            } else {
                // Caso o texto seja apagado e não seja digitado nada, o texto original é restaurado
                if (reminderText.text.toString().isEmpty()) {
                    reminderText.text = "Digite seu texto"
                }
            }
        }

        // Exemplo de como obter a data e hora selecionadas
        registerButton.setOnClickListener {
            val selectedDate = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val selectedHour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) timePicker.hour else timePicker.currentHour
            val selectedMinute = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) timePicker.minute else timePicker.currentMinute
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            val dateTime = "$selectedDate $selectedTime"
            Toast.makeText(this, "Data e hora selecionadas: $dateTime", Toast.LENGTH_SHORT).show()
        }
    }
}
