package com.projeto.piIII

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var dialog: Dialog

    private val listaPoints = mutableListOf<Point>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroDePontoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        setupFirebase()
        listener()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        getAllPoints()
    }

    fun listener(){
        binding.imageViewVoltar.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonRegistrarPonto.setOnClickListener {
            dialog = Dialog(this)

            dialog.setContentView(R.layout.popup_layout)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val fecharButton = dialog.findViewById<Button>(R.id.fecharButton)
            val acessarRelatorioButton = dialog.findViewById<Button>(R.id.acessarRelatorioButton)

            registrarPonto()

            acessarRelatorioButton.setOnClickListener {
                val intent = Intent(this, RelatorioActivity::class.java)
                startActivity(intent)
                dialog.dismiss()
            }

            fecharButton.setOnClickListener {
                dialog.dismiss()
            }
        }
    }

    fun setAndShowDialogText(text1: String, text2: String){
        dialog.findViewById<TextView>(R.id.popupText1).text = text1
        dialog.findViewById<TextView>(R.id.popupText2).text = text2

        dialog.show()
    }
    fun registrarPonto(){
        try{
            val horarioData = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val dataFormatada = dateFormat.format(horarioData.time)
            var registrado = true
            getAllPoints()

            var ponto: Point? = setPoint()

            validateLocation { isValidLocation ->
                if(isValidLocation){
                    database.child(ponto?.uuid.toString()).setValue(ponto).addOnSuccessListener {
                        Toast.makeText(this,
                            "Ponto de ${ponto?.pointType} registrado com sucesso!",
                            Toast.LENGTH_SHORT)
                            .show()
                        setAndShowDialogText("Ponto Registrado", "Seu ponto de ${ponto?.pointType} foi registrado, até uma próxima!")
                    }.addOnFailureListener{
                        Toast.makeText(this,
                            "Erro ao registrar ponto",
                            Toast.LENGTH_SHORT)
                            .show()
                        println(it.stackTrace)
                    }
                } else {
                    Toast.makeText(this,
                        "Dispositivo fora da area registrada",
                        Toast.LENGTH_SHORT)
                        .show()
                    setAndShowDialogText("Ponto NÃO Registrado", "Seu ponto de ${ponto?.pointType} não foi registrado, tente novamente!")
                }
            }
            println("Horário atual: $dataFormatada")
        } catch(e: Exception) {
            println(e.message)
        }
    }

    private fun setupFirebase(){
        database = FirebaseDatabase.getInstance().getReference("points").child(auth.currentUser?.uid ?: "Null")
    }

    private fun getAllPoints() {
        var message = ""
        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listaPoints.clear()
                for(childSnapshot in snapshot.children){
                    val uuid = childSnapshot.key ?: ""
                    val registerDate =
                        childSnapshot.child("registerDate").getValue(String::class.java) ?: ""
                    val pointType =
                        childSnapshot.child("pointType").getValue(String::class.java) ?: ""
                    val point = Point(uuid, registerDate, pointType)
                    listaPoints.add(point)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Erro ao recuperar lista de pontos do usuario")
            }
        })
        Toast.makeText(this, message, Toast.LENGTH_SHORT)
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

    private fun validateLocation(callback: (Boolean) -> Unit){
        val targetLatitude = 37.845509
        val targetLongitude = -122.026596
        val radiusInMeters = 100000.0

        getLastLocation { location ->
            if(location != null){
                val distanceFromArea = calculateDistance(location.latitude, location.longitude, targetLatitude, targetLongitude)
                val valido = distanceFromArea <= radiusInMeters
                callback(valido)
            } else {
                validateLocation(callback)
            }
        }
    }
    private fun getLastLocation(callback: (Location?) -> Unit){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
        }

        val locationTask = fusedLocationClient.lastLocation

        locationTask.addOnSuccessListener{
            callback(it)
        }.addOnFailureListener{
            Toast.makeText(this, "Erro ao acessar a ultima localizacao do dispositivo", Toast.LENGTH_SHORT).show()
            println(it.message)
            callback(null)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 101){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("Debug: ", "Permissão autorizada")
            }
        }
    }

    private fun calculateDistance(
        startLatitude: Double,
        startLongitude: Double,
        endLatitude: Double,
        endLongitude: Double
    ): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            startLatitude,
            startLongitude,
            endLatitude,
            endLongitude,
            results
        )
        return results[0]
    }
}