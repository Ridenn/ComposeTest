package com.lucas.composetest.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun PaymentCardPreview() {
    PaymentCard()
}

@Composable
fun PaymentCard(
    onClickPayment: () -> Unit = {}
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        PaymentCardComponent(
            onClickPayment = onClickPayment,
            labelChargeMoney = "Loja 1 - Casa do Pastel"
        )
    }
}

@Composable
fun PaymentCardComponent(
    onClickPayment: () -> Unit,
    labelChargeMoney: String,
) {
    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
        Text(
            text = "Está cobrando em:",
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = labelChargeMoney,
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onClickPayment,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Icon(imageVector = Icons.Default.Face, contentDescription = "")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Iniciar cobrança")
            }
        }
    }
}