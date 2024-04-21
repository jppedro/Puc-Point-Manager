package com.projeto.piIII

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.projeto.piIII.databinding.ActivityRegistroDePontoBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

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
                    val registrarPontoBemSucedido = registrarPonto()
                    val dialog = Dialog(this)

                    dialog.setContentView(R.layout.popup_layout)
                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    if(registrarPontoBemSucedido){
                        dialog.findViewById<TextView>(R.id.popupText1).text = "Ponto Registrado"
                        dialog.findViewById<TextView>(R.id.popupText2).text = "Seu ponto de entrada/saída foi registrado, até uma próxima!"

                        val acessarRelatorioButton = dialog.findViewById<Button>(R.id.acessarRelatorioButton)

                        acessarRelatorioButton.setOnClickListener {
                            // Navegar para a tela de relatório
                            // val intent = Intent(this, RelatorioActivity::class.java)
                            // startActivity(intent)
                            // dialog.dismiss()
                        }

                        val fecharButton = dialog.findViewById<Button>(R.id.fecharButton)
                        fecharButton.setOnClickListener {
                            dialog.dismiss()
                        }
                    } else{
                        dialog.findViewById<TextView>(R.id.popupText1).text = "Ponto NÃO Registrado"
                        dialog.findViewById<TextView>(R.id.popupText2).text = "Seu ponto de entrada/saída não foi registrado, tente novamente!"

                        val acessarRelatorioButton = dialog.findViewById<Button>(R.id.acessarRelatorioButton)
                        acessarRelatorioButton.setOnClickListener {
                            // Navegar para a tela de relatório
                            // val intent = Intent(this, RelatorioActivity::class.java)
                            // startActivity(intent)
                            // dialog.dismiss()
                        }

                        // val tentarNovamenteButton = dialog.findViewById<Button>(R.id.tentarNovamenteButton)
                        // tentarNovamenteButton.setOnClickListener {
                        //    // Tentar registrar o ponto novamente
                        //    dialog.dismiss()
                        //    binding.buttonRegistrarPonto.performClick()
                        // }
                    }

                    dialog.show()

                }
            }
    fun registrarPonto():Boolean{
        val horarioData = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val dataFormatada = dateFormat.format(horarioData.time)

        println("Horário atual: $dataFormatada")

        // Adicionar lógica para salvar a data no banco de dados e retornar true para a ação bem sucedida e false caso contrário

        return false
    }
}