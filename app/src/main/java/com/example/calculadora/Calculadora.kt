package com.example.calculadora


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyCalculadora() {

    var display by remember { mutableStateOf("0") }
    var primerNumero by remember { mutableStateOf<Double?>(null) }
    var operacion by remember { mutableStateOf<String?>(null) }
    var limpiarPantalla by remember { mutableStateOf(false) }

    fun calcularResultado(segundoNumero: Double): String {
        return try {
            val resultado = when (operacion) {
                "+" -> primerNumero!! + segundoNumero
                "-" -> primerNumero!! - segundoNumero
                "×" -> primerNumero!! * segundoNumero
                "÷" -> {
                    if (segundoNumero == 0.0) {
                        return "Error"
                    }
                    primerNumero!! / segundoNumero
                }
                else -> segundoNumero
            }

            if (resultado % 1 == 0.0) {
                resultado.toInt().toString()
            } else {
                resultado.toString()
            }

        } catch (e: Exception) {
            "Error"
        }
    }

    val botones = listOf(
        listOf("C", "DEL", "="),
        listOf("7", "8", "9"),
        listOf("4", "5", "6"),
        listOf("1", "2", "3"),
        listOf("0", "+", "-"),
        listOf("×", "÷")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {

        Text(
            text = display,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center,
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold
        )

        botones.forEach { fila ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                fila.forEach { boton ->

                    Button(
                        onClick = {

                            when (boton) {

                                in "0".."9" -> {
                                    display =
                                        if (display == "0" || limpiarPantalla)
                                            boton
                                        else
                                            display + boton

                                    limpiarPantalla = false
                                }

                                "+", "-", "×", "÷" -> {
                                    primerNumero = display.toDoubleOrNull()
                                    operacion = boton
                                    limpiarPantalla = true
                                }

                                "=" -> {
                                     val segundoNumero =
                                        display.toDoubleOrNull() ?: 0.0

                                    if (primerNumero != null &&
                                        operacion != null
                                    ) {
                                        display =
                                            calcularResultado(segundoNumero)

                                        primerNumero =
                                            display.toDoubleOrNull()

                                        limpiarPantalla = true
                                    }
                                }

                                "C" -> {
                                    display = "0"
                                    primerNumero = null
                                    operacion = null
                                    limpiarPantalla = false
                                }

                                "DEL" -> {
                                    display =
                                        if (display.length > 1) {
                                            display.dropLast(1)
                                        } else {
                                            "0"
                                        }
                                }
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(70.dp)
                    ) {
                        Text(
                            text = boton,
                            fontSize = 24.sp
                        )
                    }
                }

                if (fila.size < 3) {
                    Spacer(
                        modifier = Modifier.weight(
                            (3 - fila.size).toFloat()
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



