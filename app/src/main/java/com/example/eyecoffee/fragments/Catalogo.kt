package com.example.eyecoffee.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eyecoffee.adapters.ProdutosAdapter
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.api.ApiClient
import com.example.eyecoffee.api.ApiResponse
import com.example.eyecoffee.databinding.FragmentCatalogoBinding
import com.example.eyecoffee.model.ModelCarrinho
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Catalogo : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentCatalogoBinding
    private lateinit var produtosAdapter: ProdutosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflando o layout usando o databinding
        binding = FragmentCatalogoBinding.inflate(inflater, container, false)
        // Inicializando o SharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        // Configurando o RecyclerView e o adaptador
        val recyclerView = binding.recyclerViewCatalogo
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        // Configurando o adaptador de produtos
        produtosAdapter =
            ProdutosAdapter(requireContext(), sharedViewModel, onItemClickListener = { produto ->
                // Ação quando um item do RecyclerView é clicado
                sharedViewModel.setBottomBarVisibility(true)
                val value =
                    produto.productPrice.replace("R$", "").replace(",", ".").trim().toDouble()
                sharedViewModel.addToTotalSelectedValue(value)
                val carrinhoItem = ModelCarrinho(
                    produto.productTitle, produto.productPrice, 1, produto.productImage, "teste"
                )
                sharedViewModel.addToCarrinhoList(carrinhoItem, 1)

            })
        recyclerView.adapter = produtosAdapter
        // Carregando dados iniciais
        getProdutos()
        return binding.root
    }
    private fun getProdutos() {
        val apiService = ApiClient.getApiService()
        val call = apiService.getProdutos()
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("API_CALL", "API call successful")
                    Log.d("API_CALL", "Dados recebidos: ${apiResponse?.data}")
                    apiResponse?.data?.let { produtos ->
                        produtosAdapter.setProductList(produtos)
                    }
                } else {
                    Log.d("fracassou", "fracassado")
                }
            }
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("API_CALL", "Falha na chamada à API: ${t.message}")
                t.printStackTrace()
            }
        })
    }
}
