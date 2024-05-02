package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.projeto.piIII.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        auth = Firebase.auth
        listener()
    }

    private fun listener(){
        binding.buttonCadastrar.setOnClickListener {view ->


            val email = binding.editTextNomeCompleto.text.toString().trim()
            val senha = binding.editTextSenha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "preencha todos os campos! ", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                cadastrarConta(email, senha)
            }

            Toast.makeText(this, "FOI PRO LOGIN AMEM SENHOR", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cadastrarConta(email:String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this,
                        "Nova conta criada com sucesso!",
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