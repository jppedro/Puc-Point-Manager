package com.projeto.piIII

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.projeto.piIII.databinding.ActivityRegistroDePontoBinding
import com.projeto.piIII.enum.PointType
import com.projeto.piIII.model.Point
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class RegistroDePonto : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroDePontoBinding
    private lateinit var database: DatabaseReference
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityRegistroDePontoBinding.inflate(layoutInflater)
                setContentView(binding.root)
                database = Firebase.database.reference

                binding.imageViewVoltar.setOnClickListener {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
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
        val funcUUID = Firebase.auth.currentUser?.uid

        val ponto = funcUUID?.let {createPoint(it, dataFormatada, PointType.ENTRADA)}

        database.child("points").child(ponto.toString()).setValue(ponto)
        println("Horário atual: $dataFormatada")

        return true
    }

    fun createPoint(funcUID: String, registerDate: String, pointType: PointType): Point{
        val pointUuid = UUID.randomUUID()
        return Point(pointUuid, funcUID, registerDate, pointType)
    }
}