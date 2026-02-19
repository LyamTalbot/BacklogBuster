package com.lyamtalbot.backlogbuster2.backlogbuster2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.koin.android.ext.koin.androidContext

class MainApplication: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        initKoin(
            appDeclaration = {androidContext(this@MainApplication)}
        )
        setContent {
            App()
        }
    }
}