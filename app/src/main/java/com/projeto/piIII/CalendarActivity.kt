package com.projeto.piIII

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.projeto.piIII.databinding.ActivityCalendarBinding
import com.projeto.piIII.model.Atividade

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var saveButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCalendarBinding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //setupFirebase()
        database = Firebase.database.reference
        auth = Firebase.auth

        saveButton = findViewById(R.id.btn_enviar)

        binding.btnEnviar.setOnClickListener {
            val nomeDisciplina = binding.etAtividadeNome.text.toString()
            val horaInicio = binding.etHorarioInicio.text.toString()
            val horaTermino = binding.etHorarioTermino.text.toString()
            val local = binding.etLocal.text.toString()
            val diaDaSemana = binding.etDiaSemana.text.toString()
            registraAtividade(nomeDisciplina,horaInicio,horaTermino,local,diaDaSemana)
        }
    }
    private fun registraAtividade(nomeAtividade:String, horaInicio:String, horaTermino:String, local:String, diaDaSemana:String){
        val userUID = auth.currentUser?.uid ?: "Null"
        val atividadeUUID = database.push().key ?: "Null"
        val atividade = Atividade(atividadeUUID, nomeAtividade, horaInicio, horaTermino, local, diaDaSemana)

        database.child("atividades").child(userUID).child(atividadeUUID).setValue(atividade).addOnCompleteListener {
            task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Atividade criada com sucesso",Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
            else {
                Toast.makeText(this,task.exception?.message,Toast.LENGTH_SHORT).show()
            }
        }
    }
}