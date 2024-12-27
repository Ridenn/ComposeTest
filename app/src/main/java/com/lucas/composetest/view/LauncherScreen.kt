package com.lucas.composetest.view

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Preview(showBackground = true)
@Composable
fun LauncherScreenPreview() {
    LauncherScreen()
}

@Composable
fun LauncherScreen() {
    var expandedItem by remember { mutableStateOf<LauncherItemType?>(null) }

    if (expandedItem != null) {
        ExpandedLauncherItem(
            item = expandedItem!!,
            onFinish = {
                expandedItem = null
            }
        )
    } else {
        LauncherScreenComponent(
            onItemExpand = { item ->
                expandedItem = item
            }
        )
    }
}

@Composable
fun LauncherScreenComponent(
    onItemExpand: (LauncherItemType) -> Unit = {}
) {
    val colorStops = arrayOf(
        0.0f to Color(0xFFFFE600),
        0.23f to Color(0xFFFFE600),
        0.33f to Color(0xFFF3F3F3)
    )

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
        PaymentCard()
        LauncherAppGrid(
            items = LauncherItemType.entries,
            onItemClick = onItemExpand
        )
    }
}

@Composable
fun ExpandedLauncherItem(
    item: LauncherItemType,
    onFinish: () -> Unit
) {
    var isLaunching by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isLaunching, label = "LaunchTransition")

    val scale by transition.animateFloat(
        label = "ScaleAnimation",
        transitionSpec = { tween(durationMillis = 500, easing = LinearOutSlowInEasing) }
    ) { if (it) 1f else 0.5f }

    val alpha by transition.animateFloat(
        label = "AlphaAnimation",
        transitionSpec = { tween(durationMillis = 500) }
    ) { if (it) 1f else 0f }

    LaunchedEffect(item) {
        isLaunching = true
        delay(1500)
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                alpha = alpha
            )
        ) {
            Icon(
                modifier = Modifier.size(100.dp),
                imageVector = ImageVector.vectorResource(id = item.icon),
                contentDescription = item.title,
                tint = Color.Black
            )
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(
                color = Color.Blue,
                strokeWidth = 4.dp
            )
        }
    }
}

@Composable
private fun getLauncherToPayment() = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.StartActivityForResult()

) { }