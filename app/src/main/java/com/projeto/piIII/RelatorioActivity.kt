package com.example.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projeto.piIII.HomeActivity
import com.projeto.piIII.R
import com.projeto.piIII.databinding.ActivityRegistroDePontoBinding
import com.projeto.piIII.databinding.ActivityRelatorioBinding

class RelatorioActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRelatorioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelatorioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtenha o RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCards)

        // Defina o layout manager para o RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Crie os dados para os cards
        val cards = listOf(
            CardData("Ter, 16 de abril", "PUC Campinas", 8, "Entrada"),
            CardData("Qua, 17 de abril", "PUC Campinas", 6, "Saída"),
            CardData("Qui, 18 de abril", "PUC Campinas", 8, "Saída"),
            // Adicione mais dados de card conforme necessário
        )

        // Crie o adaptador e conecte-o ao RecyclerView
        val adapter = CardAdapter(cards)
        recyclerView.adapter = adapter

        binding.imageViewVoltar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonCadastrar.setOnClickListener {
            val intent = Intent(this, Regis::class.java)
            startActivity(intent)
            finish()
        }
    }
}
