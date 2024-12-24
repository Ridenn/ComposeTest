package com.lucas.composetest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HeaderComponentPreview() {
    HeaderComponent()
}

@Composable
fun HeaderComponent() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(shape = CircleShape)
            .background(Color.White)
            .padding(all = 6.dp)
    ) {
        Box(modifier = Modifier
            .clip(shape = CircleShape)
            .background(Color.White)
            .padding(all = 10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "Search"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        UserInfoCard(
            labelUserName = "Olá, Lucas!",
            labelBusinessName = "Bem vindo de volta"
        )
    }
}

@Composable
private fun UserInfoCard(
    labelUserName: String,
    labelBusinessName: String
) {
    Column(modifier = Modifier.padding(start = 6.dp, end = 40.dp)) {
        Text(
            text = labelUserName,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = labelBusinessName,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}
