package com.sacramentum.apk.com.sacramentum.apk.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.sacramentum.apk.com.sacramentum.apk.model.CartItem
import com.sacramentum.apk.com.sacramentum.apk.model.Product
import com.sacramentum.apk.com.sacramentum.apk.model.SoldItem
import com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.PaymentMethod

class OrderViewModel : ViewModel() {
    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    val totalPrice: Double
        get() = _cartItems.sumOf { it.product.price * it.quantity }


    // Lista de produtos disponíveis
    val availableProducts = listOf(
        Product(1, "Produto 1", 25.0),
        Product(2, "Produto 2", 30.0),
        Product(3, "Produto 3", 35.0),
        Product(4, "Produto 4", 40.0),
        Product(5, "Produto 5", 45.0),
        Product(6, "Produto 6", 50.0),
        Product(7, "Produto 7", 55.0),
        Product(8, "Produto 8", 60.0),
        Product(9, "Produto 9", 65.0)
    )

    /**
     * Função para adicionar produtos ao carrinho
     **/
    fun addToCart(product: Product) {
        val existingItem = _cartItems.find { it.product.uuid == product.uuid }

        if (existingItem != null) {
            val index = _cartItems.indexOf(existingItem)
            _cartItems[index] = existingItem.copy(quantity = existingItem.quantity + 1)
        } else {
            _cartItems.add(CartItem(product, 1))
        }
    }

    /**
     * Função para aumentar quantidade de produtos no carrinho
     **/
    fun incrementQuantity(productUUID: Int) {
        val item = _cartItems.find { it.product.uuid == productUUID }

        if (item != null) {
            val index = _cartItems.indexOf(item)
            _cartItems[index] = item.copy(quantity = item.quantity + 1)
        }
    }

    /**
     * Função para diminuir quantidade de produtos no carrinho
     **/
    fun decrementQuantity(productUUID: Int) {
        val item = _cartItems.find { it.product.uuid == productUUID }

        if (item != null) {
            val index = _cartItems.indexOf(item)

            if (item.quantity > 1) {
                _cartItems[index] = item.copy(quantity = item.quantity - 1)
            } else {
                _cartItems.removeAt(index)
            }
        }
    }

    /**
     * Função para remover produtos do carrinho
     **/
    fun removeFromCart(productUUID: Int) {
        _cartItems.removeAll { it.product.uuid == productUUID }
    }

    /**
    * Função para limpar o carrinho
    **/
    fun clearCart() {
        _cartItems.clear()
    }

    // No seu OrderViewModel, adicione:

    private val _salesHistory = mutableStateListOf<SoldItem>()
    private var _totalSalesAmount = 0.0

    fun completeSale(paymentMethod: PaymentMethod, observations: String, cashAmount: Double?) {
        // Adiciona os itens do carrinho atual no histórico
        _cartItems.forEach { cartItem ->
            _salesHistory.add(
                SoldItem(
                    productName = cartItem.product.name,
                    quantity = cartItem.quantity,
                    unitPrice = cartItem.product.price,
                    totalPrice = cartItem.product.price * cartItem.quantity
                )
            )
        }

        // Atualiza o total de vendas
        _totalSalesAmount += totalPrice

        // Limpa o carrinho
        _cartItems.clear()
    }

    fun getSoldItemsHistory(): List<SoldItem> {
        return _salesHistory.toList()
    }

    fun getTotalSales(): Double {
        return _totalSalesAmount
    }

    fun clearSalesHistory() {
        _salesHistory.clear()
        _totalSalesAmount = 0.0
    }
}