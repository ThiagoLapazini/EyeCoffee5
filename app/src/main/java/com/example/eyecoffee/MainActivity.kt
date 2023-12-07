package com.example.eyecoffee

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.dao.AppDataBase
import com.example.eyecoffee.dao.ProdutoDAO
import com.example.eyecoffee.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    // Declarando uma instância do SharedViewModel
    private lateinit var sharedViewModel: SharedViewModel
    // Declarando uma instância do binding para acessar os elementos da interface do usuário
    private lateinit var binding: ActivityMainBinding
    // Variáveis para controlar a visibilidade do carrinho e pagamento
    private var isCarrinhoVisivel = false
    private var isPagamentoVisivel = false

    private lateinit var dataBase: AppDataBase
    private lateinit var produtoDAO: ProdutoDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflando o layout usando o databinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.dataBase = AppDataBase.getInstance(this)

        this.produtoDAO = this.dataBase.ProdutoDAO()

        val serviceBackgroundIntent = Intent(this, MyBackgroundService::class.java)
        startService(serviceBackgroundIntent)

        val serviceForegroundIntent = Intent(this, MyForegroundService::class.java)
        startService(serviceForegroundIntent)

        // Obtendo o NavController do NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // Inicializando o SharedViewModel
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        // Observando mudanças na visibilidade da barra inferior
        sharedViewModel.bottomBarVisible.observe(this) { visible ->
            binding.footer.visibility = if (visible) View.VISIBLE else View.GONE
        }

        // Observando mudanças no valor total selecionado
        sharedViewModel.totalSelectedValue.observe(this) { totalValue ->
            val valFormatado = String.format("R$ %.2f", totalValue)
            binding.totalBottom.text = valFormatado
            binding.totalBottom2.text = valFormatado
        }

        // Observando mudanças no número total de itens selecionados
        sharedViewModel.itemCount.observe(this) { itemCount ->
            val quantFormatado = String.format("%d Itens", itemCount)
            binding.qntItens.text = quantFormatado
        }

        val limpar = binding.imageLimpar
        limpar.setOnClickListener {
            sharedViewModel.limparCarrinho()
        }



        // Configurando o clique no ícone do carrinho
        val cartIcon = binding.cartBottom
        cartIcon.setOnClickListener {
            startService(Intent(this@MainActivity,MyService::class.java))
            // Navegando para o Fragment do carrinho
            navController.navigate(R.id.action_catalogo_to_carrinho)
            // Alternando a visibilidade do carrinho
            isCarrinhoVisivel = !isCarrinhoVisivel
            // Atualizando o cabeçalho da interface do usuário
            atualizarHeader()

        }


        // Configurando o clique no ícone de avanço para pagamento
        val avancarPagamento = binding.setavancarPagamento
        avancarPagamento.setOnClickListener {
            // Navegando para o Fragment do pagamento
            navController.navigate(R.id.action_carrinho_to_pagamento)
            // Alternando a visibilidade do pagamento
            isPagamentoVisivel = !isPagamentoVisivel
            // Atualizando o cabeçalho da interface do usuário
            atualizarHeaderPagamento()
        }
    }

    // Método para atualizar o cabeçalho quando o pagamento é visível ou não
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

    // Método para atualizar o cabeçalho quando o carrinho é visível ou não
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
