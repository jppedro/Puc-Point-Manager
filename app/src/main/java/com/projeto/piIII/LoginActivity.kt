package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.projeto.piIII.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageViewVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonEsqueceuSenha.setOnClickListener {
            val intent = Intent(this, EsqueceuSenhaActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth = Firebase.auth
        listener()
    }

    private fun listener(){
        binding.buttonEntrar.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val senha = binding.editTextSenha.text.toString().trim()
            realizarLogin(email,senha)
        }
    }

    private fun realizarLogin(email:String,password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(this,
                        "Login feito com sucesso!",
                        Toast.LENGTH_SHORT)
                        .show()

                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("email",binding.editTextEmail.text.toString())
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}