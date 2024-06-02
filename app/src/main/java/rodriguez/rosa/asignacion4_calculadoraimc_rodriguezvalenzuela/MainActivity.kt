package rodriguez.rosa.asignacion4_calculadoraimc_rodriguezvalenzuela

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupElements()

    }

    data class IMC(val imc: Float, val range: String)

    private fun getIMC(altura: Float, peso: Float): IMC {

        val imc = peso / (altura * altura)

        return IMC(imc, getRange(imc))
    }

    private fun getRange(range: Float): String {
        return if (range >= 40.0) {
            "Obesidad grado 3"
        } else if (range >= 35.0 && range < 40) {
            "Obesidad grado 2"
        } else if (range >= 30.0 && range < 35) {
            "Obesidad grado 1"
        } else if (range >= 25 && range < 30) {
            "Sobrepeso"
        } else if (range >= 18.5 && range < 25) {
            "Normal"
        } else {
            "Bajo peso"
        }
    }

    private fun setupElements() {
        val textoPeso: EditText = findViewById(R.id.weight)
        val textoAltura: EditText = findViewById(R.id.height)
        val botonCalcular: Button = findViewById(R.id.calcular)
        val textoIMC: TextView = findViewById(R.id.imc)
        val textoRange: TextView = findViewById(R.id.range)

        botonCalcular.setOnClickListener {
            val nf = NumberFormat.getInstance(Locale.US)

            val peso: Float = nf.parse( textoPeso.text.toString() )?.toFloat() ?: 0.0f
            val altura: Float = nf.parse( textoAltura.text.toString() )?.toFloat() ?: 1.0f

            val imc: IMC = getIMC(altura, peso)


            textoIMC.text = imc.imc.toString()
            textoRange.text = imc.range

            val colorNaranja = arrayOf("Obesidad grado 1", "Sobrepeso", "Bajo peso")
            val colorRojo = arrayOf("Obesidad grado 2", "Obesidad grado 3")
            val colorVerde = arrayOf("Normal")

            if (colorVerde.contains(imc.range)) {
                textoRange.setBackgroundResource(R.color.colorGreenish)
            } else if(colorRojo.contains(imc.range)) {
                textoRange.setBackgroundResource(R.color.colorRed)
            } else if(colorNaranja.contains(imc.range)) {
                textoRange.setBackgroundResource(R.color.colorYellow)
            }


        }


    }

}