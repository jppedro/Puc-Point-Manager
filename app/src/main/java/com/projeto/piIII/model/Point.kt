package com.projeto.piIII.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point (
    var uuid: String? = "",
    var registerDate: String? = "",
    var pointType: String? = "",
    var userUID: String? = ""
) : Parcelable {
    constructor() : this("", "", "") // Construtor sem argumentos
}