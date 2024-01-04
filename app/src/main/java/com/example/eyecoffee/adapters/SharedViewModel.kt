package com.example.eyecoffee.adapters

import android.os.Handler
import android.os.Looper
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


    fun addToTotalSelectedValue(value: Double) {
        _totalSelectedValue.postValue((_totalSelectedValue.value ?: 0.0) + value)
        _itemCount.postValue((_itemCount.value ?: 0) + 1)

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

    fun addToCarrinhoList(produto: ModelCarrinho, quantidade: Int) {
        val listaCarrinho = _carrinhoList.value ?: mutableListOf()

        listaCarrinho.add(produto)

        // Configurar as propriedades no produto original
        listaCarrinho.forEach { carrinhoItem ->
            carrinhoItem.toProduto().apply {
                isNoCarrinho = true
                quantidadeNoCarrinho = carrinhoItem.quantidadeCarrinho
            }
        }

        // Notificar observadores sobre a mudança na lista do carrinho
        _carrinhoList.value = listaCarrinho
        notificarListaCarrinhoAtualizada()

        // Atualizar a quantidade total e o valor total
        updateQuantidadeTotalEValorTotal(listaCarrinho)

        // Notificar o adaptador sobre a alteração
        _quantidadeNoCarrinhoAtualizada.value = Unit
    }
    fun atualizarObservacaoProduto(produto: ModelCarrinho, novaObservacao: String) {
        // Encontrar o produto no carrinho e atualizar a observação
        //carrinhoList.find { it.nomeProdutoCarrinho == produto.nomeProdutoCarrinho }?.observacao = novaObservacao
    }


    private val _quantidadeNoCarrinhoAtualizada = MutableLiveData<Unit>()
    val quantidadeNoCarrinhoAtualizada: LiveData<Unit>
        get() = _quantidadeNoCarrinhoAtualizada
    fun notificarQuantidadeNoCarrinhoAtualizada() {
        _quantidadeNoCarrinhoAtualizada.value = Unit
    }
    private fun updateQuantidadeNoCarrinho(produtos: List<Produtos>, carrinho: List<ModelCarrinho>) {
        for (produto in produtos) {
            for (carrinhoItem in carrinho) {
                if (produto.productTitle == carrinhoItem.nomeProdutoCarrinho) {
                    produto.quantidadeNoCarrinho = carrinhoItem.quantidadeCarrinho
                    break
                }
            }
        }
        notificarQuantidadeNoCarrinhoAtualizada()
    }

    fun addToItemCount(quantity: Int) {
        _itemCount.value = (_itemCount.value ?: 0) + quantity
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
            productTitle = this.nomeProdutoCarrinho,
            productPrice = this.precoProdutoCarrinho,
            productImage = this.imagemProdutoCarrinho,
            observacaoProduto = this.observacao,
            quantidadeNoCarrinho = this.quantidadeCarrinho
        )
    }

    private fun updateQuantidadeCarrinho(produto: ModelCarrinho, novaQuantidade: Int) {
        // Atualizar a quantidadeCarrinho do produto
        produto.quantidadeCarrinho = novaQuantidade

        // Notificar observadores sobre a mudança
        _quantidadeCarrinhoAtualizada.value = Unit
    }

    // MutableLiveData que indica que a quantidade do carrinho foi atualizada
    private val _quantidadeCarrinhoAtualizada = MutableLiveData<Unit>()
    val quantidadeCarrinhoAtualizada: LiveData<Unit>
        get() = _quantidadeCarrinhoAtualizada

    // Método para notificar que a quantidade do carrinho foi atualizada
    fun notificarQuantidadeCarrinhoAtualizada() {
        _quantidadeCarrinhoAtualizada.value = Unit
    }

    private val yourLiveData = MutableLiveData<ModelCarrinho>()

    // Handler associated with the main Looper
    private val mainHandler = Handler(Looper.getMainLooper())

    // ...

    // Method to update LiveData from a background thread
    fun updateLiveDataFromBackground(newValue: LiveData<ModelCarrinho>) {
        // Post the value using mainHandler
        mainHandler.post {
            yourLiveData.value = newValue.value
        }
    }

    private fun updateQuantidadeTotalEValorTotal(carrinhoList: List<ModelCarrinho>) {
        var quantidadeTotal = 0
        var valorTotal = 0.0

        for (item in carrinhoList) {
            quantidadeTotal += item.quantidadeCarrinho
            valorTotal += item.quantidadeCarrinho * item.precoProdutoCarrinho.toDouble()
        }

        // Post the updated values using mainHandler
        mainHandler.post {
            _itemCount.value = quantidadeTotal
            _totalSelectedValue.value = valorTotal
        }
    }
}
