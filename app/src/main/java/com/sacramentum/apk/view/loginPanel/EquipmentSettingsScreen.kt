package com.sacramentum.apk.com.sacramentum.apk.view.loginPanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.R
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun EquipmentSettingsScreen(onConfigure: () -> Unit) {
    var communityCode by remember { mutableStateOf("") }
    var eventCode by remember { mutableStateOf("") }

    var communityCodeError by remember { mutableStateOf(false) }
    var eventCodeError by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(460.dp)
                .fillMaxHeight(0.7f)
                .shadow(8.dp, RoundedCornerShape(8.dp)),
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = LightBrownBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(DarkBrown)
                )

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.logo_sacramentum),
                    contentDescription = "Logo Sacramentum",
                    modifier = Modifier.size(170.dp),
                    contentScale = ContentScale.Crop,
                )

                Text(
                    text = "Sacramentum",
                    fontFamily = Typography.titleLarge.fontFamily,
                    fontSize = 35.sp,
                    color = DarkBrown
                )

                Text(
                    text = "Gerenciamento de Festividades",
                    modifier = Modifier.width(350.dp),
                    textAlign = TextAlign.Center,
                    color = DarkBrown,
                    fontFamily = Typography.titleSmall.fontFamily,
                    fontSize = 18.sp
                )

                Column(
                    modifier = Modifier.padding(top = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Código Comunidade
                    OutlinedTextField(
                        value = communityCode,
                        onValueChange = {
                            communityCode = it
                            communityCodeError = it.isBlank()
                        },
                        label = { Text("Código da Comunidade") },
                        modifier = Modifier
                            .width(360.dp)
                            .padding(top = 8.dp),
                        isError = communityCodeError,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ícone de Localização",
                                tint = DarkBrown,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkBrown,
                            focusedLabelColor = DarkBrown,
                            cursorColor = DarkBrown,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color(0xFFD32F2F),
                            errorLabelColor = Color(0xFFD32F2F),
                        )
                    )

                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                    ) {
                        if (communityCodeError) {
                            Text(
                                text = "Informe o código da comunidade",
                                color = Color(0xFFD32F2F),
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 55.dp)
                            )
                        }
                    }

                    // Código Evento
                    OutlinedTextField(
                        value = eventCode,
                        onValueChange = {
                            eventCode = it
                            eventCodeError = it.isBlank()
                        },
                        label = { Text("Código do Evento") },
                        modifier = Modifier
                            .width(360.dp)
                            .padding(top = 8.dp),
                        isError = eventCodeError,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Ícone de Cadeado",
                                tint = DarkBrown,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkBrown,
                            focusedLabelColor = DarkBrown,
                            cursorColor = DarkBrown,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color(0xFFD32F2F),
                            errorLabelColor = Color(0xFFD32F2F),
                        )
                    )

                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                    ) {
                        if (eventCodeError) {
                            Text(
                                text = "Informe o código do evento",
                                color = Color(0xFFD32F2F),
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 55.dp)
                            )
                        }
                    }
                }

                // Botão
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier.padding(end = 30.dp),
                        onClick = {
                            communityCodeError = communityCode.isBlank()
                            eventCodeError = eventCode.isBlank()

                            onConfigure()
                        },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBrown
                        ),
                    ) {
                        Text(text = "Configurar")
                    }
                }
            }
        }
    }
}
