package com.example.eyecoffee.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // URL base da API
    private const val BASE_URL = "https://demo1308240.mockable.io/"

    // Configuração do Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Função para obter uma instância do serviço da API
    fun getApiService(): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}
