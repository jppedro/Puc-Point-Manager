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
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        listener()
    }

    private fun listener(){
        binding.buttonEntrar.setOnClickListener {
            val email = binding.editTextID.text.toString().trim()
            val senha = binding.editTextSenha.text.toString().trim()
            realizarLogin(email,senha)

            Toast.makeText(this, "FOI PRA HOME AMEM SENHOR", Toast.LENGTH_LONG).show()
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("id",binding.editTextID.text.toString()) //mandando ID para próxima tela
            startActivity(intent)
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
                } else {
                    Toast.makeText(this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}