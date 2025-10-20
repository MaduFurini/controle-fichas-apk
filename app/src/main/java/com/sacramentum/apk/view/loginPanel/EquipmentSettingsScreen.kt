package com.sacramentum.apk.com.sacramentum.apk.view.loginPanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.R
import com.sacramentum.apk.com.sacramentum.apk.view.loginPanel.components.CustomInputField
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.MainBackround
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun EquipmentSettingsScreen(onConfigure: () -> Unit) {
    var communityCode by remember { mutableStateOf("") }
    var eventCode by remember { mutableStateOf("") }

    var communityCodeError by remember { mutableStateOf(false) }
    var eventCodeError by remember { mutableStateOf(false) }

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
                        verticalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Código da Comunidade
                        CustomInputField(
                            text = "Código da Comunidade",
                            code = communityCode,
                            onCodeChange = {
                                communityCode = it
                                communityCodeError = false
                            },
                            isError = communityCodeError,
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (communityCodeError) {
                            Text(
                                text = "⚠ Informe o código completo da comunidade (6 caracteres)",
                                color = Color(0xFFD32F2F),
                                fontSize = 13.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 4.dp)
                            )
                        }

                        // Código do Evento
                        CustomInputField(
                            text = "Código do Evento",
                            code = eventCode,
                            onCodeChange = {
                                eventCode = it
                                eventCodeError = false
                            },
                            isError = eventCodeError,
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (eventCodeError) {
                            Text(
                                text = "⚠ Informe o código completo do evento (6 caracteres)",
                                color = Color(0xFFD32F2F),
                                fontSize = 13.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            communityCodeError = communityCode.length < 6
                            eventCodeError = eventCode.length < 6

                            if (!communityCodeError && !eventCodeError) {
                                onConfigure()
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
                            text = "Configurar",
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
        )
    }
}