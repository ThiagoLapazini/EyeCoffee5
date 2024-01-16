package com.example.eyecoffee.model

import java.io.Serializable

data class Produtos(
    val productId: String,
    val productTitle: String,
    val productPrice: String,
    val productImage: String,
    val observacaoProduto: String? = null,
    var isNoCarrinho: Boolean = false,
    var quantidadeNoCarrinho: Int = 0
) : Serializable{

    fun isQuantidadeMaiorQueZero(): Boolean {
        return quantidadeNoCarrinho > 0
    }
    fun incrementarQuantidade() {
        quantidadeNoCarrinho++
    }
    @Override
    fun incrementarQuantidade(qnt: Int) {
        quantidadeNoCarrinho+= qnt
    }

    fun decrementarQuantidadeTeste(){
        quantidadeNoCarrinho--
    }
}



