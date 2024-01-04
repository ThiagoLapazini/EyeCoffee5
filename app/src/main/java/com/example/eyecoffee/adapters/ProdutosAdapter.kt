package com.example.eyecoffee.adapters

import Lancamento
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eyecoffee.databinding.ModelproductsBinding
import com.example.eyecoffee.model.ModelCarrinho
import com.example.eyecoffee.model.Produtos

class ProdutosAdapter(
    private val context: Context,
    private val sharedViewModel: SharedViewModel,
    private val onItemClickListener: (Produtos) -> Unit

) : RecyclerView.Adapter<ProdutosAdapter.FoodViewHolder>() {

    private var foodList: List<Produtos> = listOf()


    // Método chamado para criar um novo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // Inflando o layout do item de produto usando databinding
        val listItem =
            ModelproductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(listItem)
    }
    // Método chamado para obter o número total de itens no conjunto de dados
    override fun getItemCount() = foodList.size
    // Método chamado para vincular os dados a um ViewHolder específico
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        // Obtendo o objeto Produto da lista
        val produto = foodList[position]

        val quantidadeNoCarrinho = getQuantidadeNoCarrinho(produto)
        holder.qnt.text = quantidadeNoCarrinho.toString()
        // Vinculando dados ao ViewHolder
        holder.bind(produto)
    }
    private fun getQuantidadeNoCarrinho(produto: Produtos): Int {
        val carrinhoList = sharedViewModel.carrinhoList.value
        return carrinhoList?.firstOrNull { it.nomeProdutoCarrinho == produto.productTitle }?.quantidadeCarrinho
            ?: 0
    }

    fun setProductList(foodList: List<Produtos>) {
        this.foodList = foodList

        notifyDataSetChanged()
    }
    // Classe interna representando o ViewHolder para um item de produto
    inner class FoodViewHolder(private val binding: ModelproductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Elementos da interface do usuário no item de produto
        val nomeProduto = binding.nomeProduto
        val valorProduto = binding.precoProduto
        val imgFood = binding.imagemProduto
        val qnt = binding.qntItem

        // Método para vincular dados a um item de produto no ViewHolder
        fun bind(produto: Produtos) {
            // Configurando outros elementos da interface do usuário
            Glide.with(itemView)
                .load(produto.productImage)
                .into(imgFood)
            nomeProduto.text = produto.productTitle
            valorProduto.text = produto.productPrice
            qnt.text = produto.quantidadeNoCarrinho.toString()

            // Configurando o clique no item do RecyclerView
            itemView.setOnClickListener {
                onItemClickListener(produto)
            }

            // Configurando o clique longo no item do RecyclerView para mostrar o diálogo de lançamento
            itemView.setOnLongClickListener {
                sharedViewModel.setSelectedProdutoLan(produto)
                showLancamentoDialog(produto)
                true // Indica que o evento foi consumido
            }

        }

        // Método para mostrar o diálogo de lançamento
        private fun showLancamentoDialog(produto: Produtos) {
            // Obtendo o FragmentManager da atividade pai
            val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
            // Criando e mostrando o diálogo de lançamento
            val lancamentoDialog = Lancamento()
            lancamentoDialog.show(fragmentManager, "lancamento_dialog")
        }
    }
    fun updateQuantidadeNoCarrinho(produtos: List<Produtos>, carrinho: List<ModelCarrinho>) {
        for (produto in produtos) {
            for (carrinhoItem in carrinho) {
                if (produto.productTitle == carrinhoItem.nomeProdutoCarrinho) {
                    produto.quantidadeNoCarrinho = carrinhoItem.quantidadeCarrinho
                    break
                }
            }
        }
        notifyDataSetChanged()

        // Adicionar um log para verificar se está incrementando o qnt_text
        for (produto in produtos) {
            Log.d("QNT_TEXT", "Produto: ${produto.productTitle}, Quantidade: ${produto.quantidadeNoCarrinho}")
        }
    }
}