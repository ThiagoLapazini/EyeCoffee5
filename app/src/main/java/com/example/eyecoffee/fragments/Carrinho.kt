package com.example.eyecoffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eyecoffee.adapters.CarrinhoAdapter
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.databinding.FragmentCarrinhoBinding
import com.example.eyecoffee.model.ModelCarrinho


class Carrinho : Fragment() {
    private lateinit var binding: FragmentCarrinhoBinding
    private lateinit var carrinhoAdapter: CarrinhoAdapter
    private val carrinhoList: MutableList<ModelCarrinho> = mutableListOf()
    private lateinit var sharedViewModel: SharedViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarrinhoBinding.inflate(inflater, container, false)
        val recyclerView = binding.recyclerViewCarrinho
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        carrinhoAdapter = CarrinhoAdapter(requireContext(), carrinhoList)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        recyclerView.adapter = carrinhoAdapter
        sharedViewModel.setBottomBarVisibility(false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.carrinhoList.observe(viewLifecycleOwner) { carrinhoItens ->
            carrinhoAdapter.submitList(carrinhoItens)
        }

    }
}



