package com.example.eyecoffee.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.eyecoffee.R

class Pagamento : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pagamento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val btDesconto = view.findViewById<Button>(R.id.btDesconto)

        btDesconto.setOnClickListener {
            exibirDialogDesconto()
        }
    }

    private fun exibirDialogDesconto() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.fragment_desconto, null)


        builder.setView(dialogView)
        val alertDialog = builder.create()



        alertDialog.show()
    }


}
