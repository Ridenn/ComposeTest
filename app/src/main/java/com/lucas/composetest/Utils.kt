package com.lucas.composetest

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import java.math.BigInteger
import java.security.MessageDigest

fun getHash(timestamp: String, privateKey: String, publicKey: String): String {
    val hash = timestamp + privateKey + publicKey
    val md = MessageDigest.getInstance("MD5")

    return BigInteger(1, md.digest(hash.toByteArray())).toString(16).padStart(32, '0')
}

@Composable
fun AttributionText(text: String, fontSize: TextUnit = 12.sp) {
    Text(
        text = text,
        modifier = Modifier.padding(start = 8.dp, top = 4.dp),
        fontSize = fontSize,
        color = Color.White
    )
}

@Composable
fun CharacterImage(
    imageUrl: String?,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
}

@Composable
fun MarqueeText(
    title: String,
    fontSize: Int = 20,
    fontWeight: FontWeight = FontWeight.Bold,
    color: Color = Color.Black,
    modifier: Modifier = Modifier,
    speed: Float = 30f, // Pixels por segundo
    delayMillis: Int = 1000 // Tempo de espera antes do início da animação
) {
    var textWidth by remember { mutableStateOf(0f) }
    var containerWidth by remember { mutableStateOf(0f) }
    var offsetX by remember { mutableStateOf(0f) }

    LaunchedEffect(textWidth, containerWidth) {
        if (textWidth > containerWidth) {
            // Aguardar o tempo inicial
            delay(delayMillis.toLong())

            // Iniciar o loop de animação
            while (true) {
                val totalDistance = textWidth + containerWidth // Espaço total a percorrer
                val durationMillis = (totalDistance / speed * 1000).toInt()

                // Animação de deslocamento
                animate(
                    initialValue = 0f,
                    targetValue = -totalDistance,
                    animationSpec = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing
                    )
                ) { value, _ ->
                    offsetX = value
                }

                // Resetar para repetir
                offsetX = containerWidth
            }
        }
    }

    Box(modifier = modifier) {
        Text(
            text = title,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            color = color,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier
                .offset { IntOffset(offsetX.toInt(), 0) }
                .wrapContentWidth(),
            onTextLayout = { textLayoutResult ->
                textWidth = textLayoutResult.size.width.toFloat()
                containerWidth = textLayoutResult.size.width.toFloat()
            }
        )
    }
}

fun List<String>.comicsToString() = this.joinToString(separator = ", ")