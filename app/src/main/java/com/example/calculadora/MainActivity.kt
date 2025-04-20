package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadora.ui.theme.CalculadoraTheme

class Calculadora : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                VistaCalculadora()
            }
        }
    }
}

@Composable
fun VistaCalculadora() {

    var pantalla by remember { mutableStateOf("0") }
    var operacionActual by remember { mutableStateOf("") }
    var valorAnterior by remember { mutableStateOf("") }
    var limpiarPantalla by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {

        Text(
            text = pantalla,
            fontSize = 48.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
                .weight(1f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        val botonesCalc = listOf(
            listOf("Limpiar", "", "", ""),
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", ".", "=", "+")
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
        ) {
            botonesCalc.forEach { fila ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    fila.forEach { texto ->
                        if (texto.isNotEmpty()) {
                            BotonCalc(
                                texto = texto,
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f),
                                onClick = {
                                    when (texto) {
                                        in listOf("+", "-", "*", "/") -> {
                                            if (valorAnterior.isNotEmpty() && operacionActual.isNotEmpty()) {
                                                pantalla = calcular(valorAnterior, pantalla, operacionActual)
                                                valorAnterior = pantalla
                                            } else {
                                                valorAnterior = pantalla
                                            }
                                            operacionActual = texto
                                            limpiarPantalla = true
                                        }
                                        "=" -> {
                                            val resultado = calcular(valorAnterior, pantalla, operacionActual)
                                            pantalla = resultado
                                            operacionActual = ""
                                            valorAnterior = ""
                                            limpiarPantalla = true
                                        }
                                        "Limpiar" -> {
                                            pantalla = "0"
                                            operacionActual = ""
                                            valorAnterior = ""
                                            limpiarPantalla = false
                                        }
                                        "." -> {
                                            if (!pantalla.contains(".")) {
                                                pantalla += "."
                                                limpiarPantalla = false
                                            }
                                        }
                                        else -> {
                                            pantalla = if (pantalla == "0" || limpiarPantalla) texto else pantalla + texto
                                            limpiarPantalla = false
                                        }
                                    }
                                }
                            )
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BotonCalc(
    texto: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (texto) {
                "+", "-", "*", "/" -> Color(0xFFFF9500)
                "Limpiar" -> Color(0xFFFF3B30)
                "=" -> Color(0xFF34C759)
                else -> Color(0xFF333333)
            }
        )
    ) {
        Text(
            text = texto,
            fontSize = 24.sp,
            color = Color.White
        )
    }
}

fun calcular(valor1: String, valor2: String, operacion: String): String {
    val num1 = valor1.toDoubleOrNull() ?: return "Error"
    val num2 = valor2.toDoubleOrNull() ?: return "Error"

    val resultado = when (operacion) {
        "+" -> num1 + num2
        "-" -> num1 - num2
        "*" -> num1 * num2
        "/" -> if (num2 != 0.0) num1 / num2 else return "Error"
        else -> return "Error"
    }

    return resultado.toString().removeSuffix(".0")
}

@Preview(showBackground = true)
@Composable
fun PreviaCalc() {
    CalculadoraTheme {
        VistaCalculadora()
    }
}

