package com.sacramentum.apk.com.sacramentum.apk.view.managementPanel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.com.sacramentum.apk.model.SoldItem
import com.sacramentum.apk.ui.theme.BrownBackground
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun LogoutScreen(
    userName: String,
    userRole: String,
    initialCashAmount: Double,
    soldItems: List<SoldItem>,
    totalSales: Double,
    onLogout: () -> Unit,
    onCancel: () -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var isPrinting by remember { mutableStateOf(false) }
    var printCompleted by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(900.dp)
                .heightIn(max = 800.dp)
                .shadow(24.dp, RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
                .background(LightBrownBackground)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrownBackground)
                    .padding(30.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                "Relatório de Fechamento",
                                color = DarkBrown,
                                fontSize = 32.sp,
                                fontFamily = Typography.titleLarge.fontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "Resumo das vendas do dia",
                                color = DarkBrown.copy(alpha = 0.7f),
                                fontSize = 18.sp,
                                fontFamily = Typography.titleSmall.fontFamily
                            )
                        }

                        // Info do usuário
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.End
                                ) {
                                    Text(
                                        userName,
                                        color = DarkBrown,
                                        fontSize = 20.sp,
                                        fontFamily = Typography.titleSmall.fontFamily,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        userRole,
                                        color = DarkBrown.copy(alpha = 0.7f),
                                        fontSize = 14.sp,
                                        fontFamily = Typography.titleSmall.fontFamily
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(DarkBrown),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        userName.firstOrNull()?.uppercase() ?: "U",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Conteúdo Principal
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(30.dp)
            ) {
                // Cards de Resumo Financeiro
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Troco Inicial
                    FinancialCard(
                        title = "Troco Inicial",
                        value = initialCashAmount,
                        icon = "💵",
                        modifier = Modifier.weight(1f)
                    )

                    // Total Vendido
                    FinancialCard(
                        title = "Total Vendido",
                        value = totalSales,
                        icon = "💰",
                        color = DarkBrown,
                        modifier = Modifier.weight(1f)
                    )

                    // Total no Caixa
                    FinancialCard(
                        title = "Total no Caixa",
                        value = initialCashAmount + totalSales,
                        icon = "🏦",
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Lista de Produtos Vendidos
                Text(
                    "Produtos Vendidos",
                    color = DarkBrown,
                    fontSize = 24.sp,
                    fontFamily = Typography.titleSmall.fontFamily,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(BrownBackground)
                ) {
                    if (soldItems.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Nenhuma venda realizada",
                                color = DarkBrown.copy(alpha = 0.5f),
                                fontSize = 18.sp,
                                fontFamily = Typography.titleSmall.fontFamily
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Header da tabela
                            item {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 10.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Produto",
                                        color = DarkBrown,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(2f)
                                    )
                                    Text(
                                        "Qtd",
                                        color = DarkBrown,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.width(60.dp)
                                    )
                                    Text(
                                        "Unit.",
                                        color = DarkBrown,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.width(100.dp)
                                    )
                                    Text(
                                        "Total",
                                        color = DarkBrown,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.width(120.dp)
                                    )
                                }
                            }

                            items(soldItems) { item ->
                                SoldItemRow(item)
                            }
                        }
                    }
                }
            }

            // Footer com Botões
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BrownBackground)
                    .padding(30.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Botão Cancelar
                TextButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Transparent)
                        .then(
                            Modifier.shadow(
                                elevation = 0.dp,
                                shape = RoundedCornerShape(10.dp)
                            )
                        ),
                    enabled = !isPrinting
                ) {
                    Text(
                        "Cancelar",
                        color = DarkBrown,
                        fontSize = 20.sp,
                        fontFamily = Typography.titleSmall.fontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Botão Imprimir Relatório
                TextButton(
                    onClick = {
                        isPrinting = true
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(if (!isPrinting && !printCompleted) DarkBrown else DarkBrown.copy(alpha = 0.7f)),
                    enabled = !isPrinting && !printCompleted
                ) {
                    if (isPrinting) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                color = Color.White,
                                strokeWidth = 3.dp,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                "Imprimindo...",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontFamily = Typography.titleSmall.fontFamily
                            )
                        }
                    } else {
                        Text(
                            if (printCompleted) "✓ Relatório Impresso" else "🖨️ Imprimir Relatório",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = Typography.titleSmall.fontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Botão Sair
                TextButton(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier
                        .weight(1f)
                        .height(60.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            if (printCompleted) Color(0xFFD32F2F)
                            else Color(0xFFD32F2F).copy(alpha = 0.3f)
                        ),
                    enabled = printCompleted
                ) {
                    Text(
                        "Sair do Sistema",
                        color = if (printCompleted) Color.White else Color.White.copy(alpha = 0.5f),
                        fontSize = 20.sp,
                        fontFamily = Typography.titleSmall.fontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Simulação de impressão
    LaunchedEffect(isPrinting) {
        if (isPrinting) {
            delay(3000) // Simula tempo de impressão
            isPrinting = false
            printCompleted = true
        }
    }

    // Modal de Confirmação
    if (showConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            containerColor = LightBrownBackground,
            shape = RoundedCornerShape(16.dp),
            title = {
                Text(
                    "Confirmar Saída",
                    color = DarkBrown,
                    fontSize = 24.sp,
                    fontFamily = Typography.titleLarge.fontFamily,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Text(
                        "Você tem certeza que deseja sair do sistema?",
                        color = DarkBrown,
                        fontSize = 18.sp,
                        fontFamily = Typography.titleSmall.fontFamily
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "✓ Relatório foi impresso",
                        color = Color(0xFF2E7D32),
                        fontSize = 16.sp,
                        fontFamily = Typography.titleSmall.fontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showConfirmDialog = false
                        onLogout()
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFD32F2F))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        "Confirmar Saída",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmDialog = false },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkBrown.copy(alpha = 0.1f))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        "Cancelar",
                        color = DarkBrown,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
}

@Composable
fun FinancialCard(
    title: String,
    value: Double,
    icon: String,
    color: Color = DarkBrown,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(BrownBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            icon,
            fontSize = 36.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            title,
            color = DarkBrown.copy(alpha = 0.7f),
            fontSize = 14.sp,
            fontFamily = Typography.titleSmall.fontFamily,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            "R$ %.2f".format(value),
            color = color,
            fontSize = 28.sp,
            fontFamily = Typography.titleLarge.fontFamily,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SoldItemRow(item: SoldItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(LightBrownBackground)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            item.productName,
            color = DarkBrown,
            fontSize = 16.sp,
            fontFamily = Typography.titleSmall.fontFamily,
            modifier = Modifier.weight(2f)
        )
        Text(
            "${item.quantity}x",
            color = DarkBrown,
            fontSize = 16.sp,
            fontFamily = Typography.titleSmall.fontFamily,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(60.dp)
        )
        Text(
            "R$ %.2f".format(item.unitPrice),
            color = DarkBrown.copy(alpha = 0.7f),
            fontSize = 16.sp,
            fontFamily = Typography.titleSmall.fontFamily,
            modifier = Modifier.width(100.dp)
        )
        Text(
            "R$ %.2f".format(item.totalPrice),
            color = DarkBrown,
            fontSize = 16.sp,
            fontFamily = Typography.titleSmall.fontFamily,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(120.dp)
        )
    }
}