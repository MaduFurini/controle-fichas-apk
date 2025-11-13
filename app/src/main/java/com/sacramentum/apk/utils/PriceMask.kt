package com.sacramentum.apk.com.sacramentum.apk.utils

import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import java.text.NumberFormat
import java.util.Locale

/**
 * Máscara de preço para campos monetários (R$)
 * Formata automaticamente valores como: R$ 1.234,56
 */
class PriceMask : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val originalText = text.text
        val formattedText = formatPrice(originalText)

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return formattedText.length
            }

            override fun transformedToOriginal(offset: Int): Int {
                return originalText.length
            }
        }

        return TransformedText(
            AnnotatedString(formattedText),
            offsetMapping
        )
    }

    private fun formatPrice(text: String): String {
        if (text.isEmpty()) return ""

        val cleanText = text.replace(Regex("[^0-9]"), "")
        if (cleanText.isEmpty()) return ""

        val value = cleanText.toLongOrNull() ?: return ""
        val doubleValue = value / 100.0

        return NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
            .format(doubleValue)
    }
}

/**
 * Função auxiliar para formatar valor em string de preço
 */
fun formatToPriceString(value: Double): String {
    return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(value)
}

/**
 * Função para remover formatação e obter valor numérico
 */
fun parsePriceToDouble(formattedPrice: String): Double {
    val cleanText = formattedPrice.replace(Regex("[^0-9]"), "")
    if (cleanText.isEmpty()) return 0.0
    return (cleanText.toLongOrNull() ?: 0) / 100.0
}

/**
 * Composable que gerencia o estado de um campo com máscara de preço
 */
@Composable
fun rememberPriceFieldState(initialValue: Double = 0.0): PriceFieldState {
    return remember { PriceFieldState(initialValue) }
}

class PriceFieldState(initialValue: Double = 0.0) {
    // Valor interno armazenado em centavos para evitar problemas de precisão
    private var _valueInCents by mutableStateOf((initialValue * 100).toLong())

    // Texto exibido no campo (apenas números)
    var text by mutableStateOf(if (initialValue > 0) {
        String.format("%d", _valueInCents)
    } else {
        ""
    })
        private set

    val value: Double
        get() = _valueInCents / 100.0

    val formattedValue: String
        get() = if (_valueInCents == 0L) "R$ 0,00" else formatToPriceString(value)

    fun updateValue(newText: String) {
        // Remove tudo que não é número
        val cleanText = newText.replace(Regex("[^0-9]"), "")

        // Limita a 10 dígitos (R$ 99.999.999,99)
        val limitedText = cleanText.take(10)

        text = limitedText
        _valueInCents = limitedText.toLongOrNull() ?: 0L
    }

    fun clear() {
        text = ""
        _valueInCents = 0L
    }

    fun setValue(newValue: Double) {
        _valueInCents = (newValue * 100).toLong()
        text = if (_valueInCents > 0) {
            String.format("%d", _valueInCents)
        } else {
            ""
        }
    }
}
