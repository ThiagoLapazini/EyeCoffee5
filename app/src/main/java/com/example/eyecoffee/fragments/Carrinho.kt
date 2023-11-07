package com.example.eyecoffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eyecoffee.adapters.CarrinhoAdapter
import com.example.eyecoffee.databinding.FragmentCarrinhoBinding
import com.example.eyecoffee.model.ModelCarrinho


class Carrinho : Fragment() {
    private lateinit var binding: FragmentCarrinhoBinding
    private lateinit var carrinhoAdapter: CarrinhoAdapter
    private val carrinhoList: MutableList<ModelCarrinho> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarrinhoBinding.inflate(inflater, container, false)
        val recyclerView = binding.recyclerViewCarrinho
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        carrinhoAdapter = CarrinhoAdapter(requireContext(), carrinhoList)
        recyclerView.adapter = carrinhoAdapter
        return binding.root
    }
}