package com.example.eyecoffee.api

import com.example.eyecoffee.model.Produtos

// Data class que representa a resposta da API
data class ApiResponse(
    // Status da resposta (por exemplo, "true" ou "false")
    val status: String,

    // Mensagem associada à resposta
    val message: String,

    // Lista de objetos Produtos, pode ser nula dependendo da situação
    val data: List<Produtos>?
)
