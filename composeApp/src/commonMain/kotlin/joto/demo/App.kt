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

    val letra1 = if (pat.isNotEmpty()) pat[0] else '_'
    val vocalInterna = if (pat.length > 1) {
        pat.drop(1).firstOrNull { it in "AEIOU" } ?: 'X'
    } else {
        '_'
    }
    val letra3 = if (mat.isNotEmpty()) mat[0] else '_'
    val letra4 = if (nom.isNotEmpty()) nom[0] else '_'
    var fechaFormato = "______"


    if (fecha.contains("/")) {
        val partes = fecha.split("/")
        if (partes.size == 3) {
            val dia = partes[0]
            val mes = partes[1]
            val anio = partes[2]
            val diaInt = dia.toIntOrNull()
            val mesInt = mes.toIntOrNull()




            if (
                diaInt != null && mesInt != null &&
                diaInt in 1..31 &&
                mesInt in 1..12 &&
                anio.length == 4
            ) {
                fechaFormato = anio.takeLast(2) +
                        mes.padStart(2, '0') +
                        dia.padStart(2, '0')
            }
        }
    }



    // Construcción RFC
    var rfcBase = "$letra1$vocalInterna$letra3$letra4"
    // Palabras prohibidas
    val palabrasProhibidas = listOf(
        "BUEI", "BUEY", "CACA", "CACO", "CAGA",
        "KAKA", "LOCA", "LOCO", "MAME", "MAMO",
        "PENE", "PITO", "CULO", "TETA"
    )



    if (rfcBase in palabrasProhibidas) {
        rfcBase = rfcBase.substring(0, 3) + "X"
    }
    return rfcBase + fechaFormato
}
