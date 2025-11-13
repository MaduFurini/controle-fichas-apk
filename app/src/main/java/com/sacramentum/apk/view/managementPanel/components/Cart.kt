package com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.com.sacramentum.apk.model.CartItem
import com.sacramentum.apk.ui.theme.BrownBackground
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun Cart(
    cartItems: List<CartItem>,
    totalPrice: Double,
    onIncrement: (Int) -> Unit,
    onDecrement: (Int) -> Unit,
    onRemove: (Int) -> Unit,
    onNext: () -> Unit
) {
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
            .clip(RoundedCornerShape(0.dp)),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(20.dp, 25.dp, 30.dp, 15.dp)
        ) {
            Text(
                "Resumo",
                color = DarkBrown,
                fontSize = 30.sp,
                fontFamily = Typography.titleSmall.fontFamily,
                fontWeight = FontWeight.ExtraBold,
            )

            Spacer(modifier = Modifier.height(15.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(11.dp))
                    .fillMaxWidth()
                    .background(BrownBackground)
                    .weight(1f)
            ) {
                if (cartItems.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Carrinho vazio",
                            color = DarkBrown.copy(alpha = 0.5f),
                            fontSize = 20.sp,
                            fontFamily = Typography.titleSmall.fontFamily
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(20.dp),
                        verticalArrangement = Arrangement.spacedBy(30.dp)
                    ) {
                        items(cartItems) { cartItem ->
                            CartItemView(
                                cartItem = cartItem,
                                onIncrement = { onIncrement(cartItem.product.uuid) },
                                onDecrement = { onDecrement(cartItem.product.uuid) }
                            )
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .background(BrownBackground)
                .height(100.dp)
                .padding(30.dp, 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "R$ %.2f".format(totalPrice),
                color = DarkBrown,
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Typography.titleLarge.fontFamily
            )

            TextButton(
                onClick = { println("Próximo - Total: R$ %.2f".format(totalPrice)) },
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(DarkBrown)
                    .padding(10.dp, 1.dp),
                enabled = cartItems.isNotEmpty()
            ) {
                TextButton(
                    onClick = { onNext() },
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(DarkBrown)
                        .padding(10.dp, 1.dp),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text(
                        "Próximo",
                        color = if (cartItems.isNotEmpty()) Color.White else Color.White.copy(alpha = 0.5f),
                        fontSize = 20.sp,
                        fontFamily = Typography.titleSmall.fontFamily
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemView(
    cartItem: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .background(Color.White)
                .align(Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    cartItem.product.name,
                    color = DarkBrown,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = Typography.titleSmall.fontFamily
                )

                Text(
                    "R$ %.2f".format(cartItem.product.price),
                    color = DarkBrown,
                    fontSize = 18.sp,
                    fontFamily = Typography.titleSmall.fontFamily
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                QuantitySelector(
                    quantity = cartItem.quantity,
                    onIncrement = onIncrement,
                    onDecrement = onDecrement
                )
                Text(
                    "R$ %.2f".format(cartItem.product.price * cartItem.quantity),
                    color = DarkBrown,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun QuantitySelector(
    quantity: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                .background(DarkBrown)
                .width(30.dp)
                .height(45.dp)
                .clickable { onDecrement() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "-",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Box(
            modifier = Modifier
                .background(LightBrownBackground)
                .width(38.dp)
                .height(45.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                quantity.toString(),
                color = DarkBrown,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp))
                .background(DarkBrown)
                .width(30.dp)
                .height(45.dp)
                .clickable { onIncrement() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                "+",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
