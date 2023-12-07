package com.example.eyecoffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eyecoffee.R
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.model.ModelCarrinho
import com.google.android.material.imageview.ShapeableImageView

class Editando : DialogFragment() {

    private lateinit var imagemProduto: ShapeableImageView
    private lateinit var nomeProduto: TextView
    private lateinit var precoProduto: TextView
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var adicionar: Button
    private lateinit var voltar: Button
    private lateinit var editTextObservacao: EditText

    // Propriedade para armazenar o produto a ser editado
    var produtoAEditar: ModelCarrinho? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de edição
        val view = inflater.inflate(R.layout.fragment_editando, container, false)

        // Inicializar os elementos do layout
        // Inicializar o SharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // ...

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar os elementos do layout
        imagemProduto = view.findViewById(R.id.shapeableImageView)
        nomeProduto = view.findViewById(R.id.nomeProduto)
        precoProduto = view.findViewById(R.id.precoProduto)
        editTextObservacao = view.findViewById(R.id.editText)

        // Verificar se há um produto a ser editado
        produtoAEditar?.let { produto ->
            // Atualizar os elementos da interface com as informações do produto
            nomeProduto.text = produto.nomeProdutoCarrinho
            precoProduto.text = produto.precoProdutoCarrinho
            editTextObservacao.setText(produto.observacao)

            // Carregar imagem usando Glide (se houver uma URL válida)
            produto.imagemProdutoCarrinho?.let {
                Glide.with(requireContext())
                    .load(it)
                    .into(imagemProduto)
            }
        }

        // ...
    }
}
