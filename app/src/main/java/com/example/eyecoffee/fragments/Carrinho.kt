package com.example.eyecoffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eyecoffee.adapters.CarrinhoAdapter
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.databinding.FragmentCarrinhoBinding
import com.example.eyecoffee.model.ModelCarrinho


class Carrinho : Fragment() {

    // Declarando uma instância do binding para acessar os elementos da interface do usuário
    private lateinit var binding: FragmentCarrinhoBinding
    // Declarando uma instância do adaptador de carrinho
    private lateinit var carrinhoAdapter: CarrinhoAdapter
    // Lista de itens no carrinho
    private val carrinhoList: MutableList<ModelCarrinho> = mutableListOf()
    // Declarando uma instância do SharedViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflando o layout usando o databinding
        binding = FragmentCarrinhoBinding.inflate(inflater, container, false)
        // Configurando o RecyclerView e o adaptador de carrinho
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        val recyclerView = binding.recyclerViewCarrinho
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        carrinhoAdapter = CarrinhoAdapter(requireContext(), carrinhoList, sharedViewModel)
        // Inicializando o SharedViewModel
        recyclerView.adapter = carrinhoAdapter
        // Ocultando a barra inferior ao exibir o fragmento do carrinho
        sharedViewModel.setBottomBarVisibility(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.carrinhoList.observe(viewLifecycleOwner) { carrinhoItens ->
            // Atualizando o conjunto de dados do adaptador com a nova lista
            carrinhoAdapter.submitList(carrinhoItens)

            sharedViewModel.carrinhoLimpo.observe(viewLifecycleOwner) { isLimpo ->
                if (isLimpo) {
                    // Atualize a view conforme necessário após a limpeza do carrinho
                    sharedViewModel.totalSelectedValue
                    // ...
                    carrinhoAdapter.submitList(emptyList())
                    // Após a atualização, sinalize que a visualização foi tratada
                    sharedViewModel.onCarrinhoLimpoHandled()
                }
            }
        }
        sharedViewModel.popupOpcoesEditar.observe(viewLifecycleOwner) { modelCarrinho ->
            modelCarrinho?.let {
                // Exibir o PopupOpcoesEditar aqui e passar os dados do modelCarrinho
                exibirPopupOpcoesEditar(modelCarrinho, view)
            }
        }
        sharedViewModel.showPopupOpcoesEditar.observe(viewLifecycleOwner) { modelCarrinho ->
            modelCarrinho?.let {
                // Exibir o PopupOpcoesEditar aqui e passar os dados do modelCarrinho
                exibirPopupOpcoesEditar(modelCarrinho, view)
            }
        }
    }
    // Métodos para atualizar a exibição
    private fun exibirPopupOpcoesEditar(modelCarrinho: ModelCarrinho, view: View) {
        val fragmentManager = (requireActivity() as AppCompatActivity).supportFragmentManager
        val editandoDialog = Editando()
        // Passando o produto a ser editado e a callback para o diálogo de edição
        editandoDialog.produtoAEditar = modelCarrinho
        editandoDialog.show(fragmentManager, "edicao_dialog")
    }
}






