package com.sacramentum.apk

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sacramentum.apk.com.sacramentum.apk.view.caixa.InitialChange
import com.sacramentum.apk.com.sacramentum.apk.view.loginPanel.EquipmentSettingsScreen
import com.sacramentum.apk.com.sacramentum.apk.view.loginPanel.LoginScreen
import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.OrderScreen
import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.SummaryScreen
import com.sacramentum.apk.com.sacramentum.apk.viewmodel.OrderViewModel
import com.sacramentum.apk.view.loginPanel.LoadingScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppNavigation()
        }
    }
}

@SuppressLint("UnrememberedGetBackStackEntry")
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
        composable("management") {
            val backStackEntry = navController.getBackStackEntry("management")
            val viewModel: OrderViewModel = viewModel(backStackEntry)

            OrderScreen(
                viewModel = viewModel,
                onNavigateToSummary = { navController.navigate("summary") }
            )
        }
        composable("summary") {
            val backStackEntry = navController.getBackStackEntry("management")
            val viewModel: OrderViewModel = viewModel(backStackEntry)

            SummaryScreen(
                cartItems = viewModel.cartItems,
                totalPrice = viewModel.totalPrice,
                onConfirm = { paymentMethod, observations, cashAmount ->
                    println("Pedido confirmado!")
                    println("Pagamento: $paymentMethod")
                    println("Observações: $observations")
                    if (cashAmount != null) {
                        println("Dinheiro recebido: R$ %.2f".format(cashAmount))
                        println("Troco: R$ %.2f".format(cashAmount - viewModel.totalPrice))
                    }

                     viewModel.clearCart()
                    navController.popBackStack("management", inclusive = false)
                },
                onBack = { navController.popBackStack() }
            )
        }
    }
}