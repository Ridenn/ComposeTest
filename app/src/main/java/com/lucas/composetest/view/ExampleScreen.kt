package com.lucas.composetest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class GridItem(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit = {}
)

//@Composable
//fun AdaptiveGridCarousel(items: List<GridItem>) {
//    if (items.size <= 9) {
//        GridLayout(items)
//    } else {
//        CarouselWithIndicator(items)
//    }
//}

@Composable
fun GridItemComponent(item: GridItem) {
    Column(
        modifier = Modifier
            .width(48.dp)
            .padding(vertical = 8.dp)
            .padding(horizontal = 12.dp)
//            .padding(horizontal = 2.dp)
//            .clip(RoundedCornerShape(8.dp))
//            .background(Color.White)
            .clickable { item.onClick() }
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null
//            ) { item.onClick() }
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            modifier = Modifier
//                .width(48.dp)
//                .height(48.dp)
//                .padding(8.dp)
//                .padding(horizontal = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .clickable { item.onClick() }
                .padding(12.dp)
//            .clickable(
//                interactionSource = remember { MutableInteractionSource() },
//                indication = null
//            ) { item.onClick() }
//                .padding(12.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.label,
                modifier = Modifier.size(48.dp),
                tint = Color.Black
            )
        }
        Text(
            text = item.label,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            maxLines = 2,
            color = Color.Black
        )
    }
}

//@Composable
//fun GridLayout(items: List<GridItem>) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(3),
//        contentPadding = PaddingValues(8.dp),
//        modifier = Modifier.height(((items.size / 3 + 1) * 120).dp)
//    ) {
//        items(items) { item ->
//            GridItemComponent(item)
//        }
//    }
//}

@Composable
fun CarouselWithIndicator(items: List<GridItem>) {
    val pageCount = (items.size + 11) / 12
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            val start = page * 12
            val end = minOf(start + 12, items.size)
            val pageItems = items.subList(start, end)

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(pageItems) { item ->
                    GridItemComponent(item)
                }
            }
        }

        if (items.count() > 12) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .height(22.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                repeat(pageCount) { index ->
                    val color = if (pagerState.currentPage == index)
                        Color.Black
                    else
                        Color.LightGray

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 400, heightDp = 400)
//@Composable
//fun GridLayoutPreview() {
//    val sampleItems = listOf(
//        GridItem(Icons.Default.Add, "Adicionar"),
//        GridItem(Icons.Default.Email, "Email"),
//        GridItem(Icons.Default.Place, "Mapa"),
//        GridItem(Icons.Default.Info, "Info"),
//        GridItem(Icons.Default.LocationOn, "Câmera"),
//        GridItem(Icons.Default.Search, "Buscar"),
//        GridItem(Icons.Default.Add, "Galeria"),
//        GridItem(Icons.Default.Share, "Compartilhar"),
//        GridItem(Icons.Default.Settings, "Definir")
//    )
//
//    MaterialTheme {
//        Surface {
//            AdaptiveGridCarousel(sampleItems)
//        }
//    }
//}

@Preview(showBackground = true)
@Composable
fun CarouselLayoutPreview() {
    val sampleItems = listOf(
        GridItem(Icons.Default.Add, "Tus cobros"),
        GridItem(Icons.Default.Email, "Resúmenes de venta"),
        GridItem(Icons.Default.Place, "Simulador de costos"),
        GridItem(Icons.Default.Info, "Servicios"),
        GridItem(Icons.Default.Info, "Tienda de apps"),
        GridItem(Icons.Default.Search, "Buscar"),
        GridItem(Icons.Default.AccountBox, "Galeria"),
        GridItem(Icons.Default.Share, "Compartilhar"),
        GridItem(Icons.Default.Settings, "Definir"),
        GridItem(Icons.Default.DateRange, "Agenda"),
        GridItem(Icons.Default.Build, "Direções"),
        GridItem(Icons.Default.Edit, "Editar"),
//        GridItem(Icons.Default.Build, "Gerenciar"),
//        GridItem(Icons.Default.LocationOn, "Localização"),
//        GridItem(Icons.Default.Face, "Preferências")
    )

    MaterialTheme {
        Surface(
            color = Color.Red
        ) {
            CarouselWithIndicator(sampleItems)
        }
    }
}
