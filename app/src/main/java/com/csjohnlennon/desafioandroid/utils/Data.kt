package com.csjohnlennon.desafioandroid.utils

import java.text.SimpleDateFormat

fun formataDataBR(data: String): String {
    return try {
        val dataAtual = SimpleDateFormat("yyyy-MM-dd").parse(data)
        SimpleDateFormat("dd/MM/yyyy").format(dataAtual)
    } catch (e: Exception) {
        ""
    }
}
