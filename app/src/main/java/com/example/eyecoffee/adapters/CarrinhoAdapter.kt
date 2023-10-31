package com.example.eyecoffee.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eyecoffee.databinding.ModelcarrinhoBinding
import com.example.eyecoffee.model.ModelCarrinho

class CarrinhoAdapter(private val context: Context, private val carrinhoList: MutableList<ModelCarrinho>) :
    RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrinhoViewHolder {
        val listItem = ModelcarrinhoBinding.inflate(LayoutInflater.from(context), parent, false)
        return CarrinhoViewHolder(listItem)
    }

    override fun getItemCount() = carrinhoList.size

    override fun onBindViewHolder(holder: CarrinhoViewHolder, position: Int) {
        val carrinhoItem = carrinhoList[position]

        holder.nomeProduto.text = carrinhoItem.nomeProdutoCarrinho
        holder.valorProduto.text = carrinhoItem.precoProdutoCarrinho
        holder.quantidade.text = carrinhoItem.quantidadeCarrinho.toString()
        holder.imgFood.setImageResource(carrinhoItem.imagemProdutoCarrinho)
    }

    inner class CarrinhoViewHolder(binding: ModelcarrinhoBinding) : RecyclerView.ViewHolder(binding.root) {
        val nomeProduto = binding.nomeProdutoCarrinho
        val valorProduto = binding.precoProdutoCarrinho
        val quantidade = binding.quantidade
        val imgFood = binding.imagemProdutoCarrinho
    }
}
