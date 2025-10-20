package com.sacramentum.apk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sacramentum.apk.com.sacramentum.apk.view.caixa.InitialChange
import com.sacramentum.apk.com.sacramentum.apk.view.loginPanel.EquipmentSettingsScreen
import com.sacramentum.apk.com.sacramentum.apk.view.loginPanel.LoginScreen
import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.OrderScreen
import com.sacramentum.apk.view.loginPanel.LoadingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OrderScreen()
//            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loading") {
        composable("loading") {
            LoadingScreen(onTimeout = { navController.navigate("equipment") })
        }
        composable("equipment") {
            EquipmentSettingsScreen(onConfigure = { navController.navigate("login") })
        }
        composable("login") {
            LoginScreen(onConfigure = { navController.navigate("initialChange") })
        }
        composable("initialChange") {
            InitialChange(onConfigure = { navController.navigate("management") })
        }
    }
}
