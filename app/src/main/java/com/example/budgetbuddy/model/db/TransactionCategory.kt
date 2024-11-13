package com.example.budgetbuddy.model.db

enum class TransactionCategory(val value: String, val type: TransactionType, val icon: String) {
    // INCOMES
    SALARY("salary", TransactionType.INCOME, "ic_salary"),
    BUSINESS_INCOME("business_income", TransactionType.INCOME, "ic_business_income"),
    INVESTMENT_INCOME("investment_income", TransactionType.INCOME, "ic_investment_income"),
    GIFT("gift", TransactionType.INCOME, "ic_gift"),
    BONUS("bonus", TransactionType.INCOME, "ic_bonus"),
    LOAN_RECEIVED("loan_received", TransactionType.INCOME, "ic_loan_received"),
    OTHER_INCOME("other_income", TransactionType.INCOME, "ic_other_income"),

    // EXPENSES
    GROCERIES("groceries", TransactionType.EXPENSE, "ic_groceries"),
    RESTAURANTS("restaurants", TransactionType.EXPENSE, "ic_restaurants"),
    COFFEE("coffee", TransactionType.EXPENSE, "ic_coffee"),
    RENT("rent", TransactionType.EXPENSE, "ic_rent"),
    UTILITIES("utilities", TransactionType.EXPENSE, "ic_utilities"),
    FUEL("fuel", TransactionType.EXPENSE, "ic_fuel"),
    MEDICAL("medical", TransactionType.EXPENSE, "ic_medical"),
    INSURANCE("insurance", TransactionType.EXPENSE, "ic_insurance"),
    CLOTHING("clothing", TransactionType.EXPENSE, "ic_clothing"),
    GIFTS("gifts", TransactionType.EXPENSE, "ic_gifts"),
    ENTERTAINMENT("entertainment", TransactionType.EXPENSE, "ic_entertainment"),
    BANK_FEES("bank_fees", TransactionType.EXPENSE, "ic_bank_fees"),
    LOAN_PAYMENT("loan_payment", TransactionType.EXPENSE, "ic_loan_payment"),
    OTHER_EXPENSE("other_expense", TransactionType.EXPENSE, "ic_other_expense")
}

enum class TransactionType(val value: String) {
    INCOME("income"),
    EXPENSE("expense")
}
