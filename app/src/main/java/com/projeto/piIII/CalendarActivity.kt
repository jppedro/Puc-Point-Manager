package com.projeto.piIII

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projeto.piIII.databinding.ActivityCalendarBinding
class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var datePicker: DatePicker
    private lateinit var registerButton: Button
    private lateinit var reminderEditText: EditText
    val user = FirebaseAuth.getInstance().currentUser

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCalendarBinding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)


        reminderEditText = findViewById(R.id.reminderEditText)
        datePicker = findViewById(R.id.datePicker1)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            registerReminder()
        }
    }

    private fun registerReminder() {
        val reminderText = reminderEditText.text.toString()
        if (reminderText.isNotEmpty()) {
            // Aqui você pode implementar a lógica para registrar o lembrete, por exemplo, armazenando-o em um banco de dados
            val date = "${datePicker.dayOfMonth}/${datePicker.month + 1}/${datePicker.year}"
            val reminder = "Lembrete: $reminderText\nData: $date"
            Toast.makeText(this, reminder, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Por favor, digite seu texto", Toast.LENGTH_SHORT).show()
        }
    }
}