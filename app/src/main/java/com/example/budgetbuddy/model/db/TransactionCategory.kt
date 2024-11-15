package com.example.budgetbuddy.model.db

import com.example.budgetbuddy.R

enum class TransactionCategory(val value: String, val type: TransactionType, val icon: Int) {
    // INCOMES
    SALARY("salary", TransactionType.INCOME, R.drawable.account_balance_wallet_24px),
    BUSINESS_INCOME("business_income", TransactionType.INCOME, R.drawable.shop_24px),
    INVESTMENT_INCOME("investment_income", TransactionType.INCOME, R.drawable.attach_money_24px),
    GIFT("gift", TransactionType.INCOME, R.drawable.featured_seasonal_and_gifts_24px),
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
    GIFTS("gifts", TransactionType.EXPENSE, R.drawable.featured_seasonal_and_gifts_24px),
    ENTERTAINMENT("entertainment", TransactionType.EXPENSE, R.drawable.movie_24px),
    BANK_FEES("bank_fees", TransactionType.EXPENSE, R.drawable.account_balance_24px),
    LOAN_PAYMENT("loan_payment", TransactionType.EXPENSE, R.drawable.money_off_24px),
    OTHER_EXPENSE("other_expense", TransactionType.EXPENSE, R.drawable.more_horiz_24px)
}

enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense")
}

