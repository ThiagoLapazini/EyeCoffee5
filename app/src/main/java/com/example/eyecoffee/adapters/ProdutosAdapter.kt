package com.example.eyecoffee.adapters
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecoffee.databinding.ModelproductsBinding
import com.example.eyecoffee.model.Produtos

class ProdutosAdapter(private val context: Context, private val foodList: MutableList<Produtos>) :
    RecyclerView.Adapter<ProdutosAdapter.FoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val listItem = ModelproductsBinding.inflate(LayoutInflater.from(context), parent, false)
        return FoodViewHolder(listItem)
    }
    override fun getItemCount() = foodList.size
    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val produto = foodList[position]

        holder.imgFood.setImageResource(produto.imagemProduto!!)
        holder.nomeProduto.text = produto.nomeProduto
        holder.valorProduto.text = produto.precoProduto




    }
    inner class FoodViewHolder(binding: ModelproductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nomeProduto = binding.nomeProduto
        val valorProduto = binding.precoProduto
        val imgFood = binding.imagemProduto
    }
}

