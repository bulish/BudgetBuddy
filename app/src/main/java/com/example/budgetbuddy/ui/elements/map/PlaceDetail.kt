package com.example.budgetbuddy.ui.elements.map

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.budgetbuddy.R
import com.example.budgetbuddy.model.db.Place
import com.example.budgetbuddy.ui.elements.shared.button.CustomButton
import com.example.budgetbuddy.ui.elements.shared.button.CustomButtonType
import com.example.budgetbuddy.ui.theme.BasicMargin
import com.example.budgetbuddy.ui.theme.HalfMargin

@Composable
fun PlaceDetail(
    place: Place,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context
) {
    Card(
        modifier = modifier.zIndex(0f),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface // Barva pozadí
        )
    ) {
        Box {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = BasicMargin(), vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(modifier = Modifier.weight(1.0f),
                        verticalAlignment = Alignment.CenterVertically) {
                        PlaceDetailIcon(place = place.category)
                        Text(text = place.category.name)
                    }
                    IconButton(onClick = { onClose() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }

                PlaceIcon(
                    filesDir = context.filesDir,
                    iconName = place.imageName
                )

                Column(modifier = Modifier.padding(BasicMargin()).fillMaxWidth()) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        text = place.address,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Row(
                        Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        CustomButton(
                            type = CustomButtonType.Outlined,
                            text = stringResource(id = R.string.edit),
                            onClickAction = {
                                onEdit()
                            }
                        )

                        Spacer(modifier = Modifier.width(HalfMargin()))

                        CustomButton(
                            type = CustomButtonType.Basic,
                            text = stringResource(id = R.string.delete),
                            onClickAction = { onDelete() }
                        )
                    }
                }
            }
        }
    }
}
