package com.projeto.piIII

import CalendarActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.projeto.piIII.databinding.ActivityCalendar2Binding

class Calendar2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendar2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCalendar2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            Toast.makeText(this, "Data selecionada: $selectedDate", Toast.LENGTH_SHORT).show()
            // Adicione aqui o código adicional que deseja executar quando a data for alterada
        }

        binding.btnAdd.setOnClickListener {
            // Ir para a tela do calendário (CalendarActivity)
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            // Voltar para a tela inicial (HomeActivity)
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
