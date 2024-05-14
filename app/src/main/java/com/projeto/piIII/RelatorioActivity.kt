package com.projeto.piIII

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.CardAdapter
import com.example.app.CardData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projeto.piIII.databinding.ActivityRelatorioBinding
import com.projeto.piIII.model.Point

class RelatorioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRelatorioBinding
    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private val listaPoints = mutableListOf<Point>()
    private val cardsList = mutableListOf<CardData>()
    private lateinit var auth:FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        // Obtenha o RecyclerView
        recyclerView = findViewById(R.id.recyclerViewCards)

        // Defina o layout manager para o RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*// Crie os dados para os cards
        val cards = listOf(
            CardData("Ter, 16 de abril", "PUC Campinas", 8, "Entrada"),
            CardData("Qua, 17 de abril", "PUC Campinas", 6, "Saída"),
            CardData("Qui, 18 de abril", "PUC Campinas", 8, "Saída"),
            // Adicione mais dados de card conforme necessário
        )

        // Crie o adaptador e conecte-o ao RecyclerView
        val adapter = CardAdapter(cards)
        recyclerView.adapter = adapter*/

        binding.imageViewVoltar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


        binding.buttonCadastrar.setOnClickListener {
            val intent = Intent(this, RegistroDePonto::class.java)
            startActivity(intent)
        }
        setupFirebase()
        getAllPoints()
    }
    private fun setupFirebase(){
        database = FirebaseDatabase.getInstance().getReference("points").child(auth.currentUser?.uid ?: "Null")
    }

    private fun getAllPoints() {

        database.get().addOnSuccessListener { dataSnapshot ->
            for (childSnapshot in dataSnapshot.children) {
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
}
