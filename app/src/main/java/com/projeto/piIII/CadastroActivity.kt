package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.projeto.piIII.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        binding.buttonCadastrar.setOnClickListener {
            Toast.makeText(this, "FOI PRO LOGIN AMEM SENHOR", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.buttonCadastrar.setOnClickListener{view ->
            val email = binding.editTextNomeCompleto.text.toString()
            val senha = binding.editTextSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "preencha todos os campos! ", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{

            }
        }
    }
}