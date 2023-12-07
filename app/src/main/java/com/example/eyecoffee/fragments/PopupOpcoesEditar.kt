package com.example.eyecoffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.eyecoffee.R
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.model.ModelCarrinho

class PopupOpcoesEditar : DialogFragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var editarProduto: Button
    private lateinit var excluirProduto: Button

    // Propriedade para armazenar o produto a ser editado
    private var produtoAEditar: ModelCarrinho? = null

    // CarrinhoList para acesso direto neste fragmento (ajuste conforme necessário)
    private var carrinhoList: MutableList<ModelCarrinho> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de opções de edição
        val view = inflater.inflate(R.layout.opcoescarrinho, container, false)

        editarProduto = view.findViewById(R.id.editarproduto)
        excluirProduto = view.findViewById(R.id.excluirproduto)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editarProduto.setOnClickListener {
            sharedViewModel.showPopupOpcoesEditar.observe(viewLifecycleOwner) { modelCarrinho ->
                modelCarrinho?.let {
                    // Atribuir o produto a ser editado à propriedade produtoAEditar
                    produtoAEditar = modelCarrinho
                    // Exibir o PopupOpcoesEditar quando houver uma mudança
                    exibirPopupOpcoesEditar(modelCarrinho)
                }
            }
            // Ou fechar o diálogo
            dismiss()
        }
    }



        private fun exibirPopupOpcoesEditar(modelCarrinho: ModelCarrinho) {
            val fragmentManager = (requireActivity() as AppCompatActivity).supportFragmentManager
            val editandoDialog = Editando()

            // Passando o produto a ser editado e a callback para o diálogo de edição
            editandoDialog.produtoAEditar = modelCarrinho

            editandoDialog.show(fragmentManager, "edicao_dialog")
        }

    }
