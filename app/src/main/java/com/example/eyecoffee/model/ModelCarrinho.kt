package com.example.eyecoffee.model

data class ModelCarrinho(
    val idProdutoCarrinho: String,
    val nomeProdutoCarrinho: String,
    val precoProdutoCarrinho: String,
    var quantidadeCarrinho: Int,
    val imagemProdutoCarrinho: String,
    var observacao: String
)