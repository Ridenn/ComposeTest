package com.lucas.composetest.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.animateDp
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.delay

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
        0.30f to Color(0xFFF3F3F3)
    )

    var expandedItem by remember { mutableStateOf<LauncherItemType?>(null) }
    var itemPosition by remember { mutableStateOf<IntOffset?>(null) }

    if (expandedItem != null && itemPosition != null) {
        ExpandedLauncherItem(
            item = expandedItem!!,
            startPosition = itemPosition!!,
            onFinish = {
                expandedItem = null
                itemPosition = null
            }
        )
    } else {
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
            LauncherAppGrid(
                items = LauncherItemType.entries,
                onItemClick = { item, position ->
                    expandedItem = item
                    itemPosition = position
                }
            )
        }
    }
}

@Composable
fun ExpandedLauncherItem(
    item: LauncherItemType,
    startPosition: IntOffset,
    onFinish: () -> Unit
) {
    val context = LocalContext.current
    var isLaunching by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isLaunching, label = "LaunchTransition")

    val animatedOffset by transition.animateIntOffset(
        label = "OffsetAnimation",
        transitionSpec = { tween(durationMillis = 600) }
    ) { if (it) IntOffset(0, 0) else startPosition }

//    val iconSize by transition.animateDp(
//        label = "IconSize",
//        transitionSpec = { tween(durationMillis = 600) }
//    ) { if (it) 120.dp else 48.dp }

    val backgroundAlpha by transition.animateFloat(
        label = "BackgroundAlpha",
        transitionSpec = { tween(durationMillis = 600) }
    ) { if (it) 1f else 0f }

    LaunchedEffect(item) {
        isLaunching = true
        delay(600) // Aguarda animação inicial
        try {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                setPackage(item.packageIntent)
            }
            startActivity(context, intent, null)
        } catch (e: Exception) {
            Toast.makeText(context, "${item.title} não está instalado.", Toast.LENGTH_SHORT).show()
        }
        delay(1000) // Mostra a tela expandida por 3 segundos
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = backgroundAlpha)),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .offset { animatedOffset }
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize(),
                color = Color.Blue,
                strokeWidth = 8.dp
            )
            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(12.dp),
                imageVector = ImageVector.vectorResource(id = item.icon),
                contentDescription = item.title,
                tint = Color.Black
            )
        }
    }
}
