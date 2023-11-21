package com.example.eyecoffee.api

import retrofit2.Call
import retrofit2.http.GET

// Interface que define os endpoints da API
interface ApiService {

    // Método para obter a lista de produtos da API
    @GET("products")
    fun getProdutos(): Call<ApiResponse>
}
