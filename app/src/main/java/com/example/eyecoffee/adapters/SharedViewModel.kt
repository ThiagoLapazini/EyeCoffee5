package com.example.eyecoffee.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _bottomBarVisible = MutableLiveData<Boolean>()
    val bottomBarVisible: LiveData<Boolean>
        get() = _bottomBarVisible

    fun setBottomBarVisibility(visible: Boolean) {
        _bottomBarVisible.value = visible
    }
}