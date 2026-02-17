package joto.demo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        // inicio interfaz
        var textoCaja1 by remember { mutableStateOf("") }
        var textoCaja2 by remember { mutableStateOf("") }
        var textoCaja3 by remember { mutableStateOf("") }
        var fechaNacimiento by remember { mutableStateOf("") }
        val resultado = generarRFC(textoCaja1, textoCaja2, textoCaja3, fechaNacimiento)


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),

            // Alineaar composables
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // 1. Titulo
            Text(
                text = "Calculador de RCF",
                color = Color.Red,
                fontSize = 38.sp,
                fontWeight = FontWeight.ExtraLight
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Nombre
            TextField(
                value = textoCaja1,
                onValueChange = { textoCaja1 = it },
                label = { Text("Nombre/s") },
                placeholder = { Text("Pepito") },

            )

            Spacer(modifier = Modifier.height(10.dp))

            // 3. Apellido Paterno
            TextField(
                value = textoCaja2,
                onValueChange = { textoCaja2 = it },
                label = { Text("Apellido Paterno") },
                placeholder = { Text("Ej. Perez") },

            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Apellido Materno
            TextField(
                value = textoCaja3,
                onValueChange = { textoCaja3 = it }, // 'it' es lo nuevo que escribiste
                label = { Text("Apellido Materno") },
                placeholder = { Text("Ej. Martinez") },

                )

            Spacer(modifier = Modifier.height(20.dp))

            // 5. Fecha Nacimiento
            TextField(
                value = fechaNacimiento,
                onValueChange = { fechaNacimiento = it },
                label = { Text("Fecha de Nacimiento") },
                placeholder = { Text("Ej. 12/05/2000") },

                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                    keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            //4. Texto que nos indica el RFF
            Text(
                text = "$resultado",
                color = Color.Black,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraLight
            )

        }
    }
}

// Magia del RFC
fun generarRFC(nombre: String, paterno: String, materno: String, fecha: String): String {
    val nom = nombre.uppercase().trim()
    val pat = paterno.uppercase().trim()
    val mat = materno.uppercase().trim()

    // Regla 1. Apellido Paterno
    val letra1 = if (pat.isNotEmpty()) pat[0] else '_'
    val vocalInterna = if (pat.length > 1) {
        pat.drop(1).firstOrNull { it in "AEIOU" } ?: 'X'
    } else {
        '_'
    }

    // --- Reglaa 2. apellido materno
    val letra3 = if (mat.isNotEmpty()) mat[0] else '_'
    // --- Regla 3. Nombre ---
    val letra4 = if (nom.isNotEmpty()) nom[0] else '_'

    // --- Regla 4. fecha
    var fechaFormato = "______"
    if (fecha.length == 10) {
        try {
            val partes = fecha.split("/")
            if (partes.size == 3) {
                val anio = partes[2].takeLast(2)
                val mes = partes[1]
                val dia = partes[0]
                fechaFormato = "$anio$mes$dia"
            }
        } catch (e: Exception) {
            fechaFormato = "ERROR "
        }
    }

    // Regla 5. homoclave
    // Solo generamos los randoms si tenemos todos los datos anteriores
    val faltanDatos = (letra1 =='_' || vocalInterna =='_' || letra3 =='_' || letra4 =='_' || fechaFormato.contains('_'))
    val homoclave = if (faltanDatos) {
        "XXX"
    } else {
        val letrasRandom = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        (1..3).map { letrasRandom.random() }.joinToString("")
    }

    return "$letra1$vocalInterna$letra3$letra4$fechaFormato$homoclave"
}