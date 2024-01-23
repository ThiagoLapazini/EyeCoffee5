package com.example.eyecoffee.adapters

import android.annotation.SuppressLint
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

    private val _popupOpcoesEditar = MutableLiveData<ModelCarrinho>()
    val popupOpcoesEditar: LiveData<ModelCarrinho> get() = _popupOpcoesEditar

    fun exibirPopupOpcoesEditar(modelCarrinho: ModelCarrinho) {
        _popupOpcoesEditar.value = modelCarrinho
    }
    // Método para atualizar a quantidadeCatalogo

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


    fun addToTotalSelectedValue(value: Double, quantidade: Int) {
        _totalSelectedValue.postValue((_totalSelectedValue.value ?: 0.0) + value)
        _itemCount.postValue((_itemCount.value ?: 0) + quantidade)

        // Notify observers about the change in the total value
        notificarValorTotalAtualizado()
    }

    // MutableLiveData que indica que o valor total foi atualizado
    private val _valorTotalAtualizado = MutableLiveData<Unit>()
    val valorTotalAtualizado: LiveData<Unit>
        get() = _valorTotalAtualizado

    // Método para notificar que o valor total foi atualizado
    fun notificarValorTotalAtualizado() {
        _valorTotalAtualizado.value = Unit
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
    private val idMapping: MutableMap<String, String> = mutableMapOf()

    fun updateItemCountAndTotalValue(quantidadeTotal: Int, valorTotal: Double) {
        _itemCount.postValue(quantidadeTotal)
        _totalSelectedValue.postValue(valorTotal)
    }


    // MutableLiveData que controla a exibição do popup de opções de edição
    private val _showPopupOpcoesEditar = MutableLiveData<ModelCarrinho>()
    val showPopupOpcoesEditar: LiveData<ModelCarrinho>
        get() = _showPopupOpcoesEditar

    // Método para exibir o popup de opções de edição
    fun showPopupOpcoesEditar(modelCarrinho: ModelCarrinho) {
        _showPopupOpcoesEditar.value = modelCarrinho
    }
    private val quantidadesProduto: MutableMap<String, MutableLiveData<Int>> = mutableMapOf()

    fun getQuantidadeProduto(productId: String): LiveData<Int> {
        if (!quantidadesProduto.containsKey(productId)) {
            quantidadesProduto[productId] = MutableLiveData(0)
        }
        return quantidadesProduto[productId]!!
    }

    fun setQuantidadeProduto(productId: String, quantidade: Int) {
        quantidadesProduto[productId]?.postValue(quantidade)
    }
    // Método para remover um item do carrinho permanentemente
    fun removeItemFromCarrinho(modelCarrinho: ModelCarrinho) {
        // Remover o item da lista do carrinho
        _carrinhoList.value?.remove(modelCarrinho)
        val listaCarrinho = _carrinhoList.value ?: mutableListOf()
        listaCarrinho.forEach { carrinhoItem ->
            carrinhoItem.toProduto().apply {
                quantidadeNoCarrinho--
            }
        }
        // Notificar observadores sobre a mudança na lista do carrinho
        notificarListaCarrinhoAtualizada()

        // Atualizar a quantidade total e o valor total após a remoção do item
        updateQuantidadeTotalEValorTotal(_carrinhoList.value ?: emptyList())
    }
    @SuppressLint("SuspiciousIndentation")
    fun addToCarrinhoListWithQuantity(produto: ModelCarrinho, quantidade: Int) {
        val listaCarrinho = _carrinhoList.value ?: mutableListOf()


                listaCarrinho.add(produto)

        addToTotalSelectedValue(quantidade * produto.precoProdutoCarrinho.toDouble(), quantidade)


        // Notify observers about the change in the cart list
        _carrinhoList.value = listaCarrinho
        notificarListaCarrinhoAtualizada()

        // Notify the adapter about the change in the product quantity
        notificarQuantidadeProdutoAtualizada()

        // Update the total quantity and total value
        updateQuantidadeTotalEValorTotal(listaCarrinho)
    }
    fun addToCarrinhoList(produto: ModelCarrinho, quantidade: Int) {
        val listaCarrinho = _carrinhoList.value ?: mutableListOf()


        listaCarrinho.add(produto)

        // Notificar observadores sobre a mudança na lista do carrinho
        _carrinhoList.value = listaCarrinho
        notificarListaCarrinhoAtualizada()

        // Notificar o adaptador sobre a alteração na quantidade do produto
        notificarQuantidadeProdutoAtualizada()

        // Atualizar a quantidade total e o valor total
        updateQuantidadeTotalEValorTotal(listaCarrinho)

    }
    fun atualizarObservacaoProduto(modelCarrinho: ModelCarrinho, novaObservacao: String) {
        // Atualizar a observação do produto
        modelCarrinho.observacao = novaObservacao

        // Notificar observadores sobre a mudança na observação
        notificarObservacaoProdutoAtualizada()
    }
    private val _observacaoProdutoAtualizada = MutableLiveData<Unit>()
    val observacaoProdutoAtualizada: LiveData<Unit>
        get() = _observacaoProdutoAtualizada

    fun notificarObservacaoProdutoAtualizada() {
        _observacaoProdutoAtualizada.value = Unit
    }


    private val _quantidadeProdutoAtualizada = MutableLiveData<Unit>()
    val quantidadeProdutoAtualizada: LiveData<Unit>
        get() = _quantidadeProdutoAtualizada

    // Método para notificar que a quantidade do produto foi atualizada
    fun notificarQuantidadeProdutoAtualizada() {
        _quantidadeProdutoAtualizada.postValue(Unit)
    }

    // Nova propriedade para armazenar a quantidade total de produtos selecionados
    private val _quantidadeTotalSelecionada = MutableLiveData<Int>()
    val quantidadeTotalSelecionada: LiveData<Int> get() = _quantidadeTotalSelecionada

    fun addToQuantidadeTotalSelecionada(quantidade: Int) {
        val totalAtual = _quantidadeTotalSelecionada.value ?: 0
        _quantidadeTotalSelecionada.value = totalAtual + quantidade
    }


    // MutableLiveData que indica que a lista de carrinho foi atualizada
    private val _listaCarrinhoAtualizada = MutableLiveData<Unit>()
    val listaCarrinhoAtualizada: LiveData<Unit>
        get() = _listaCarrinhoAtualizada

    // Método para notificar que a lista de carrinho foi atualizada
    fun notificarListaCarrinhoAtualizada() {
        _listaCarrinhoAtualizada.value = Unit
    }

    private fun ModelCarrinho.toProduto(): Produtos {
        return Produtos(
            productId = this.idProdutoCarrinho,
            productTitle = this.nomeProdutoCarrinho,
            productPrice = this.precoProdutoCarrinho,
            productImage = this.imagemProdutoCarrinho,
            observacaoProduto = this.observacao,
            quantidadeNoCarrinho = this.quantidadeCarrinho
        )
    }
    fun getCarrinhoList(): List<ModelCarrinho> {
        return _carrinhoList.value ?: emptyList()
    }

    private fun updateQuantidadeTotalEValorTotal(carrinhoList: List<ModelCarrinho>) {
        var quantidadeTotal = 0
        var valorTotal = 0.0

        for (item in carrinhoList) {
            quantidadeTotal += item.quantidadeCarrinho
            valorTotal += item.quantidadeCarrinho * item.precoProdutoCarrinho.toDouble()
        }
    }
}
