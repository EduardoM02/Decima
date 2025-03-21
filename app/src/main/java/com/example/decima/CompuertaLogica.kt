package com.example.decima

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.decima.databinding.ActivityCompuertaLogicaBinding

class CompuertaLogica : AppCompatActivity() {

    private lateinit var binding: ActivityCompuertaLogicaBinding
    private var estadoA = false
    private var estadoB = false
    private var compuerta = "AND"


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityCompuertaLogicaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compuerta = intent.getStringExtra("Compuerta") ?: "AND"
        mostrarImagen(compuerta)

        val tabla = obtenerTablaVerdad(compuerta)
        agregarFilas(tabla, compuerta)


        binding.cbA?.setOnCheckedChangeListener { _, isChecked ->

            estadoA = isChecked
            actualizarLED()

        }

        binding.cbB?.setOnCheckedChangeListener { _, isChecked ->

            estadoB = isChecked
            actualizarLED()

        }

    }



    private fun mostrarImagen(compuerta: String) {

        val imageResId = when (compuerta) {
            "AND" -> R.drawable.and
            "OR" -> R.drawable.or
            "NOT" -> R.drawable.not
            "NAND" -> R.drawable.nand
            "NOR" -> R.drawable.nor
            "XOR" -> R.drawable.xor
            else -> R.drawable.and
        }

        binding.imgDiagrama.setImageResource(imageResId)

    }



    private fun agregarFilas(tabla: List<List<Int>>, tipoCompuerta: String) {

        binding.tablaVerdad.removeAllViews()


        val columnas = if (tipoCompuerta == "NOT") listOf("A", "F","LED") else listOf("A", "B", "F", "LED")


        val encabezado = TableRow(this).apply {
            setBackgroundColor(Color.WHITE)
        }

        columnas.forEach { columna ->
            val textView = crearCelda(columna, true).apply {

                setBackgroundColor(Color.WHITE)
                setTextColor(Color.BLACK)

            }

            encabezado.addView(textView)

        }

        binding.tablaVerdad.addView(encabezado)


        tabla.forEach { fila ->
            val row = TableRow(this).apply {

                setBackgroundColor(Color.DKGRAY)

            }

            fila.forEachIndexed { index, valor ->
                val textView = crearCelda(valor.toString(), false).apply {

                    setTextColor(Color.WHITE)

                }

                row.addView(textView)

            }


            val ledView = LED(this, fila.last() == 1)
            row.addView(ledView)

            binding.tablaVerdad.addView(row)

        }

    }



    private fun crearCelda(texto: String, esEncabezado: Boolean): TextView {

        return TextView(this).apply {

            text = texto
            textSize = if (esEncabezado) 22f else 18f

            setPadding(16, 16, 16, 16)
            gravity = Gravity.CENTER

            if (esEncabezado) setTypeface(null, Typeface.BOLD)

        }

    }



    class LED(context: Context, esEncendido: Boolean) : View(context) {

        private val paint = Paint().apply {

            color = if (esEncendido) Color.GREEN else Color.RED
            isAntiAlias = true

        }


        override fun onDraw(canvas: Canvas) {

            super.onDraw(canvas)
            val radio = width.coerceAtMost(height) / 2.5f
            canvas.drawCircle(width / 2f, height / 2f, radio, paint)

        }


        override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

            val size = 100
            setMeasuredDimension(size, size)

        }

    }



    private fun actualizarLED() {

        val resultado = when (compuerta) {

            "AND" -> estadoA && estadoB
            "OR" -> estadoA || estadoB
            "XOR" -> estadoA xor estadoB
            "NAND" -> !(estadoA && estadoB)
            "NOR" -> !(estadoA || estadoB)
            "NOT" -> !estadoA
            else -> false

        }

        // Cambiar imagen del LED
        if (resultado) {

            binding.imageViewLED?.setImageResource(R.drawable.led_verde)

        } else {

            binding.imageViewLED?.setImageResource(R.drawable.led_rojo)

        }

    }

    
    private fun obtenerTablaVerdad(tipo: String): List<List<Int>> {

        return when (tipo) {
            "AND" -> listOf(listOf(0, 0, 0), listOf(0, 1, 0), listOf(1, 0, 0), listOf(1, 1, 1))
            "OR" -> listOf(listOf(0, 0, 0), listOf(0, 1, 1), listOf(1, 0, 1), listOf(1, 1, 1))
            "NOT" -> listOf(listOf(0, 1), listOf(1, 0))
            "NAND" -> listOf(listOf(0, 0, 1), listOf(0, 1, 1), listOf(1, 0, 1), listOf(1, 1, 0))
            "NOR" -> listOf(listOf(0, 0, 1), listOf(0, 1, 0), listOf(1, 0, 0), listOf(1, 1, 0))
            "XOR" -> listOf(listOf(0, 0, 0), listOf(0, 1, 1), listOf(1, 0, 1), listOf(1, 1, 0))
            else -> listOf()
        }

    }

}
