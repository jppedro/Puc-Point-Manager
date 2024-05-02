package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.projeto.piIII.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonEntrar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("email",binding.editTextEmail.text.toString())
            startActivity(intent)
            finish()
        }

        binding.textViewCadastro.setOnClickListener {
            val intent = Intent(this,  CadastroActivity::class.java)
            startActivity(intent)
        }
    }
}