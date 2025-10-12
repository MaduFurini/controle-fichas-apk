package com.sacramentum.apk.com.sacramentum.apk.view.components


import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sacramentum.apk.ui.theme.DarkBrown
import com.sacramentum.apk.ui.theme.Red

@Composable
fun CustomInputField (
    code: String,
    text: String,
    onCodeChange: (String) -> Unit,
    isError: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val focusRequesters = remember { List(6) { FocusRequester() } }
    val codeChars = code.take(6).padEnd(6, ' ')

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isError) Red else DarkBrown,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .border(
                            width = 2.dp,
                            color = when {
                                isError -> Red
                                codeChars[index] != ' ' -> DarkBrown
                                else -> DarkBrown
                            },
                            shape = MaterialTheme.shapes.medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = if (codeChars[index] == ' ') "" else codeChars[index].toString(),
                        onValueChange = { newChar ->
                            if (newChar.length <= 1 && newChar.all { it.isLetterOrDigit() }) {
                                val newCode = codeChars.toCharArray()
                                newCode[index] = newChar.uppercase().firstOrNull() ?: ' '
                                val result = newCode.concatToString().trim()
                                onCodeChange(result)

                                // Move para o próximo campo se digitou algo
                                if (newChar.isNotEmpty() && index < 5) {
                                    focusRequesters[index + 1].requestFocus()
                                }
                            } else if (newChar.isEmpty()) {
                                // Backspace - limpa o campo atual e volta pro anterior
                                val newCode = codeChars.toCharArray()
                                newCode[index] = ' '
                                onCodeChange(newCode.concatToString().trim())

                                if (index > 0) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .focusRequester(focusRequesters[index]),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (codeChars[index] == ' ') {
                                    Text(
                                        text = "-",
                                        fontSize = 24.sp,
                                        color = DarkBrown,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            }
        }

        if (isError) {
            Text(
                text = "Informe o código completo (6 caracteres)",
                color = Color(0xFFD32F2F),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .align(Alignment.Start)
            )
        }
    }
}