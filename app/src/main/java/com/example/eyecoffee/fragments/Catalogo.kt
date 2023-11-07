package com.example.eyecoffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.eyecoffee.R
import com.example.eyecoffee.adapters.ProdutosAdapter
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.databinding.FragmentCatalogoBinding
import com.example.eyecoffee.model.Produtos

class Catalogo : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var binding: FragmentCatalogoBinding
    private lateinit var produtosAdapter: ProdutosAdapter
    private val produtolist: MutableList<Produtos> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCatalogoBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val recyclerView = binding.recyclerViewCatalogo
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        produtosAdapter = ProdutosAdapter(requireContext(), produtolist) { produto ->
            sharedViewModel.setBottomBarVisibility(true)
        }
        recyclerView.adapter = produtosAdapter
        getFood()

        return binding.root
    }

    private fun getFood() {
        val food1 = Produtos("Cafe Coado", "R$ 4,00", R.drawable.ovo)
        produtolist.add(food1)
        val food2 = Produtos("Capuccino", "R$ 8,00", R.drawable.ovo)
        produtolist.add(food2)
        val food3 = Produtos("Croissant", "R$ 9,00", R.drawable.ovo)
        produtolist.add(food3)
        val food4 = Produtos("Espresso", "R$ 6,00", R.drawable.ovo)
        produtolist.add(food4)
        val food5 = Produtos("Pão de Queijo", "R$ 5,00", R.drawable.ovo)
        produtolist.add(food5)
        val food6 = Produtos("Cafe Coado", "R$ 4,00", R.drawable.ovo)
        produtolist.add(food6)
        val food7 = Produtos("Capuccino", "R$ 8,00", R.drawable.ovo)
        produtolist.add(food7)
        val food8 = Produtos("Croissant", "R$ 9,00", R.drawable.ovo)
        produtolist.add(food8)
        val food9 = Produtos("Espresso", "R$ 6,00", R.drawable.ovo)
        produtolist.add(food9)
        val food10 = Produtos("Pão de Queijo", "R$ 5,00", R.drawable.ovo)
        produtolist.add(food10)
    }

}
