package com.example.budgetbuddy.model.db
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.budgetbuddy.R
import kotlin.random.Random



enum class TransactionCategory(val value: String, val icon: Int) {
    SALARY("salary", R.drawable.account_balance_wallet_24px),
    BUSINESS("business", R.drawable.shop_24px),
    OTHER("other", R.drawable.more_horiz_24px),
    GROCERIES("groceries", R.drawable.shopping_cart_24px),
    RESTAURANTS("restaurants", R.drawable.fork_spoon_24px),
    COFFEE("coffee", R.drawable.local_cafe_24px),
    FUEL("fuel", R.drawable.local_gas_station_24px),
    MEDICAL("medical", R.drawable.health_and_safety_24px),
    CLOTHING("clothing", R.drawable.checkroom_24px),
    ENTERTAINMENT("entertainment", R.drawable.movie_24px),
    GIFT("gift", R.drawable.featured_seasonal_and_gifts_24px);

    val color: Color by lazy { randomColor() }

    fun getStringResource(): Int {
        return when (this) {
            SALARY -> R.string.category_salary
            BUSINESS -> R.string.category_business
            OTHER -> R.string.category_other
            GROCERIES -> R.string.category_groceries
            RESTAURANTS -> R.string.category_restaurants
            COFFEE -> R.string.category_coffee
            FUEL -> R.string.category_fuel
            MEDICAL -> R.string.category_medical
            CLOTHING -> R.string.category_clothing
            ENTERTAINMENT -> R.string.category_entertainment
            GIFT -> R.string.category_gift
        }
    }

    companion object {
        fun fromString(type: String?): TransactionCategory {
            Log.d("transaction category", "${type}")
            return entries.find { it.value.equals(type, ignoreCase = true) } ?: OTHER
        }
    }
}

fun randomColor(): Color {
    val random = Random(System.currentTimeMillis())
    return Color(
        red = random.nextInt(0, 256),
        green = random.nextInt(0, 256),
        blue = random.nextInt(0, 256),
        alpha = 255
    )
}

enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense")
}

