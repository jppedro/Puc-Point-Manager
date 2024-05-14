package com.projeto.piIII.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Atividade(
    val id: String? = null,
    var nomeDisciplina: String = "",
    var horaInicio: String = "",
    var horaTermino: String = "",
    var local: String = "",
    var diaDaSemana: String = ""
) : Parcelable
