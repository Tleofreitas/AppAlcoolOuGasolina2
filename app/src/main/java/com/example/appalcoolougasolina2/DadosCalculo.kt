package com.example.appalcoolougasolina2

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DadosCalculo(
    val valorAlcool: String,
    val valorGasolina: String
) : Parcelable
