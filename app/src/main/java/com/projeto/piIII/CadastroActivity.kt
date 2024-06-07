package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projeto.piIII.databinding.ActivityCadastroBinding
import com.projeto.piIII.model.Funcionario

class CadastroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCadastrar.setOnClickListener {
            auth = Firebase.auth
            setupFirebase()
            listener()
        }

        binding.imageViewVoltar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun listener(){
        binding.buttonCadastrar.setOnClickListener {view ->
            val email = binding.editTextEmail.text.toString().trim()
            val senha = binding.editTextSenha.text.toString().trim()
            val nome = binding.editTextNomeCompleto.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "preencha todos os campos! ", Snackbar.LENGTH_SHORT)
                snackbar.show()
            }else{
                cadastrarConta(email, senha, nome)
            }
        }
    }

    private fun cadastrarConta(email:String, password: String, nome: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this,
                        "Nova conta criada com sucesso!",
                        Toast.LENGTH_SHORT)
                        .show()
                    val funcionario = Funcionario(auth.currentUser?.uid, nome, email)
                    registrarConta(funcionario)
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    } else {
                    Toast.makeText(this,
                        task.exception?.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

    private fun setupFirebase(){
        database = FirebaseDatabase.getInstance().getReference("funcionarios")
    }

    private fun registrarConta(funcionario: Funcionario){
        database.setValue(funcionario).addOnSuccessListener {
            Toast.makeText(this,
                "Funcionário registrado com sucesso!",
                Toast.LENGTH_SHORT)
                .show()
         }.addOnFailureListener{
            Toast.makeText(this,
                "Erro ao registrar funcionario",
                Toast.LENGTH_SHORT)
                .show()
            println(it.stackTrace)
        }
    }
}
