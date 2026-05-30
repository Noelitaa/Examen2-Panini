package com.moviles.paninisupport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.moviles.paninisupport.navigation.AppNavHost
import com.moviles.paninisupport.ui.theme.PaniniSupportTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PaniniSupportTheme {
                AppNavHost(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
