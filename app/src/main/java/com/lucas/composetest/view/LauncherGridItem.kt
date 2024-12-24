package com.lucas.composetest.view

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.node.Ref
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun LauncherGridItemPreview() {
    LauncherGridItem(
        item = LauncherItemType.TINDER,
        onClick = {}
    )
}

@Composable
fun LauncherGridItem(
    item: LauncherItemType,
    onClick: (IntOffset) -> Unit
) {
    val coordinates = remember { Ref<IntOffset>() }
    val xPosDifference = 425
    val yPosDifference = 1100

    Column(
        modifier = Modifier
            .width(48.dp)
            .clickable {
                onClick(coordinates.value ?: IntOffset.Zero)
            }
            .onGloballyPositioned { layoutCoordinates ->
                val position = layoutCoordinates.positionInWindow()
                    coordinates.value = IntOffset(position.x.toInt() - xPosDifference, position.y.toInt() - yPosDifference)
                Log.e("posic x", position.x.toString())
                Log.e("posic y", position.y.toString())
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom)
    ) {
        Icon(
            modifier = Modifier
                .widthIn(max = 48.dp)
                .aspectRatio(1f)
                .shadow(5.dp, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(12.dp),
            imageVector = ImageVector.vectorResource(id = item.icon),
            contentDescription = item.title,
            tint = Color.Black
        )
        Text(
            modifier = Modifier.height(26.dp),
            text = item.title,
            maxLines = 2,
            textAlign = TextAlign.Center,
            softWrap = true,
            color = Color.Black
        )
    }
}
