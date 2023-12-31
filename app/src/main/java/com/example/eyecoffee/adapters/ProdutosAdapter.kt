package com.example.eyecoffee.adapters

import Lancamento
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eyecoffee.databinding.ModelproductsBinding
import com.example.eyecoffee.model.Produtos

class ProdutosAdapter(
    private val context: Context,
    private val foodList: MutableList<Produtos>,
    private val onItemClickListener: (Produtos) -> Unit
) : RecyclerView.Adapter<ProdutosAdapter.FoodViewHolder>() {

    // Método chamado para criar um novo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        // Inflando o layout do item de produto usando databinding
        val listItem = ModelproductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(listItem)
    }

    // Método chamado para obter o número total de itens no conjunto de dados
    override fun getItemCount() = foodList.size

    // Método chamado para vincular os dados a um ViewHolder específico
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        // Obtendo o objeto Produto da lista
        val produto = foodList[position]
        // Vinculando dados ao ViewHolder
        holder.bind(produto)
    }

    // Classe interna representando o ViewHolder para um item de produto
    inner class FoodViewHolder(private val binding: ModelproductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Elementos da interface do usuário no item de produto
        val nomeProduto = binding.nomeProduto
        val valorProduto = binding.precoProduto
        val imgFood = binding.imagemProduto
        private val btnQntItem: Button = binding.qntItem

        // Método para vincular dados a um item de produto no ViewHolder
        fun bind(produto: Produtos) {
            // Configurando a visibilidade e a contagem do botão de quantidade do item
            if (produto.clickCount > 0) {
                btnQntItem.visibility = View.VISIBLE
                btnQntItem.text = produto.clickCount.toString()
            } else {
                btnQntItem.visibility = View.GONE
            }
            // Configurando o clique do botão para incrementar a contagem
            btnQntItem.setOnClickListener {
                produto.clickCount++
                btnQntItem.text = produto.clickCount.toString()
            }

            // Configurando outros elementos da interface do usuário
            Glide.with(itemView)
                .load(produto.productImage)
                .into(imgFood)
            nomeProduto.text = produto.productTitle
            valorProduto.text = produto.productPrice

            // Configurando o clique no item do RecyclerView
            itemView.setOnClickListener {
                onItemClickListener(produto)
            }

            // Configurando o clique longo no item do RecyclerView para mostrar o diálogo de lançamento
            itemView.setOnLongClickListener {
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
}