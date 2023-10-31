package com.example.eyecoffee.model

import java.io.Serializable

data class Produtos(

    val nomeProduto: String,
    val precoProduto: String,
    val imagemProduto: Int? = null,
    val observacaoProduto: String? = null
) : Serializable



