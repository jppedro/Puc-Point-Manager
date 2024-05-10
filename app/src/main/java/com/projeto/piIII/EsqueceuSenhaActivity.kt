package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.projeto.piIII.databinding.ActivityEsqueceuSenhaBinding
import com.projeto.piIII.databinding.ActivityRelatorioBinding

class EsqueceuSenhaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEsqueceuSenhaBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.buttonEnviar.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "E-mail de redefinição enviado com sucesso!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val errorMessage = task.exception?.message ?: "Ocorreu um erro ao enviar o e-mail."
                            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor, insira um endereço de e-mail.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageViewVoltar.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}