package com.sacramentum.apk.com.sacramentum.apk.view.loginPanel

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lint.kotlin.metadata.Visibility
import com.sacramentum.apk.R
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography

@Composable
fun LoginScreen(onConfigure: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    
    var passwordVisible by remember { mutableStateOf(false) }
    
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
                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = it.isBlank()
                        },
                        label = { Text("Email") },
                        modifier = Modifier
                            .width(360.dp)
                            .padding(top = 8.dp),
                        isError = emailError,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Ícone de Usuário",
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
                        if (emailError) {
                            Text(
                                text = "Informe seu email",
                                color = Color(0xFFD32F2F),
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 55.dp)
                            )
                        }
                    }

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = it.isBlank()
                        },
                        label = { Text("Senha") },
                        modifier = Modifier
                            .width(360.dp)
                            .padding(top = 8.dp),
                        isError = passwordError,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = "Ícone de Cadeado",
                                tint = DarkBrown,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        trailingIcon = {
                            val visibilityIcon = if (passwordVisible)
                                Icons.Default.Close
                            else
                                Icons.Default.Check

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = visibilityIcon,
                                    contentDescription = if (passwordVisible) "Ocultar senha" else "Mostrar senha",
                                    tint = DarkBrown
                                )
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),

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
                        if (passwordError) {
                            Text(
                                text = "Informe sua senha",
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
                            emailError = email.isBlank()
                            passwordError = password.isBlank()

                            onConfigure()
                        },
                        shape = RoundedCornerShape(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkBrown
                        ),
                    ) {
                        Text(text = "Entrar")
                    }
                }
            }
        }
    }
}