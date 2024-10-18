package com.example.appalcoolougasolina2

import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalhesActivity : AppCompatActivity() {
    lateinit var precoAlcool : TextView
    lateinit var precoGasolina : TextView
    lateinit var melhorPreco : TextView
    lateinit var buttonVoltar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalhes)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonVoltar = findViewById(R.id.btn_voltar)
        precoAlcool = findViewById(R.id.preco_alcool)
        precoGasolina = findViewById(R.id.preco_gasolina)
        melhorPreco = findViewById(R.id.resultado)

        // Pegar OBJETO passado Kotlin Parcelize
        val bundleoBJ = intent.extras
        if(bundleoBJ != null) {
            // SDK mais antigos
            val dados = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                bundleoBJ.getParcelable("dados", DadosCalculo::class.java)
                // SDK atuais
            } else {
                bundleoBJ.getParcelable("dados")
            }

            // COM OBJ (Com Erro)
            precoAlcool.text = "Preço Gasolina: ${dados?.valorGasolina}"
            precoGasolina.text = "Preço Álcool: ${dados?.valorAlcool}"

            // melhorPreco
            calcularMelhorPreco(dados?.valorAlcool, dados?.valorGasolina)
        }

        buttonVoltar.setOnClickListener {
            finish()
        }
    }

    // COM OBJ (Com Erro)
    private fun calcularMelhorPreco(valorAlcool: String?, valorGadolina: String?) {
        val precoAlcool   = valorAlcool?.toDouble()
        val precoGasolina = valorGadolina?.toDouble()

        var resultado = 0.0
        if(precoAlcool!=null && precoGasolina!=null){
            resultado = (precoAlcool / precoGasolina)
        }

        if(resultado!=0.0){
            // Se valorAlcool / ValorGasolina >= 0,7 , é melhor usar Gasolina
            if(resultado >= 0.7){
                melhorPreco.text = "Melhor utilizar Gasilina"
            } else {
                melhorPreco.text = "Melhor utilizar Álcool"
            }
        } else {
            melhorPreco.text = "Dados Inválidos!"
        }
    }
}