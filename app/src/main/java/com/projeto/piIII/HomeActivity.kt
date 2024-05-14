package com.projeto.piIII

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.app.CardAdapter
import com.example.app.CardData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projeto.piIII.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName
    private lateinit var recyclerView: RecyclerView
    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
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
            getAllPoints()
            val intent = Intent(this, RelatorioActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAcessarCalendario.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        binding.imageViewLogout.setOnClickListener {
            performLogout()
        }
        recyclerView = findViewById(R.id.recyclerViewCards)
        recyclerView.layoutManager = LinearLayoutManager(this)
        database = FirebaseDatabase.getInstance().getReference("points")
    }

    private fun performLogout(){
        Firebase.auth.signOut()
        Toast.makeText(this, "Logout realizado com sucesso!", Toast.LENGTH_LONG).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun getAllPoints() {
        /*var message = ""
        database.get().addOnSuccessListener {
            listaPoints.clear()
            for(childSnapshot in it.children){
                Log.d("Antes", childSnapshot.toString())
                val uuid = childSnapshot.key ?: ""
                val Informacoes =
                    childSnapshot.value?: ""
                val Informacoes1 = Informacoes.
                Log.d("Antes", Informacoes.toString())
            }
            message = "Dados recuperados"

        }.addOnFailureListener{
            message = "Erro ao acessar o banco de dados ${it.message}"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT)*/


        val cardsList = mutableListOf<CardData>()

        database.get().addOnSuccessListener { dataSnapshot ->
            for (childSnapshot in dataSnapshot.children) {
                val uuid = childSnapshot.key ?: ""
                val pointData = childSnapshot.value as? Map<String, Any>

                pointData?.let {
                    val card = CardData(
                        pointType = it["pointType"].toString(),
                        registerDate = it["registerDate"].toString(),
                    )
                    cardsList.add(card)
                }
            }

            updateRecyclerView(cardsList)

        }.addOnFailureListener { exception ->
            // Lidar com falha ao acessar o banco de dados
            val message = "Erro ao acessar o banco de dados ${exception.message}"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateRecyclerView(cards: List<CardData>) {
        val adapter = CardAdapter(cards)
        recyclerView.adapter = adapter
    }
    private fun setupFirebase(){

    }
}