package com.example.decima

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.decima.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val botones = listOf(
            Pair(R.id.btnAND, "AND"),
            Pair(R.id.btnOR, "OR"),
            Pair(R.id.btnNOT, "NOT"),
            Pair(R.id.btnNAND, "NAND"),
            Pair(R.id.btnNOR, "NOR"),
            Pair(R.id.btnXOR, "XOR")
        )

        for ((id, compuerta) in botones) {

            findViewById<Button>(id).setOnClickListener {

                val intent = Intent(this, CompuertaLogica::class.java)
                intent.putExtra("Compuerta", compuerta)
                startActivity(intent)

            }

        }

    }

}