package com.projeto.piIII

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.projeto.piIII.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {

    lateinit var binding: ActivityCalendarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendarView = findViewById<CalendarView>(R.id.calendario)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // Mostra a data selecionada
            Toast.makeText(this@CalendarActivity, "Data selecionada: $dayOfMonth/${month + 1}/$year", Toast.LENGTH_SHORT).show()
        }
    }
}
