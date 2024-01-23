package com.example.eyecoffee.adapters

import Lancamento
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eyecoffee.databinding.ModelproductsBinding
import com.example.eyecoffee.model.Produtos

class ProdutosAdapter(
    private val context: Context,
    private val sharedViewModel: SharedViewModel,
    private val onItemClickListener: (Produtos) -> Unit


) : RecyclerView.Adapter<ProdutosAdapter.FoodViewHolder>() {

    private var foodList: MutableList<Produtos> = mutableListOf()

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
        // Configurar a visibilidade do qnt_item
        holder.bind(produto)
    }
    // Add a method to retrieve the list of products
    fun getProductList(): List<Produtos> {
        return foodList
    }


    fun setProductList(newFoodList: List<Produtos>) {
        for (newProduct in newFoodList) {
            val existingProduct = foodList.find { it.productId == newProduct.productId }
            if (existingProduct != null) {
                // Update existing product
                existingProduct.quantidadeNoCarrinho = newProduct.quantidadeNoCarrinho
            } else {
                // Add new product
                foodList.add(newProduct)
            }
        }
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



            if (produto.quantidadeNoCarrinho > 0){
                qnt.visibility = View.VISIBLE
            } else qnt.visibility = View.GONE

            // Configuring the click listener for the item
            itemView.setOnClickListener {
                onItemClickListener(produto)


                produto.incrementarQuantidade()

            }

            // Configuring the long click listener for the item
            itemView.setOnLongClickListener {
                sharedViewModel.setSelectedProdutoLan(produto)
                qnt.visibility = View.VISIBLE
                showLancamentoDialog(produto)
                true // Indicate that the event was consumed
            }
        }

        // Adicione um método para atualizar a quantidade do produto no carrinho



        // Método para mostrar o diálogo de lançamento
        private fun showLancamentoDialog(produto: Produtos) {
            // Obtendo o FragmentManager da atividade pai
            val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
            // Criando e mostrando o diálogo de lançamento
            val lancamentoDialog = Lancamento()
            lancamentoDialog.show(fragmentManager, "lancamento_dialog")
        }

    }
    fun updateProductQuantity(productId: String, newQuantity: Int) {
        val index = foodList.indexOfFirst { it.productId == productId }
        if (index != -1) {
            foodList[index].quantidadeNoCarrinho += newQuantity
            notifyItemChanged(index)
        }
    }
}