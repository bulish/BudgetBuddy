package com.example.budgetbuddy.model.db

import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.PlaceCategory.OTHER

enum class TransactionCategory(val value: String, val type: TransactionType, val icon: Int) {
    // INCOMES
    SALARY("salary", TransactionType.INCOME, R.drawable.account_balance_wallet_24px),
    BUSINESS_INCOME("business_income", TransactionType.INCOME, R.drawable.shop_24px),
    INVESTMENT_INCOME("investment_income", TransactionType.INCOME, R.drawable.attach_money_24px),
    BONUS("bonus", TransactionType.INCOME, R.drawable.star_24px),
    LOAN_RECEIVED("loan_received", TransactionType.INCOME, R.drawable.payments_24px),
    OTHER_INCOME("other_income", TransactionType.INCOME, R.drawable.more_horiz_24px),

    // EXPENSES
    GROCERIES("groceries", TransactionType.EXPENSE, R.drawable.shopping_cart_24px),
    RESTAURANTS("restaurants", TransactionType.EXPENSE, R.drawable.fork_spoon_24px),
    COFFEE("coffee", TransactionType.EXPENSE, R.drawable.local_cafe_24px),
    RENT("rent", TransactionType.EXPENSE, R.drawable.home_24px),
    UTILITIES("utilities", TransactionType.EXPENSE, R.drawable.bolt_24px),
    FUEL("fuel", TransactionType.EXPENSE, R.drawable.local_gas_station_24px),
    MEDICAL("medical", TransactionType.EXPENSE, R.drawable.health_and_safety_24px),
    INSURANCE("insurance", TransactionType.EXPENSE, R.drawable.security_24px),
    CLOTHING("clothing", TransactionType.EXPENSE, R.drawable.checkroom_24px),
    ENTERTAINMENT("entertainment", TransactionType.EXPENSE, R.drawable.movie_24px),
    BANK_FEES("bank_fees", TransactionType.EXPENSE, R.drawable.account_balance_24px),
    LOAN_PAYMENT("loan_payment", TransactionType.EXPENSE, R.drawable.money_off_24px),
    OTHER_EXPENSE("other_expense", TransactionType.EXPENSE, R.drawable.more_horiz_24px),
    GIFT("gift", TransactionType.EXPENSE, R.drawable.featured_seasonal_and_gifts_24px);


    fun getStringResource(): Int {
        return when (this) {
            SALARY -> R.string.category_salary
            BUSINESS_INCOME -> R.string.category_business_income
            INVESTMENT_INCOME -> R.string.category_investment_income
            BONUS -> R.string.category_bonus
            LOAN_RECEIVED -> R.string.category_load_received
            OTHER_INCOME -> R.string.category_other_income

            GROCERIES -> R.string.category_groceries
            RESTAURANTS -> R.string.category_restaurants
            COFFEE -> R.string.category_coffee
            RENT -> R.string.category_rent
            UTILITIES -> R.string.category_utilities
            FUEL -> R.string.category_fuel
            MEDICAL -> R.string.category_medical
            INSURANCE -> R.string.category_insurance
            CLOTHING -> R.string.category_clothing
            ENTERTAINMENT -> R.string.category_entertainment
            BANK_FEES -> R.string.category_bank_fees
            LOAN_PAYMENT -> R.string.category_loan_payment
            OTHER_EXPENSE -> R.string.category_other_expense
            GIFT -> R.string.category_gift

        }
    }

    companion object {
        fun fromString(type: String?): PlaceCategory {
            return PlaceCategory.values().find { it.name.equals(type, ignoreCase = true) } ?: OTHER
        }
    }
}

enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense")
}

