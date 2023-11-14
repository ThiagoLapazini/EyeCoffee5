package com.example.eyecoffee.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eyecoffee.model.ModelCarrinho

class SharedViewModel : ViewModel() {
    private val _bottomBarVisible = MutableLiveData<Boolean>()
    val bottomBarVisible: LiveData<Boolean>
        get() = _bottomBarVisible

    private val _totalSelectedValue = MutableLiveData<Double>(0.0)
    val totalSelectedValue: LiveData<Double>
        get() = _totalSelectedValue

    private val _itemCount = MutableLiveData<Int>(0)
    val itemCount: LiveData<Int>
        get() = _itemCount

    private val _carrinhoList = MutableLiveData<MutableList<ModelCarrinho>>(mutableListOf())
    val carrinhoList: LiveData<MutableList<ModelCarrinho>> get() = _carrinhoList

    fun addToCarrinhoList(item: ModelCarrinho) {
        val carrinhoItens = _carrinhoList.value ?: mutableListOf()
        carrinhoItens.add(item)
        _carrinhoList.value = carrinhoItens
    }


    fun addToTotalSelectedValue(value: Double) {
        _totalSelectedValue.value = (_totalSelectedValue.value ?: 0.0) + value
        _itemCount.value = (_itemCount.value ?: 0) + 1
    }

    fun removeFromTotalSelectedValue(value: Double) {
        _totalSelectedValue.value = (_totalSelectedValue.value ?: 0.0) - value
        _itemCount.value = (_itemCount.value ?: 0) - 1
    }

    fun setBottomBarVisibility(visible: Boolean) {
        _bottomBarVisible.value = visible
    }
}