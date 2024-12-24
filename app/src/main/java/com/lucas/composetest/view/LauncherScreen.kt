package com.lucas.composetest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LauncherScreenPreview() {
    LauncherScreen()
}

@Composable
fun LauncherScreen() {
    val context = LocalContext.current

    val colorStops = arrayOf(
        0.0f to Color(0xFFFFE600),
        0.20f to Color(0xFFFFE600),
        0.40f to Color(0xFFF3F3F3)
    )

    var expandedItem by remember { mutableStateOf<LauncherItemType?>(null) }

//    if (expandedItem != null) {
//        ExpandedLauncherItem(
//            item = expandedItem!!,
//            onFinish = { expandedItem = null }
//        )
//    } else {
//
//    }



    Column(
        modifier = Modifier
            .background(brush = Brush.verticalGradient(colorStops = colorStops))
            .padding(horizontal = 20.dp)
            .padding(top = 60.dp)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        HeaderComponent()
        Spacer(modifier = Modifier.height(24.dp))
        PaymentCard(
            onClickPayment = {
                context.packageManager.getLaunchIntentForPackage(
                    "com.google.android.apps.wallet"
                )?.let { intent ->
                    context.startActivity(intent)
                }
            },
        )
        LauncherAppGrid(LauncherItemType.entries)
    }
}


