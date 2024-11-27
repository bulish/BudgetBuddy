package com.example.budgetbuddy.model.db

import androidx.compose.ui.graphics.Color
import com.example.budgetbuddy.R

import com.example.budgetbuddy.ui.theme.Blue
import com.example.budgetbuddy.ui.theme.Pink40
import com.example.budgetbuddy.ui.theme.Purple40
import com.example.budgetbuddy.ui.theme.Yellow40
import com.example.budgetbuddy.ui.theme.Green40
import com.example.budgetbuddy.ui.theme.Orange40
import com.example.budgetbuddy.ui.theme.Red40
import com.example.budgetbuddy.ui.theme.Teal40
import com.example.budgetbuddy.ui.theme.Gray40

enum class PlaceCategory(val value: String, val icon: Int, val itemColor: Color) {
    FOOD("food", R.drawable.fork_spoon_24px, Yellow40),
    ENTERTAINMENT("entertainment", R.drawable.sports_esports_24px, Orange40),
    BOOKS("books", R.drawable.auto_stories_24px, Purple40),
    ELECTRONICS("electronics", R.drawable.battery_charging_full_24px, Blue),
    CLOTHING("clothing", R.drawable.checkroom_24px, Pink40),
    HEALTH("health", R.drawable.health_and_safety_24px, Green40),
    EDUCATION("education", R.drawable.school_24px, Red40),
    GROCERIES("groceries", R.drawable.shopping_cart_24px, Teal40),
    OTHER("other", R.drawable.shop_24px, Gray40);

    fun getStringResource(): Int {
        return when (this) {
            FOOD -> R.string.category_food
            ENTERTAINMENT -> R.string.category_entertainment
            BOOKS -> R.string.category_books
            ELECTRONICS -> R.string.category_electronics
            CLOTHING -> R.string.category_clothing
            HEALTH -> R.string.category_health
            EDUCATION -> R.string.category_education
            GROCERIES -> R.string.category_groceries
            OTHER -> R.string.category_other
        }
    }

    companion object {
        fun fromString(type: String?): PlaceCategory {
            return values().find { it.name.equals(type, ignoreCase = true) } ?: OTHER
        }
    }
}
