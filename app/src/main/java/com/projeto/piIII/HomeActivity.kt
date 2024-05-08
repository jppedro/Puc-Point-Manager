package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.projeto.piIII.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val user = Firebase.auth.currentUser
    val userName = user?.displayName
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        database = Firebase.database.reference

        val textView = findViewById<TextView>(R.id.textViewHome)
        textView.text = "Bem-vindo, ${user?.email}"

        listener()
    }

    fun listener(){
        binding.buttonRegistrarPonto.setOnClickListener {
            val intent = Intent(this, RegistroDePonto::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonAcessarRelatorio.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE RELATŔOIO", Toast.LENGTH_LONG).show()
        }

        binding.buttonAcessarCalendario.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE CALENDÁRIO", Toast.LENGTH_LONG).show()
        }
    }
}