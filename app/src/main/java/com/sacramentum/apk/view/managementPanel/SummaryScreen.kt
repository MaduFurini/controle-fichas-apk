package com.sacramentum.apk.com.sacramentum.apk.view.managementPanel

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.com.sacramentum.apk.model.CartItem
import com.sacramentum.apk.com.sacramentum.apk.utils.PrinterUtils
import com.sacramentum.apk.ui.theme.BrownBackground
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography

enum class PaymentMethod {
    CREDIT_CARD,
    DEBIT_CARD,
    PIX,
    CASH
}

@Composable
fun SummaryScreen(
    cartItems: List<CartItem>,
    totalPrice: Double,
    onConfirm: (PaymentMethod, String, Double?) -> Unit,
    onBack: () -> Unit
) {
    var selectedPayment by remember { mutableStateOf<PaymentMethod?>(null) }
    var observations by remember { mutableStateOf("") }
    var cashAmount by remember { mutableStateOf("") }

    val change = if (selectedPayment == PaymentMethod.CASH && cashAmount.isNotEmpty()) {
        val amount = cashAmount.toDoubleOrNull() ?: 0.0
        if (amount >= totalPrice) amount - totalPrice else 0.0
    } else {
        0.0
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Área principal - Produtos
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .background(LightBrownBackground)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrownBackground)
                    .padding(30.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "← Voltar",
                    color = DarkBrown,
                    fontSize = 18.sp,
                    fontFamily = Typography.titleSmall.fontFamily,
                    modifier = Modifier.clickable { onBack() }
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    "Resumo do Pedido",
                    color = DarkBrown,
                    fontSize = 28.sp,
                    fontFamily = Typography.titleSmall.fontFamily,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            // Lista de produtos
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                item {
                    Text(
                        "Produtos",
                        color = DarkBrown,
                        fontSize = 24.sp,
                        fontFamily = Typography.titleSmall.fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                items(cartItems) { cartItem ->
                    OrderSummaryItem(cartItem)
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(BrownBackground)
                            .padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Subtotal",
                            color = DarkBrown,
                            fontSize = 20.sp,
                            fontFamily = Typography.titleLarge.fontFamily
                        )
                        Text(
                            "R$ %.2f".format(totalPrice),
                            color = DarkBrown,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Typography.titleSmall.fontFamily
                        )
                    }
                }

                item {
                    // Campo de observações
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(BrownBackground)
                            .padding(20.dp)
                    ) {
                        Text(
                            "Observações",
                            color = DarkBrown,
                            fontSize = 16.sp,
                            fontFamily = Typography.titleSmall.fontFamily,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        BasicTextField(
                            value = observations,
                            onValueChange = { observations = it },
                            textStyle = TextStyle(
                                color = DarkBrown,
                                fontSize = 16.sp,
                                fontFamily = Typography.titleSmall.fontFamily
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .background(Color.White)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(15.dp),
                            decorationBox = { innerTextField ->
                                if (observations.isEmpty()) {
                                    Text(
                                        "Digite observações sobre o pedido...",
                                        color = DarkBrown.copy(alpha = 0.3f),
                                        fontSize = 16.sp
                                    )
                                }
                                innerTextField()
                            }
                        )
                    }
                }
            }
        }

        // Painel lateral - Forma de pagamento
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(450.dp)
                .shadow(
                    elevation = 16.dp,
                    shape = RoundedCornerShape(0.dp),
                    clip = false
                )
                .background(LightBrownBackground)
                .padding(30.dp, 25.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Forma de Pagamento",
                    color = DarkBrown,
                    fontSize = 26.sp,
                    fontFamily = Typography.titleSmall.fontFamily,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(25.dp))

                // Opções de pagamento
                PaymentOption(
                    title = "Cartão de Crédito",
                    icon = "💳",
                    isSelected = selectedPayment == PaymentMethod.CREDIT_CARD,
                    onClick = { selectedPayment = PaymentMethod.CREDIT_CARD }
                )

                Spacer(modifier = Modifier.height(15.dp))

                PaymentOption(
                    title = "Cartão de Débito",
                    icon = "💳",
                    isSelected = selectedPayment == PaymentMethod.DEBIT_CARD,
                    onClick = { selectedPayment = PaymentMethod.DEBIT_CARD }
                )

                Spacer(modifier = Modifier.height(15.dp))

                PaymentOption(
                    title = "PIX",
                    icon = "📱",
                    isSelected = selectedPayment == PaymentMethod.PIX,
                    onClick = { selectedPayment = PaymentMethod.PIX }
                )

                Spacer(modifier = Modifier.height(15.dp))

                PaymentOption(
                    title = "Dinheiro",
                    icon = "💵",
                    isSelected = selectedPayment == PaymentMethod.CASH,
                    onClick = { selectedPayment = PaymentMethod.CASH }
                )

                // Campo de valor em dinheiro (aparece apenas quando selecionado)
                if (selectedPayment == PaymentMethod.CASH) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(BrownBackground)
                            .padding(20.dp)
                    ) {
                        Text(
                            "Valor Recebido",
                            color = DarkBrown,
                            fontSize = 16.sp,
                            fontFamily = Typography.titleSmall.fontFamily,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        BasicTextField(
                            value = cashAmount,
                            onValueChange = { cashAmount = it },
                            textStyle = TextStyle(
                                color = DarkBrown,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = Typography.titleSmall.fontFamily
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clip(RoundedCornerShape(8.dp))
                                .padding(15.dp),
                            decorationBox = { innerTextField ->
                                if (cashAmount.isEmpty()) {
                                    Text(
                                        "R$ 0,00",
                                        color = DarkBrown.copy(alpha = 0.3f),
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                innerTextField()
                            }
                        )

                        if (cashAmount.isNotEmpty() && (cashAmount.toDoubleOrNull() ?: 0.0) >= totalPrice) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Troco:",
                                    color = DarkBrown,
                                    fontSize = 18.sp,
                                    fontFamily = Typography.titleSmall.fontFamily,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    "R$ %.2f".format(change),
                                    color = DarkBrown,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Typography.titleSmall.fontFamily
                                )
                            }
                        }
                    }
                }
            }

            // Footer com total e botão de confirmar
            Column {
                val context = LocalContext.current

                val isValid = selectedPayment != null &&
                        (selectedPayment != PaymentMethod.CASH ||
                                (cashAmount.isNotEmpty() && (cashAmount.toDoubleOrNull() ?: 0.0) >= totalPrice))

                TextButton(
                    onClick = {
                        selectedPayment?.let { payment ->
                            val amount = if (payment == PaymentMethod.CASH && cashAmount.isNotEmpty()) {
                                cashAmount.toDoubleOrNull()
                            } else null

                            val paymentString = when (payment) {
                                PaymentMethod.CREDIT_CARD -> "Cartão de Crédito"
                                PaymentMethod.DEBIT_CARD -> "Cartão de Débito"
                                PaymentMethod.PIX -> "PIX"
                                PaymentMethod.CASH -> "Dinheiro"
                            }

                            PrinterUtils.printProductTickets(
                                context = context,
                                orderItems = cartItems
                            )

                            onConfirm(payment, observations, amount)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (isValid) DarkBrown else DarkBrown.copy(alpha = 0.3f))
                        .padding(15.dp),
                    enabled = isValid
                ) {
                    Text(
                        "Confirmar Pedido",
                        color = if (isValid) Color.White else Color.White.copy(alpha = 0.5f),
                        fontSize = 22.sp,
                        fontFamily = Typography.titleSmall.fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun OrderSummaryItem(cartItem: CartItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(BrownBackground)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                cartItem.product.name,
                color = DarkBrown,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Typography.titleSmall.fontFamily
            )
            Text(
                "Quantidade: ${cartItem.quantity}",
                color = DarkBrown.copy(alpha = 0.7f),
                fontSize = 14.sp,
                fontFamily = Typography.titleSmall.fontFamily
            )
        }

        Text(
            "R$ %.2f".format(cartItem.product.price * cartItem.quantity),
            color = DarkBrown,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Typography.titleSmall.fontFamily
        )
    }
}

@Composable
fun PaymentOption(
    title: String,
    icon: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) DarkBrown else BrownBackground)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) DarkBrown else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            icon,
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.width(15.dp))

        Text(
            title,
            color = if (isSelected) Color.White else DarkBrown,
            fontSize = 20.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            fontFamily = Typography.titleSmall.fontFamily
        )

        Spacer(modifier = Modifier.weight(1f))

        if (isSelected) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            ) {
                Text(
                    "✓",
                    color = DarkBrown,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}