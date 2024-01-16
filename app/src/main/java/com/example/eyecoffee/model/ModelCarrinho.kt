package com.example.eyecoffee.model

data class ModelCarrinho(
    val idProdutoCarrinho: String,
    val nomeProdutoCarrinho: String,
    val precoProdutoCarrinho: String,
    var quantidadeCarrinho: Int,
    val imagemProdutoCarrinho: String,
    var observacao: String
){
    fun toProduto(): Produtos {
        return Produtos(
            productId = this.idProdutoCarrinho,
            productTitle = this.nomeProdutoCarrinho,
            productPrice = this.precoProdutoCarrinho,
            productImage = this.imagemProdutoCarrinho,
            observacaoProduto = this.observacao,
            quantidadeNoCarrinho = this.quantidadeCarrinho
        )
    }
}