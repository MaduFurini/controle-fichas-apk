package com.sacramentum.apk.com.sacramentum.apk.view.managementPanel

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.LightBrownBackground
import com.sacramentum.apk.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun ProcessingSaleScreen(onComplete: () -> Unit) {
    var currentStep by remember { mutableStateOf(0) }

    // Animação de progresso
    val progress by animateFloatAsState(
        targetValue = currentStep / 3f,
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "progress"
    )

    // Animação da notinha caindo
    val infiniteTransition = rememberInfiniteTransition(label = "receipt")
    val receiptOffset by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "receiptOffset"
    )

    LaunchedEffect(Unit) {
        delay(1000)
        currentStep = 1
        delay(1000)
        currentStep = 2
        delay(1000)
        currentStep = 3
        delay(1500)
        onComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBrownBackground)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animação da notinha sendo impressa
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                // Impressora
                PrinterAnimation(receiptOffset = receiptOffset)
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Processando Venda",
                fontSize = 36.sp,
                color = DarkBrown,
                fontFamily = Typography.titleLarge.fontFamily,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = when (currentStep) {
                    0 -> "Iniciando processamento..."
                    1 -> "Registrando venda..."
                    2 -> "Imprimindo fichas..."
                    else -> "Imprimindo comprovante..."
                },
                fontSize = 20.sp,
                color = DarkBrown.copy(alpha = 0.8f),
                fontFamily = Typography.titleSmall.fontFamily
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Barra de progresso personalizada
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 60.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(
                            color = DarkBrown.copy(alpha = 0.2f),
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .fillMaxHeight()
                            .background(
                                color = DarkBrown,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
                            )
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${(progress * 100).toInt()}%",
                    fontSize = 24.sp,
                    color = DarkBrown,
                    fontFamily = Typography.titleSmall.fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            // Ícones de status
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                modifier = Modifier.padding(horizontal = 40.dp)
            ) {
                StatusIcon(
                    icon = "💰",
                    label = "Venda",
                    isActive = currentStep >= 1,
                    isComplete = currentStep > 1
                )
                StatusIcon(
                    icon = "📄",
                    label = "Nota Fiscal",
                    isActive = currentStep >= 2,
                    isComplete = currentStep > 2
                )
                StatusIcon(
                    icon = "🖨️",
                    label = "Impressão",
                    isActive = currentStep >= 3,
                    isComplete = false
                )
            }
        }
    }
}

@Composable
fun PrinterAnimation(receiptOffset: Float) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val centerX = size.width / 2
        val printerTop = size.height / 3

        // Corpo da impressora
        drawRoundRect(
            color = DarkBrown,
            topLeft = Offset(centerX - 80, printerTop),
            size = Size(160f, 100f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
        )

        // Slot de saída
        drawRect(
            color = Color(0xFFE8D5C4),
            topLeft = Offset(centerX - 60, printerTop + 90),
            size = Size(120f, 20f)
        )

        // Notinha saindo (animada)
        val receiptY = printerTop + 110 + receiptOffset.coerceIn(0f, 150f)

        // Papel da nota
        drawRoundRect(
            color = Color.White,
            topLeft = Offset(centerX - 50, receiptY),
            size = Size(100f, 140f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
        )

        // Borda da nota
        drawRoundRect(
            color = DarkBrown,
            topLeft = Offset(centerX - 50, receiptY),
            size = Size(100f, 140f),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f),
            style = Stroke(width = 2f)
        )

        // Linhas da nota (simulando texto impresso)
        val lineSpacing = 12f
        for (i in 0..8) {
            val lineY = receiptY + 20 + (i * lineSpacing)
            if (lineY < receiptY + 140) {
                drawLine(
                    color = DarkBrown.copy(alpha = 0.4f),
                    start = Offset(centerX - 40, lineY),
                    end = Offset(centerX + 40, lineY),
                    strokeWidth = 2f
                )
            }
        }

        // Padrão serrilhado no topo da nota
        val zigzagPath = Path().apply {
            var x = centerX - 50
            moveTo(x, receiptY)
            while (x < centerX + 50) {
                lineTo(x + 5, receiptY - 3)
                lineTo(x + 10, receiptY)
                x += 10
            }
        }
        drawPath(
            path = zigzagPath,
            color = DarkBrown,
            style = Stroke(width = 2f)
        )

        // LED indicador na impressora
        drawCircle(
            color = Color(0xFF4CAF50),
            radius = 6f,
            center = Offset(centerX + 60, printerTop + 20)
        )
    }
}

@Composable
fun StatusIcon(
    icon: String,
    label: String,
    isActive: Boolean,
    isComplete: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    color = when {
                        isComplete -> DarkBrown
                        isActive -> DarkBrown.copy(alpha = 0.7f)
                        else -> DarkBrown.copy(alpha = 0.2f)
                    },
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isComplete) {
                Text(
                    text = "✓",
                    fontSize = 30.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = icon,
                    fontSize = 28.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = label,
            fontSize = 14.sp,
            color = if (isActive) DarkBrown else DarkBrown.copy(alpha = 0.5f),
            fontFamily = Typography.titleSmall.fontFamily,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}