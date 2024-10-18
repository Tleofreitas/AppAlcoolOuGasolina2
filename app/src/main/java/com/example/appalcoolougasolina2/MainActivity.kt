package com.example.appalcoolougasolina2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private lateinit var textInputAlcool : TextInputLayout
    private lateinit var editAlcool : TextInputEditText

    private lateinit var textInputGasolina : TextInputLayout
    private lateinit var editGasolina : TextInputEditText

    private lateinit var buttonCalcular : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inicializar variáveis
        inicializarComponentesInterface()

        buttonCalcular.setOnClickListener {
            val intent = Intent(this, DetalhesActivity::class.java)

            val precoAlcool = editAlcool.text.toString()
            val precoGasolina = editGasolina.text.toString()

            val resultadoValidacao = validarCampos(precoAlcool, precoGasolina)

            if(resultadoValidacao){
                val calculos = DadosCalculo(precoAlcool, precoGasolina)
                intent.putExtra("dados",calculos)

                startActivity(intent)
            }
        }
    }

    private fun inicializarComponentesInterface() {
        textInputAlcool = findViewById(R.id.edit_alcool)
        editAlcool = findViewById(R.id.valor_alcool)

        textInputGasolina = findViewById(R.id.edit_gasolina)
        editGasolina = findViewById(R.id.valor_gasolina)

        buttonCalcular = findViewById(R.id.btn_calcular)
    }

    private fun validarCampos(pAlcool: String, pGasolina: String): Boolean {

        // Inicialmente sem nenhuma mensagem de erro
        textInputAlcool.error = null
        textInputGasolina.error = null

        if(pAlcool.isEmpty() || pGasolina.isEmpty()){
            // Se tiver vazio, da mensagem de erro
            if(pAlcool.isEmpty()){
                textInputAlcool.error = "Digite o preço do Álcool"
                return false
            } else if(pGasolina.isEmpty()) {
                textInputGasolina.error = "Digite o preço da Gasolina"
                return false
            }
        } else if(pAlcool.toDouble()<=0 || pGasolina.toDouble()<=0){
            // Se menor ou igual a 0, da mensagem de erro
            if(pAlcool.toDouble()<=0){
                textInputAlcool.error = "Digite o preço válido (maior que zero)"
                return false
            } else if(pGasolina.isEmpty()) {
                textInputGasolina.error = "Digite o preço válido (maior que zero)"
                return false
            }
        }

        // Se tiver OK, sem nenhuma mensagem de erro
        return true
    }
}