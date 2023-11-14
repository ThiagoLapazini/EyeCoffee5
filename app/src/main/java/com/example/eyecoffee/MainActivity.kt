package com.example.eyecoffee

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: ActivityMainBinding
    private var isCarrinhoVisivel = false
    private var isPagamentoVisivel = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        sharedViewModel.bottomBarVisible.observe(this) { visible ->
            binding.footer.visibility = if (visible) View.VISIBLE else View.GONE
        }
        sharedViewModel.totalSelectedValue.observe(this) { totalValue ->
            val valFormatado = String.format("R$ %.2f", totalValue)
            binding.totalBottom.text = valFormatado
        }

        sharedViewModel.itemCount.observe(this) { itemCount ->
            val quantFormatado = String.format("%d Itens", itemCount)
            binding.qntItens.text = quantFormatado
        }
        val cartIcon = binding.cartBottom// Referencie o ícone do carrinho
        cartIcon.setOnClickListener {
            // Navegue para o Fragment do carrinho
            navController.navigate(R.id.action_catalogo_to_carrinho)
            isCarrinhoVisivel = !isCarrinhoVisivel
            atualizarHeader()

        }
        val avancarPagamento = binding.setavancarPagamento// Referencie o ícone do avanço para pagamento
        avancarPagamento.setOnClickListener {
            // Navegue para o Fragment do pagamento
            navController.navigate(R.id.action_carrinho_to_pagamento)
            isPagamentoVisivel = !isPagamentoVisivel
            atualizarHeaderPagamento()
        }
    }

    private fun atualizarHeaderPagamento() {
        if (isPagamentoVisivel) {
            binding.carrinhoheader.visibility = View.GONE
            binding.headerpagamento.visibility = View.VISIBLE
            binding.segundofooter.visibility = View.GONE
        } else {
            binding.headerpagamento.visibility = View.GONE
            binding.carrinhoheader.visibility = View.VISIBLE
            binding.segundofooter.visibility = View.VISIBLE
        }
    }

    private fun atualizarHeader() {
        if (isCarrinhoVisivel) {
            binding.header.visibility = View.GONE
            binding.carrinhoheader.visibility = View.VISIBLE
            binding.footer.visibility = View.GONE
            binding.segundofooter.visibility = View.VISIBLE
        } else {
            binding.header.visibility = View.VISIBLE
            binding.carrinhoheader.visibility = View.GONE
            binding.footer.visibility = View.VISIBLE
            binding.segundofooter.visibility = View.GONE
        }
    }
}
