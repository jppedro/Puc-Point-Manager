package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.projeto.piIII.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textView = findViewById<TextView>(R.id.textViewHome)
        textView.text = "Bem-vindo, $userName"

        if(intent.hasExtra("id")){
            binding.textViewHome.text = intent.getStringExtra("id")
        }x

        binding.buttonRegistrarPonto.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE REGISTRO DE PONTO", Toast.LENGTH_LONG).show()
        }

        binding.buttonAcessarRelatorio.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE RELATŔOIO", Toast.LENGTH_LONG).show()
        }

        binding.buttonAcessarCalendario.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE CALENDÁRIO", Toast.LENGTH_LONG).show()
        }
    }
}