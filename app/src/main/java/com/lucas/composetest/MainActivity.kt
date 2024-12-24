package com.lucas.composetest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lucas.composetest.ui.theme.ComposeTestTheme
import com.lucas.composetest.view.LauncherScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() serve para que a status bar suma
        enableEdgeToEdge()
        setContent {
            ComposeTestTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
                    LauncherScreen()
                }
            }
        }
    }
}
