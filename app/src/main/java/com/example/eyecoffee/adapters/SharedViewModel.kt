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

    // MutableLiveData que controla o valor total selecionado
    private val _totalSelectedValue = MutableLiveData<Double>(0.0)
    val totalSelectedValue: LiveData<Double>
        get() = _totalSelectedValue

    // MutableLiveData que controla o número total de itens selecionados
    private val _itemCount = MutableLiveData<Int>(0)
    val itemCount: LiveData<Int>
        get() = _itemCount

    private val _selectedProduto = MutableLiveData<Produtos>()
    val selectedProduto: LiveData<Produtos>
        get() = _selectedProduto

    // Função para definir o produto selecionado
    fun setSelectedProduto(produto: Produtos?) {
        _selectedProduto.value = produto
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

    // Método para remover um valor do total selecionado e decrementar o número de itens
    fun removeFromTotalSelectedValue(value: Double) {
        _totalSelectedValue.value = (_totalSelectedValue.value ?: 0.0) - value
        _itemCount.value = (_itemCount.value ?: 0) - 1
    }

    // Método para definir a visibilidade da barra inferior
    fun setBottomBarVisibility(visible: Boolean) {
        _bottomBarVisible.value = visible
    }
}
