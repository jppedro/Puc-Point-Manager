package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.projeto.piIII.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //receber id
        if(intent.hasExtra("id")){
            binding.textViewHome.text = intent.getStringExtra("id")
        }

        binding.buttonVoltar.setOnClickListener {
            Toast.makeText(this, "VOLTA PARA MAIN", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonRegistrarPonto.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE REGISTRO DE PONTO", Toast.LENGTH_LONG).show()
            //TODO
            /* Adianda não foi criada
            val intent = Intent(this, PontoActivity::class.java)
            startActivity(intent) */
        }

        binding.buttonAcessarRelatorio.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE RELATŔOIO", Toast.LENGTH_LONG).show()
            //TODO
            /* Adianda não foi criada,
            val intent = Intent(this, RelatorioActivity::class.java)
            startActivity(intent) */
        }

        binding.buttonAcessarCalendario.setOnClickListener {
            Toast.makeText(this, "VAI PARA TELA DE CALENDÁRIO", Toast.LENGTH_LONG).show()
            //TODO
            /* Adianda não foi criada,
            val intent = Intent(this, CalendárioActivity::class.java)
            startActivity(intent) */
        }
    }
}