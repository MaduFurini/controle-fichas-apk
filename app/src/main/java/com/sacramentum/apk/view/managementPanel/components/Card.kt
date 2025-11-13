package com.sacramentum.apk.com.sacramentum.apk.view.managementPanel.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.com.sacramentum.apk.model.Product
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun ProductCard(
    product: Product,
    onAddToCart: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .shadow(
                8.dp,
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 0.dp
                )
            )
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 16.dp,
                    bottomEnd = 16.dp,
                    bottomStart = 0.dp
                )
            )
            .background(LightBrownBackground)
            .height(200.dp)
            .fillMaxWidth()
    ) {
        // Barrinha lateral
        Box(
            modifier = Modifier
                .background(DarkBrown)
                .fillMaxHeight()
                .width(10.dp)
        )

        Box(
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = CircleShape,
                            ambientColor = Color.Black.copy(alpha = 0.2f),
                            spotColor = Color.Black.copy(alpha = 0.3f)
                        )
                        .clip(CircleShape)
                        .background(Color.White)
                        .align(Alignment.CenterVertically)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(
                            product.name,
                            color = DarkBrown,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Typography.titleSmall.fontFamily
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            "R$ %.2f".format(product.price),
                            color = DarkBrown,
                            fontSize = 26.sp,
                            fontFamily = Typography.titleSmall.fontFamily
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(DarkBrown)
                            .clickable { onAddToCart() }
                            .padding(horizontal = 20.dp, vertical = 10.dp)
                            .align(Alignment.End)
                    ) {
                        Text(
                            "Adicionar",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontFamily = Typography.titleSmall.fontFamily
                        )
                    }
                }
            }
        }
    }
}