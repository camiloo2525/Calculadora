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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .padding(16.dp)
    ) {

        Text(
            text = "0",
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
            listOf("7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", ".", "=", "+"),
            listOf("Limpiar")
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
                        BotonCalc(
                            texto = texto,
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BotonCalc(
    texto: String,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = { },
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = when (texto) {
                "+", "-", "*", "/" -> Color(0xFFFF9500) // Naranja
                "Limpiar" -> Color(0xFFFF3B30) // Rojo
                "=" -> Color(0xFF34C759) // Verde
                else -> Color(0xFF333333) // Gris
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

@Preview(showBackground = true)
@Composable
fun PreviaCalc() {
    CalculadoraTheme {
        VistaCalculadora()
    }
}