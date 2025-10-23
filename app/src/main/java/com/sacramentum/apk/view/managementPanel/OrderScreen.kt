package com.sacramentum.apk.com.sacramentum.apk.view.managementPanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.components.Cart
import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.components.Header
import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.components.ProductCard
import com.sacramentum.apk.com.sacramentum.apk.viewmodel.OrderViewModel
import com.sacramentum.apk.ui.theme.LightBrownBackground

@Composable
fun OrderScreen(
    viewModel: OrderViewModel = viewModel(),
    userName: String,
    userRole: String,
    initialCashAmount: Double,
    onNavigateToSummary: () -> Unit,
    onLogout: () -> Unit
) {
    var showLogoutScreen by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            // Área principal - Header e Cards
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .background(LightBrownBackground)
            ) {
                // Header com detecção de toque longo
                Header(onLongPress = { showLogoutScreen = true })

                // Cards
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(
                        start = 20.dp,
                        end = 20.dp,
                        top = 20.dp,
                        bottom = 20.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(viewModel.availableProducts) { product ->
                        ProductCard(
                            product = product,
                            onAddToCart = { viewModel.addToCart(product) }
                        )
                    }
                }
            }

            Cart(
                cartItems = viewModel.cartItems,
                totalPrice = viewModel.totalPrice,
                onIncrement = { viewModel.incrementQuantity(it) },
                onDecrement = { viewModel.decrementQuantity(it) },
                onRemove = { viewModel.removeFromCart(it) },
                onNext = onNavigateToSummary
            )
        }

        if (showLogoutScreen) {
            // Converte os itens do carrinho para o histórico de vendas
            val soldItems = viewModel.getSoldItemsHistory()

            LogoutScreen(
                userName = userName,
                userRole = userRole,
                initialCashAmount = initialCashAmount,
                soldItems = soldItems,
                totalSales = viewModel.getTotalSales(),
                onLogout = {
                    showLogoutScreen = false
                    onLogout()
                },
                onCancel = {
                    showLogoutScreen = false
                }
            )
        }
    }
}