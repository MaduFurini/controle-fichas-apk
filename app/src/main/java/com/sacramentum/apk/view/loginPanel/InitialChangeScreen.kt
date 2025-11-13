package com.sacramentum.apk.com.sacramentum.apk.view.caixa

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.R
import com.sacramentum.apk.com.sacramentum.apk.utils.PriceMask
import com.sacramentum.apk.com.sacramentum.apk.utils.rememberPriceFieldState
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.MainBackround
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun InitialChange(onConfigure: (Double) -> Unit) {
    val priceState = rememberPriceFieldState()
    var showError by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(MainBackround),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .width(500.dp)
                    .wrapContentHeight()
                    .shadow(16.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = LightBrownBackground
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .background(DarkBrown)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Image(
                        painter = painterResource(id = R.drawable.logo_sacramentum),
                        contentDescription = "Logo Sacramentum",
                        modifier = Modifier.size(140.dp),
                        contentScale = ContentScale.Crop,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Sacramentum",
                        fontFamily = Typography.titleLarge.fontFamily,
                        fontSize = 38.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkBrown
                    )

                    Text(
                        text = "Gerenciamento de Festividades",
                        modifier = Modifier.padding(horizontal = 32.dp),
                        textAlign = TextAlign.Center,
                        color = DarkBrown.copy(alpha = 0.7f),
                        fontFamily = Typography.titleSmall.fontFamily,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        OutlinedTextField(
                            value = priceState.text,
                            onValueChange = {
                                priceState.updateValue(it)
                                showError = false
                            },
                            label = {
                                Text(
                                    "Valor do Troco Inicial",
                                    fontFamily = Typography.bodyMedium.fontFamily
                                )
                            },
                            placeholder = { Text("0,00") },
                            leadingIcon = {
                                Text(
                                    "R$",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = DarkBrown
                                )
                            },
                            visualTransformation = PriceMask(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.NumberPassword
                            ),
                            isError = showError,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = DarkBrown,
                                unfocusedBorderColor = DarkBrown.copy(alpha = 0.5f),
                                focusedLabelColor = DarkBrown,
                                unfocusedLabelColor = DarkBrown.copy(alpha = 0.7f),
                                cursorColor = DarkBrown,
                                errorBorderColor = Color(0xFFD32F2F),
                                errorLabelColor = Color(0xFFD32F2F)
                            ),
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = Typography.bodyLarge.fontFamily
                            )
                        )

                        if (showError) {
                            Text(
                                text = "⚠ Informe um valor válido para o troco inicial",
                                color = Color(0xFFD32F2F),
                                fontSize = 13.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 4.dp)
                            )
                        }

                        if (priceState.value > 0) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = DarkBrown.copy(alpha = 0.1f)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Troco Inicial",
                                        fontSize = 14.sp,
                                        color = DarkBrown.copy(alpha = 0.7f),
                                        fontFamily = Typography.bodySmall.fontFamily
                                    )
                                    Text(
                                        text = priceState.formattedValue,
                                        fontSize = 32.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = DarkBrown,
                                        fontFamily = Typography.titleLarge.fontFamily
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (priceState.value <= 0) {
                                showError = true
                            } else {
                                onConfigure(priceState.value)
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBrown
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = "Iniciar Caixa",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Text(
            text = "Desenvolvido com carinho pelos estudantes de engenharia de software - UNIFAE",
            fontSize = 16.sp,
            color = DarkBrown,
            fontFamily = Typography.titleSmall.fontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}