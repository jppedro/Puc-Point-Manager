package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.app.RelatorioActivity
import com.google.firebase.auth.FirebaseAuth
import com.projeto.piIII.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textView = findViewById<TextView>(R.id.textViewHome)
        textView.text = "Bem-vindo, $user"

        binding.buttonRegistrarPonto.setOnClickListener {
            val intent = Intent(this, RegistroDePonto::class.java)
            startActivity(intent)
        }

        // Função para acessar a tela de relatório, que é a tela que possui os cards a serem renderizados de acordo com a quantidade de elementos em um determiando array
        binding.buttonAcessarRelatorio.setOnClickListener {
            val intent = Intent(this, RelatorioActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAcessarCalendario.setOnClickListener {
            val intent = Intent(this, RegistroDePonto::class.java)
            startActivity(intent)
        }

        binding.imageViewLogout.setOnClickListener {
            performLogout()
        }
    }

    private fun performLogout(){
        auth.signOut()
        Toast.makeText(this, "Logout realizado com sucesso!", Toast.LENGTH_LONG).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}