package com.projeto.piIII

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.projeto.piIII.databinding.ActivityRegistroDePontoBinding

class RegistroDePonto : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroDePontoBinding
            override fun onCreate(savedInstanceState: Bundle?) {
                binding = ActivityRegistroDePontoBinding.inflate(layoutInflater)
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_registro_de_ponto)

                if(intent.hasExtra("id")) {
                    binding.textViewRegistroDePonto.text = intent.getStringExtra("id")
                }

                binding.buttonVoltar.setOnClickListener {
                    Toast.makeText(this, "VOLTA PARA HOME", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }

                binding.buttonRegistrarPonto.setOnClickListener {
                    // Adicionar lógica para registrar o horário atual, quando clicar no botão com o id `buttonRegistrarPonto`
                    //
                    // Feito isso, deve exibir um elemento (como um popup) com as seguintes características:
                    // -> height: 312px
                    // -> width: 95%
                    // -> background: #D7B255
                    //
                    // Vai possuir mais 4 elementos centralizados sendo, dois elementos de texto em cima e dois elmentos de botão em baixo
                    // -> Os dois elementos de texto em cima devem ter um gap de 10px entre eles e um gap de 35px com os elementos de baixo
                    // -> Os dois elementos de botão em baixo devem ter um gap de 10px entre eles e um gap de 35px com os de cima
                    //
                    // Caso o horário seja resgistrado com sucesso, devem seguir as seguintes regras:
                    // -> O primeiro elemento de texto deve ter como texto o seguinte: "Ponto Registrado "
                    // -> O segundo elemento de texto deve ter como texto o seguinte: "Seu ponto de entrada/saída foi registrado, até uma próxima!"
                    // -> O primeiro elemento botão deve possuir um ícone ao seu lado esquerdo, junto de um texto: "Acessar Relatório", que deverá ter como funcionalidade a navegação para uma nova tela
                    // -> O segundo elemento deve possuir apenas um texto: "Fechar", que deve ter a funcionalidade de fechar o popup
                    //
                    // Caso o horário não seja registrado com sucesso, devem seguir as seguintes regras:
                    // -> O primeiro elemento de texto deve ter como texto o seguinte: "Ponto NÃO Registrado"
                    // -> O segundo elemento de texto deve ter como texto o seguinte: "Seu ponto de entrada/saída não foi registrado, tente novamente!"
                    // -> O primeiro elemento botão deve possuir um ícone ao seu lado esquerdo, junto de um texto: "Acessar Relatório", que deverá ter como funcionalidade a navegação para uma nova tela
                    // -> O segundo elemento deve possuir apenas um texto: "Tentar Novamente", que deve ter a funcionalidade de registrar o horário atual novamente


                    Toast.makeText(this, "", Toast.LENGTH_LONG).show()
                    //TODO
                    /* Adianda não foi criada
                    val intent = Intent(this, PontoActivity::class.java)
                    startActivity(intent) */
                }
        }
    }
}