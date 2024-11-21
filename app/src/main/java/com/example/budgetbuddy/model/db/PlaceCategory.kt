package com.example.budgetbuddy.model.db

import com.example.budgetbuddy.R

enum class PlaceCategory(val value: String, val icon: String) {
    FOOD("food", "ic_food"),
    ENTERTAINMENT("entertainment", "ic_entertainment"),
    BOOKS("books", "ic_books"),
    ELECTRONICS("electronics", "ic_electronics"),
    CLOTHING("clothing", "ic_clothing"),
    HEALTH("health", "ic_health"),
    EDUCATION("education", "ic_education"),
    GROCERIES("groceries", "ic_groceries"),
    OTHER("other", "ic_other");

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
}
