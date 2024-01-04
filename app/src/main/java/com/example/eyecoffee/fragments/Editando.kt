package com.example.eyecoffee.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
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
    var produtoAEditar: ModelCarrinho? = null



    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        fragmentManager?.beginTransaction()?.remove(this)?.commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de edição
        val view = inflater.inflate(R.layout.fragment_editando, container, false)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        // Inicializar os elementos do layout

        return view
    }
}

