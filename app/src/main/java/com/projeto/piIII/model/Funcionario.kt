package com.projeto.piIII.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Funcionario(
    val uuid: String? = "",
    var nome: String? = "",
    var email: String = ""
) : Parcelable