package com.geogebra.utils

import com.geogebra.core.dto.Creator
import com.geogebra.core.dto.Material
import java.util.*

fun mockMaterials(): List<Material> {
    return listOf(
        Material(
            "m9cvbqgp",
            "Parallel Lines in the Coordinate Plane: Quick Exploration",
            "ws",
            Date(),
            Date(),
            "O",
            "https://www.geogebra.org/resource/m9cvbqgp/jwu26zO0VaMqZUpt/material-m9cvbqgp-thumb$1.png",
            Creator(5743822, "GeoGebra Team", "geogebrateam")
        ),
        Material(
            "bjgvfrdw",
            "Geometry Apps with Whiteboards",
            "ws",
            Date(),
            Date(),
            "O",
            "https://www.geogebra.org/resource/bjgvfrdw/Ek4v4lrsVrmTNVIt/material-bjgvfrdw-thumb$1.png",
            Creator(5743822, "GeoGebra Team", "geogebrateam")
        )
    )
}