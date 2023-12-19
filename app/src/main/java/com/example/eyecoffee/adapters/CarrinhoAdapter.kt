package com.example.eyecoffee.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eyecoffee.databinding.ModelcarrinhoBinding
import com.example.eyecoffee.model.ModelCarrinho

class CarrinhoAdapter(
    private val context: Context,
    private val carrinhoList: MutableList<ModelCarrinho>,
    private val sharedViewModel: SharedViewModel

) : RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder>() {
    // Método chamado para criar um novo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrinhoViewHolder {
        // Inflando o layout do item de carrinho usando databinding
        val listItem = ModelcarrinhoBinding.inflate(LayoutInflater.from(context), parent, false)
        return CarrinhoViewHolder(listItem)
    }
    // Método chamado para obter o número total de itens no conjunto de dados
    override fun getItemCount() = carrinhoList.size

    // Método chamado para vincular os dados a um ViewHolder específico
    override fun onBindViewHolder(holder: CarrinhoViewHolder, position: Int) {
        // Obtendo o objeto ModelCarrinho da lista
        val carrinhoItem = carrinhoList[position]

        // Configurando os elementos da interface do usuário no ViewHolder
        holder.nomeProduto.text = carrinhoItem.nomeProdutoCarrinho
        holder.valorProduto.text = carrinhoItem.precoProdutoCarrinho
        holder.quantidade.text = carrinhoItem.quantidadeCarrinho.toString()

        // Carregar imagem usando Glide (se houver uma URL válida)
        carrinhoItem.imagemProdutoCarrinho?.let {
            Glide.with(context)
                .load(it)
                .into(holder.imgFood)
        }
    }
    // Método para atualizar a lista de itens no adaptador
    fun submitList(list: List<ModelCarrinho>) {
        carrinhoList.clear()
        carrinhoList.addAll(list)
        notifyDataSetChanged()
    }

    // Classe interna representando o ViewHolder para um item de carrinho
    inner class CarrinhoViewHolder(binding: ModelcarrinhoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Elementos da interface do usuário no item de carrinho
        val nomeProduto = binding.nomeProdutoCarrinho
        val valorProduto = binding.precoProdutoCarrinho
        val quantidade = binding.quantidade
        val imgFood = binding.imagemProdutoCarrinho
        val opcoes: ImageButton = binding.opcoes

        init {
            // Definindo o OnClickListener para o botão opcoes no construtor
            opcoes.setOnClickListener {
                // Chamar o método do SharedViewModel para exibir o PopupOpcoesEditar
                sharedViewModel.showPopupOpcoesEditar(carrinhoList[adapterPosition])
            }
        }
    }
}
