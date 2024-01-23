package com.example.eyecoffee.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eyecoffee.R
import com.example.eyecoffee.databinding.ModelcarrinhoBinding
import com.example.eyecoffee.model.ModelCarrinho

class CarrinhoAdapter(
    private val context: Context,
    private val carrinhoList: MutableList<ModelCarrinho>,
    private val sharedViewModel: SharedViewModel,

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
    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("RestrictedApi")
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



        // Definindo o OnClickListener para o botão opcoes
        holder.opcoes.setOnClickListener {
            val popupMenu = PopupMenu(context, it)

            // Inflar o menu usando o estilo personalizado
            val inflater: MenuInflater = popupMenu.menuInflater
            inflater.inflate(R.menu.popupmenu_layout, popupMenu.menu)

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.opcao_editar -> {
                        sharedViewModel.exibirPopupOpcoesEditar(carrinhoItem)

                        true
                    }
                    R.id.opcao_excluir -> {

                        // Obter a posição do item a ser excluído
                        val itemPosicao = holder.adapterPosition
                        // Chamar o método para excluir o item
                        excluirItem(itemPosicao)
                        true
                    }
                    else -> false
                }
            }

            // Verificar a versão do Android antes de chamar setGroupDividerEnabled
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                popupMenu.menu.setGroupDividerEnabled(true)
            }

            // Exibir o PopupMenu ancorado no botão "opcoes"
            popupMenu.show()
        }
    }
        // Método para atualizar a lista de itens no adaptador
        fun submitList(list: List<ModelCarrinho>) {
            carrinhoList.clear()
            carrinhoList.addAll(list)
            notifyDataSetChanged()
        }


    fun excluirItem(posicao: Int) {
        val itemRemovido = carrinhoList.removeAt(posicao)
        notifyItemRemoved(posicao)

        // Chame a função removeItemFromCarrinho no SharedViewModel
        sharedViewModel.removeItemFromCarrinho(itemRemovido)

        // Atualizar a quantidade total e o valor total
        updateQuantidadeTotalEValorTotal(carrinhoList)
    }

    private fun updateQuantidadeTotalEValorTotal(carrinhoList: List<ModelCarrinho>) {
        var quantidadeTotal = 0
        var valorTotal = 0.0

        for (item in carrinhoList) {
            quantidadeTotal += item.quantidadeCarrinho
            valorTotal += item.quantidadeCarrinho * item.precoProdutoCarrinho.toDouble()
        }

        // Post the updated values using postValue
        sharedViewModel.updateItemCountAndTotalValue(quantidadeTotal, valorTotal)
    }
    // Classe interna representando o ViewHolder para um item de carrinho
    inner class CarrinhoViewHolder(binding: ModelcarrinhoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Elementos da interface do usuário no item de carrinho
        val nomeProduto = binding.nomeProdutoCarrinho
        val valorProduto = binding.precoProdutoCarrinho
        val quantidade = binding.quantidade
        val imgFood = binding.imagemProdutoCarrinho
        val opcoes: ImageView = binding.opcoes
    }
}

