package com.lucas.composetest.view

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.lucas.composetest.R
import kotlinx.coroutines.launch

@Preview
@Composable
fun LauncherAppGridPreview() {
    LauncherAppGrid(
        items = LauncherItemType.entries
    )
}

@Composable
fun LauncherAppGrid(items: List<LauncherItemType>) {
    val pageCount = (items.size + 11) / 12
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = pagerState.currentPage != 0) {
        coroutineScope.launch {
            pagerState.animateScrollToPage(0)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            val start = page * 12
            val end = minOf(start + 12, items.size)
            val pageItems = items.subList(start, end)

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                contentPadding = PaddingValues(0.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
                modifier = Modifier.fillMaxHeight()
            ) {
                items(pageItems) { item ->
                    LauncherGridItem(
                        item = item,
                        onClick = {
                            try {
                                val intent = Intent().apply {
                                    action = Intent.ACTION_VIEW
                                    setPackage(item.packageIntent)
                                }
                                startActivity(context, intent, null)
                            } catch (e: Exception) {
                                Toast.makeText(context, "${item.title} não está instalado.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        }

        AnimatedContent(targetState = items.size > 12) { showIndicator ->
            if (showIndicator) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom
                ) {
                    repeat(pageCount) { index ->
                        val color by animateColorAsState(
                            if (pagerState.currentPage == index) {
                                Color.Black
                            } else {
                                Color.LightGray
                            }
                        )

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.size(16.dp))
            }
        }
    }
}

enum class LauncherItemType(
    val icon: Int,
    val title: String,
    val packageIntent: String
) {
    PICPAY(
        R.drawable.ic_library,
        "PicPay",
        "com.picpay"
    ),
    TINDER(
        R.drawable.ic_library,
        "Tinder",
        "com.picpay"
    ),
    FACEBOOK(
        R.drawable.ic_collection,
        "Facebook",
        "com.picpay"
    ),
    INSTAGRAM(
        R.drawable.ic_library,
        "Instagram",
        "com.picpay"
    ),
    TWITTER(
        R.drawable.ic_library,
        "Twitter",
        "com.picpay"
    ),
    YOUTUBE(
        R.drawable.ic_library,
        "YouTube",
        "com.picpay"
    ),
    WHATSAPP(
        R.drawable.ic_library,
        "WhatsApp",
        "com.picpay"
    ),
    DISCORD(
        R.drawable.ic_library,
        "Discord",
        "com.picpay"
    ),
    SNAPCHAT(
        R.drawable.ic_library,
        "Snapchat",
        "com.picpay"
    ),
    PINTEREST(
        R.drawable.ic_library,
        "Pinterest",
        "com.picpay"
    ),
    LINKEDIN(
        R.drawable.ic_library,
        "LinkedIn",
        "com.picpay"
    ),
    SPOTIFY(
        R.drawable.ic_library,
        "Spotify",
        "com.picpay"
    ),
    NETFLIX(
        R.drawable.ic_library,
        "Netflix",
        "com.picpay"
    ),
    GITHUB(
        R.drawable.ic_library,
        "GitHub",
        "com.picpay"
    ),
    GOOGLE_MAPS(
        R.drawable.ic_library,
        "Google Maps",
        "com.google.android.apps.maps"
    ),
    TIKTOK(
        R.drawable.ic_library,
        "TikTok",
        "com.ss.android.ugc.trill"
    ),
    YOUTUBE_MUSIC(
        R.drawable.ic_library,
        "YouTube Music",
        "com.picpay"
    )
}
