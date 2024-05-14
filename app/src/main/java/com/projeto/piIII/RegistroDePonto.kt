package com.projeto.piIII

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.projeto.piIII.databinding.ActivityRegistroDePontoBinding
import com.projeto.piIII.enum.PointType
import com.projeto.piIII.model.Point
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
class RegistroDePonto : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroDePontoBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private val listaPoints = mutableListOf<Point>()

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityRegistroDePontoBinding.inflate(layoutInflater)
                setContentView(binding.root)

                auth = Firebase.auth
                setupFirebase()
                listener()
            }

    fun listener(){
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

            val fecharButton = dialog.findViewById<Button>(R.id.fecharButton)
            val acessarRelatorioButton = dialog.findViewById<Button>(R.id.acessarRelatorioButton)

            if(registrarPontoBemSucedido){
                dialog.findViewById<TextView>(R.id.popupText1).text = "Ponto Registrado"
                dialog.findViewById<TextView>(R.id.popupText2).text = "Seu ponto foi registrado, até uma próxima!"


                acessarRelatorioButton.setOnClickListener {
                    val intent = Intent(this, RelatorioActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }

                fecharButton.setOnClickListener {
                    dialog.dismiss()
                }
            } else{
                dialog.findViewById<TextView>(R.id.popupText1).text = "Ponto NÃO Registrado"
                dialog.findViewById<TextView>(R.id.popupText2).text = "Seu ponto de entrada/saída não foi registrado, tente novamente!"

                acessarRelatorioButton.setOnClickListener {
                    val intent = Intent(this, RelatorioActivity::class.java)
                    startActivity(intent)
                    dialog.dismiss()
                }
                fecharButton.setOnClickListener {
                    dialog.dismiss()
                }
            }

            dialog.show()

        }
    }
    fun registrarPonto():Boolean{
        try{
            val horarioData = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val dataFormatada = dateFormat.format(horarioData.time)

            var registrado = true

            var ponto: Point? = setPoint()

            database.child(ponto?.uuid.toString()).setValue(ponto).addOnSuccessListener {
                Toast.makeText(this,
                    "Ponto de ${ponto?.pointType} registrado com sucesso!",
                    Toast.LENGTH_SHORT)
                    .show()
                registrado = true
            }.addOnFailureListener{
                Toast.makeText(this,
                    "Erro ao registrar ponto",
                    Toast.LENGTH_SHORT)
                    .show()
                println(it.stackTrace)
                registrado = false
            }

            Toast.makeText(this, "as", Toast.LENGTH_LONG)
            println("Horário atual: $dataFormatada")
            return registrado
        } catch(e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG)
        }
        return false
    }

    private fun setupFirebase(){
        database = FirebaseDatabase.getInstance().getReference("points").child(auth.currentUser?.uid ?: "Null")
    }



    private fun createPoint(registerDate: String, pointType: PointType): Point{
        val pointUuid = UUID.randomUUID()
        val userUID = FirebaseAuth.getInstance().currentUser?.uid ?: "Null"
        return Point(pointUuid.toString(), registerDate, pointType.name, userUID)
    }

    private fun setPoint():Point{
        val horarioData = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val dataFormatada = dateFormat.format(horarioData.time)
        var ponto: Point?

        val latestPoint = listaPoints.maxByOrNull { LocalDateTime.parse(it.registerDate, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) }
        if (latestPoint == null)
            ponto = createPoint(dataFormatada, PointType.ENTRADA)
        else {
            if (latestPoint.pointType == PointType.ENTRADA.toString())
                ponto = createPoint(dataFormatada, PointType.SAIDA)
            else
                ponto = createPoint(dataFormatada, PointType.ENTRADA)
        }

        return ponto
    }
}