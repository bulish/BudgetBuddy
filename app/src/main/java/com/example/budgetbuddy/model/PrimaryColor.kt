package com.example.budgetbuddy.model

import androidx.compose.ui.graphics.Color
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.PlaceCategory
import com.example.budgetbuddy.ui.theme.BluePrimary
import com.example.budgetbuddy.ui.theme.Green
import com.example.budgetbuddy.ui.theme.RedPrimary

enum class PrimaryColor(val color: Color) {
    GREEN(Green),
    RED(RedPrimary),
    BLUE(BluePrimary);

    fun getStringResource(): Int {
        return when (this) {
            GREEN -> R.string.green_color
            RED -> R.string.red_color
            BLUE -> R.string.blue_color
        }
    }

    companion object {
        fun fromString(type: String?): PrimaryColor {
            return PrimaryColor.values()
                .find { it.name.equals(type, ignoreCase = true) } ?: PrimaryColor.GREEN
        }
    }
}
