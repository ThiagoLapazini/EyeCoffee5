package com.example.eyecoffee.adapters

import Lancamento
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecoffee.databinding.ModelproductsBinding
import com.example.eyecoffee.model.Produtos

class ProdutosAdapter(private val context: Context, private val foodList: MutableList<Produtos>, private val onItemClickListener: (Produtos) -> Unit) :
    RecyclerView.Adapter<ProdutosAdapter.FoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val listItem = ModelproductsBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodViewHolder(listItem)
    }

    override fun getItemCount() = foodList.size
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val produto = foodList[position]
        holder.bind(produto)
    }

    inner class FoodViewHolder(binding: ModelproductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nomeProduto = binding.nomeProduto
        val valorProduto = binding.precoProduto
        val imgFood = binding.imagemProduto

        fun bind(produto: Produtos) {
            imgFood.setImageResource(produto.imagemProduto!!)
            nomeProduto.text = produto.nomeProduto
            valorProduto.text = produto.precoProduto

            itemView.setOnClickListener {
                onItemClickListener(produto)
            }
            itemView.setOnClickListener {
                onItemClickListener(produto)
            }

            itemView.setOnLongClickListener {
                // Lidar com o clique longo aqui, por exemplo, inflar o DialogFragment de lançamento
                showLancamentoDialog(produto)
                true // Indica que o evento foi consumido
            }
        }
        private fun showLancamentoDialog(produto: Produtos) {
            val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
            val lancamentoDialog = Lancamento()
            lancamentoDialog.setProduto(produto)
            lancamentoDialog.show(fragmentManager, "lancamento_dialog")
        }
    }
}

