package com.lucas.composetest.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
        LauncherComplete()
        LauncherAppGrid(
                items = LauncherItemType.entries,
                onItemClick = { item, position ->
                    itemPosition = position
                    expandedItem = item
                }
            )


//        val context = LocalContext.current
//
//        val colorStops = arrayOf(
//            0.0f to Color(0xFFFFE600),
//            0.23f to Color(0xFFFFE600),
//            0.33f to Color(0xFFF3F3F3)
//        )
//
//        Column(
//            modifier = Modifier
//                .background(brush = Brush.verticalGradient(colorStops = colorStops))
//                .padding(horizontal = 20.dp)
//                .padding(top = 60.dp)
//                .fillMaxWidth()
//                .fillMaxHeight()
//        ) {
//            HeaderComponent()
//            Spacer(modifier = Modifier.height(24.dp))
//            PaymentCard(
//                onClickPayment = {
//                    context.packageManager.getLaunchIntentForPackage(
//                        "com.google.android.apps.wallet"
//                    )?.let { intent ->
//                        context.startActivity(intent)
//                    }
//                },
//            )
//            LauncherAppGrid(
//                items = LauncherItemType.entries,
//                onItemClick = { item, position ->
//                    itemPosition = position
//                    expandedItem = item
//                }
//            )
//        }
    }
}

@Composable
fun LauncherComplete() {
    val context = LocalContext.current

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
        PaymentCard(
            onClickPayment = {
                context.packageManager.getLaunchIntentForPackage(
                    "com.google.android.apps.wallet"
                )?.let { intent ->
                    context.startActivity(intent)
                }
            },
        )
//        LauncherAppGrid(
//            items = LauncherItemType.entries
//        )
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

    LaunchedEffect(item) {
        isLaunching = true
        delay(1000)

        try {
            val intent = Intent().apply {
                action = Intent.ACTION_VIEW
                setPackage(item.packageIntent)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "${item.title} não está instalado.", Toast.LENGTH_SHORT).show()
        }
        delay(500)
        onFinish()
    }

    // Fundo branco animado que sobe da parte inferior
    val backgroundOffset by animateDpAsState(
        targetValue = if (isLaunching) 0.dp else 800.dp,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )

    LauncherComplete()

    Box(modifier = Modifier.fillMaxSize()) {
        // Fundo animado subindo da parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .offset { IntOffset(0, backgroundOffset.roundToPx()) }
                .background(Color.White)
        )

        // Ícone animado subindo para o centro
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .offset { animatedOffset } // Controla o movimento do ícone
                    .size(100.dp)
                    .clip(CircleShape),
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
}
