package com.example.budgetbuddy.ui.elements.transactions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.ui.theme.HalfMargin
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = HalfMargin())
            .background(MaterialTheme.colorScheme.tertiary),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = transaction.category.icon),
            contentDescription = null,
            tint = Color.Blue,
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transaction.category.toString(),
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${transaction.price} Kč",
                color = if (transaction.price < 0) Color.Red else Color.Green,
                fontSize = 14.sp
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}