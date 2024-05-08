package com.projeto.piIII.model

import com.projeto.piIII.enum.PointType
import java.time.Instant
import java.util.UUID

data class Point(
    var uuid: UUID,
    var funcUID: String,
    var registerDate: String,
    var pointType: PointType
)