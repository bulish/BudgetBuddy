package com.example.budgetbuddy.ui.elements.transactions

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.budgetbuddy.model.db.Transaction
import com.example.budgetbuddy.ui.theme.HalfMargin
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.budgetbuddy.extensions.toFormattedString
import com.example.budgetbuddy.model.db.TransactionCategory
import com.example.budgetbuddy.model.db.TransactionType
import com.example.budgetbuddy.navigation.INavigationRouter
import com.example.budgetbuddy.ui.theme.BasicMargin

@Composable
fun TransactionItem(
    transaction: Transaction,
    navigation: INavigationRouter
) {
    val transactionCategory = TransactionCategory.fromString(transaction.category)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .clickable {
                navigation.navigateToTransactionDetailScreen(transaction.id)
            }
            .padding(BasicMargin()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = transactionCategory.color,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = transactionCategory.icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }

        Column(modifier = Modifier
            .weight(1f)
            .padding(start = HalfMargin())) {
            Text(
                text = stringResource(id = transactionCategory.getStringResource()),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "${if (transaction.type == TransactionType.EXPENSE.value) "- " else ""}${transaction.price.toFormattedString()} Kč",
                color = if (transaction.type == TransactionType.EXPENSE.value) Color.Red else MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier
                .padding(end = 8.dp)
                .rotate(-90F)
                .size(24.dp)
        )
    }
}
