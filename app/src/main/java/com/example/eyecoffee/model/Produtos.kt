package com.example.eyecoffee.model

import java.io.Serializable

data class Produtos(
    val productTitle: String,
    val productPrice: String,
    val productImage: String,
    val observacaoProduto: String? = null,
    var quantidadeCatalogo: String? = null
) : Serializable



