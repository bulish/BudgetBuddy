package com.example.budgetbuddy.ui.elements.map

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.budgetbuddy.model.db.PlaceCategory
import com.example.budgetbuddy.ui.theme.DoubleMargin
import com.example.budgetbuddy.ui.theme.HalfMargin
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun PlaceDetailIcon(
    place: PlaceCategory?,
) {
    val iconColor = place?.itemColor ?: PlaceCategory.OTHER.itemColor
    val placeIcon = place?.icon

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.padding(vertical = DoubleMargin())
    ) {
        if (placeIcon != null) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(end = HalfMargin())
                    .size(36.dp)
                    .background(iconColor, shape = androidx.compose.foundation.shape.CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = placeIcon),
                    contentDescription = "Place Icon",
                    tint = androidx.compose.ui.graphics.Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
