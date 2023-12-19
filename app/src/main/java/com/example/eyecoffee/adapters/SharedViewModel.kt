package com.example.eyecoffee.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eyecoffee.model.ModelCarrinho
import com.example.eyecoffee.model.Produtos

class SharedViewModel : ViewModel() {

    // MutableLiveData que controla a visibilidade da barra inferior
    private val _bottomBarVisible = MutableLiveData<Boolean>()
    val bottomBarVisible: LiveData<Boolean>
        get() = _bottomBarVisible

    // MutableLiveData que indica se o carrinho está limpo
    private val _carrinhoLimpo = MutableLiveData<Boolean>(false)
    val carrinhoLimpo: LiveData<Boolean>
        get() = _carrinhoLimpo

    // MutableLiveData que armazena o valor de desconto
    private val _discountValue = MutableLiveData<Double>(0.0)
    val discountValue: LiveData<Double>
        get() = _discountValue

    // Método para definir o valor de desconto
    fun setDiscountValue(discount: Double) {
        _discountValue.value = discount
    }

    // MutableLiveData que controla o valor total selecionado
    private val _totalSelectedValue = MutableLiveData<Double>(0.0)
    val totalSelectedValue: LiveData<Double>
        get() = _totalSelectedValue

    // MutableLiveData que controla o número total de itens selecionados
    private val _itemCount = MutableLiveData<Int>(0)
    val itemCount: LiveData<Int>
        get() = _itemCount

    // MutableLiveData que armazena o produto selecionado
    private val _selectedProduto = MutableLiveData<Produtos>()
    val selectedProduto: LiveData<Produtos>
        get() = _selectedProduto

    // MutableLiveData que armazena o produto selecionado (lançamento)
    private val _selectedProdutoLan = MutableLiveData<Produtos>()
    val selectedProdutoLan: LiveData<Produtos>
        get() = _selectedProdutoLan

    // Método para definir o produto selecionado (lançamento)
    fun setSelectedProdutoLan(produto: Produtos) {
        _selectedProdutoLan.value = produto
    }

    // MutableLiveData que controla a lista de itens no carrinho
    private val _carrinhoList = MutableLiveData<MutableList<ModelCarrinho>>(mutableListOf())
    val carrinhoList: LiveData<MutableList<ModelCarrinho>> get() = _carrinhoList

    // Método para adicionar um item à lista do carrinho
    fun addToCarrinhoList(item: ModelCarrinho) {
        val carrinhoItens = _carrinhoList.value ?: mutableListOf()
        carrinhoItens.add(item)
        _carrinhoList.value = carrinhoItens

    }

    // Método para adicionar um valor ao total selecionado e incrementar o número de itens
    fun addToTotalSelectedValue(value: Double) {
        _totalSelectedValue.value = (_totalSelectedValue.value ?: 0.0) + value
        _itemCount.value = (_itemCount.value ?: 0) + 1
    }

    // Método para definir a visibilidade da barra inferior
    fun setBottomBarVisibility(visible: Boolean) {
        _bottomBarVisible.value = visible
    }

    // Método para limpar o carrinho
    fun limparCarrinho() {
        _carrinhoList.value?.clear()
        _totalSelectedValue.value = 0.0
        _itemCount.value = 0
        _carrinhoLimpo.value = true
    }

    // Método para sinalizar que a limpeza do carrinho foi tratada
    fun onCarrinhoLimpoHandled() {
        _carrinhoLimpo.value = false
    }

    // Método para adicionar um produto ao carrinho com uma quantidade específica
    fun adicionarProdutoAoCarrinho(produto: Produtos, quantidade: Int) {
        val precoProduto =
            produto.productPrice.replace("R$", "").replace(",", ".").trim().toDouble()
        repeat(quantidade) {
            // Criar um item de carrinho com os detalhes do produto
            val carrinhoItem = ModelCarrinho(
                produto.productTitle, produto.productPrice, 1, produto.productImage, "teste"
            )
            // Adicionar o item ao carrinho
            addToCarrinhoList(carrinhoItem)
            // Atualizar o total selecionado
            addToTotalSelectedValue(precoProduto)
            // Atualizar a quantidadeCatalogo no catálogo
            produto.quantidadeCatalogo = (produto.quantidadeCatalogo?.toInt() ?: 0 + 1).toString()
        }
    }
    // MutableLiveData que controla a exibição do popup de opções de edição
    private val _showPopupOpcoesEditar = MutableLiveData<ModelCarrinho>()
    val showPopupOpcoesEditar: LiveData<ModelCarrinho>
        get() = _showPopupOpcoesEditar

    // Método para exibir o popup de opções de edição
    fun showPopupOpcoesEditar(modelCarrinho: ModelCarrinho) {
        _showPopupOpcoesEditar.value = modelCarrinho
    }


    fun removerQuantidadeDoCarrinho(produto: Produtos?, diferencaQuantidade: Int) {
        if (produto != null) {
            // Verificar se o produto está no carrinho
            val carrinhoItens = _carrinhoList.value ?: mutableListOf()
            val itemNoCarrinho = carrinhoItens.find { it.nomeProdutoCarrinho == produto.productTitle }

            // Se o produto estiver no carrinho, atualizar a quantidade e o total selecionado
            if (itemNoCarrinho != null) {
                if (itemNoCarrinho.quantidadeCarrinho > diferencaQuantidade) {
                    itemNoCarrinho.quantidadeCarrinho -= diferencaQuantidade
                    _totalSelectedValue.value = (_totalSelectedValue.value ?: 0.0) - (produto.productPrice.toDouble() * diferencaQuantidade)
                } else {
                    // Se a quantidade a ser removida for maior ou igual à quantidade no carrinho,
                    // remover completamente o item do carrinho
                    carrinhoItens.remove(itemNoCarrinho)
                    _totalSelectedValue.value = (_totalSelectedValue.value ?: 0.0) - (produto.productPrice.toDouble() * itemNoCarrinho.quantidadeCarrinho)
                    _itemCount.value = (_itemCount.value ?: 0) - 1
                }

                // Atualizar a lista do carrinho
                _carrinhoList.value = carrinhoItens
            }
        }
    }
    private val _quantidadeAtual = MutableLiveData<Int>()
    val quantidadeAtual: LiveData<Int>
        get() = _quantidadeAtual

    // Método para atualizar a quantidade atual
    fun atualizarQuantidadeAtual(novaQuantidade: Int) {
        _quantidadeAtual.value = novaQuantidade
    }


}
