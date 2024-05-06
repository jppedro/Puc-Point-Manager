package com.projeto.piIII

import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.projeto.piIII.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        lateinit var binding: ActivityCalendarBinding
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val datePicker = findViewById<DatePicker>(R.id.datePicker1)
        val timePicker = findViewById<TimePicker>(R.id.timePicker1)
        timePicker.setIs24HourView(true) // Define o formato de 24 horas para o TimePicker

        // Exemplo de como obter a data selecionada
        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year" // mês é base zero, então adicionamos 1
            Toast.makeText(this, "Data selecionada: $selectedDate", Toast.LENGTH_SHORT).show()

            // Exemplo de como obter a data e hora selecionadas
            datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year" // mês é base zero, então adicionamos 1
                val selectedHour = timePicker.hour
                val selectedMinute = timePicker.minute
                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                val dateTime = "$selectedDate $selectedTime"
                Toast.makeText(this, "Data e hora selecionadas: $dateTime", Toast.LENGTH_SHORT).show()


            }
        }

    }
}
