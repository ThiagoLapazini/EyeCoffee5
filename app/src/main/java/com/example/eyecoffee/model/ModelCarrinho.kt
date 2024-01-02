package com.example.eyecoffee.model

data class ModelCarrinho(
    val nomeProdutoCarrinho: String,
    val precoProdutoCarrinho: String,
    var quantidadeCarrinho: Int,
    val imagemProdutoCarrinho: String,
    val observacao: String
)