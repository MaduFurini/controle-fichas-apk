    package com.sacramentum.apk

    import android.annotation.SuppressLint
    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.activity.enableEdgeToEdge
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.remember
    import androidx.lifecycle.viewmodel.compose.viewModel
    import androidx.navigation.compose.NavHost
    import androidx.navigation.compose.composable
    import androidx.navigation.compose.rememberNavController
    import com.sacramentum.apk.com.sacramentum.apk.view.caixa.InitialChange
    import com.sacramentum.apk.com.sacramentum.apk.view.loginPanel.EquipmentSettingsScreen
    import com.sacramentum.apk.com.sacramentum.apk.view.loginPanel.LoginScreen
    import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.OrderScreen
    import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.ProcessingSaleScreen
    import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.SummaryScreen
    import com.sacramentum.apk.com.sacramentum.apk.viewmodel.OrderViewModel
    import com.sacramentum.apk.view.loginPanel.LoadingScreen

    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            enableEdgeToEdge()

            window.decorView.apply {
                systemUiVisibility =
                    android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
            }

            setContent {
                AppNavigation()
            }
        }
    }

    @SuppressLint("UnrememberedGetBackStackEntry")
    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()

        // Dados do usuário - você deve pegar isso do seu sistema de login
        // Por enquanto, valores de exemplo
        val userName = "João Silva"
        val userRole = "Operador de Caixa"
        val initialCashAmount = 100.0

        NavHost(navController = navController, startDestination = "management") {
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
            composable("management") { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry("management")
                }
                val viewModel: OrderViewModel = viewModel(parentEntry)

                OrderScreen(
                    viewModel = viewModel,
                    userName = userName,
                    userRole = userRole,
                    initialCashAmount = initialCashAmount,
                    onNavigateToSummary = { navController.navigate("summary") },
                    onLogout = {
                        // Limpa todo o backstack e volta para o login
                        navController.navigate("loading") {
                            popUpTo("loading") { inclusive = true }
                        }
                    }
                )
            }
            composable("summary") { backStackEntry ->
                val parentEntry = remember(navController) {
                    navController.getBackStackEntry("management")
                }
                val viewModel: OrderViewModel = viewModel(parentEntry)

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

                        // Registra a venda no histórico
                        viewModel.completeSale(paymentMethod, observations, cashAmount)

                        // Navega para a tela de processamento
                        navController.navigate("processingSale")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
            composable("processingSale") {
                ProcessingSaleScreen(
                    onComplete = {
                        // Volta para a tela de management e limpa o carrinho
                        navController.popBackStack("management", inclusive = false)
                    }
                )
            }
        }
    }