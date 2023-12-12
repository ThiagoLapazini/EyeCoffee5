package com.example.eyecoffee.fragments

import Desconto
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.eyecoffee.R
import com.example.eyecoffee.adapters.SharedViewModel

class Pagamento : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private var totalValue: Double = 0.0
    private lateinit var totalValueTextView: TextView
    private var payType: String? = null

    private fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_pagamento, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        totalValueTextView = view.findViewById(R.id.total_value)
        sharedViewModel.totalSelectedValue.observe(viewLifecycleOwner) { totalValue ->
            // Update the UI with the total value
            totalValueTextView.text = String.format("R$ %.2f", totalValue)
        }
        totalValueTextView.text = String.format("R$ %.2f", totalValue)

        val btnDiscount = view.findViewById<Button>(R.id.btn_discount)

        btnDiscount.setOnClickListener {
            showDescontoDialog()
        }

        initButtons(view)

        sharedViewModel.discountValue.observe(viewLifecycleOwner) { discount ->
            // Handle the discount value as needed (e.g., display it)
            Log.d("Pagamento", "Received discount value: $discount")

            // Update the UI with the new total after applying the discount
            val novoTotal = totalValue - (totalValue * discount)
            totalValueTextView.text = String.format("R$ %.2f", novoTotal.coerceAtLeast(0.0))
        }


    }

    private fun showDescontoDialog() {
        // Obtendo o FragmentManager da atividade pai
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        // Criando e mostrando o diálogo de lançamento
        val descontoDialog = Desconto()
        descontoDialog.show(fragmentManager, "desconto_dialog")
    }

    private fun initButtons(view: View) {
        val buttons = listOf(
            view.findViewById<ImageButton>(R.id.adicionarDinheiroPagamento),
            view.findViewById(R.id.adicionarCreditoPagamento),
            view.findViewById(R.id.adicionarDebitoPagamento)
        )

        val removeButtons = listOf(
            view.findViewById<ImageButton>(R.id.removerDinheiroPagamento),
            view.findViewById(R.id.removerCreditoPagamento),
            view.findViewById(R.id.removerDebitoPagamento)
        )

        val layouts = listOf(
            view.findViewById<ConstraintLayout>(R.id.constraintLayout2),
            view.findViewById(R.id.constraintLayout4),
            view.findViewById(R.id.constraintLayout5)
        )

        val valueTextViews = listOf(
            view.findViewById<TextView>(R.id.dinheiroValorPagamento),
            view.findViewById(R.id.creditoValorPagamento),
            view.findViewById(R.id.debitoValorPagamento)
        )

        val midBars = listOf(
            view.findViewById<View>(R.id.mid_bar0),
            view.findViewById(R.id.mid_bar1),
            view.findViewById(R.id.mid_bar2)
        )

        for (i in buttons.indices) {
            val buttonAdd = buttons[i]
            val buttonRemove = removeButtons[i]
            val layout = layouts[i]
            val valueTextView = valueTextViews[i]
            val midBar = midBars[i]


            buttonAdd.setOnClickListener {
                if (payType == null) {
                    buttonAdd.visibility = View.GONE
                    buttonRemove.visibility = View.VISIBLE
                    layout.layoutParams.height = convertDpToPixel(133f, requireContext())
                    valueTextView.visibility = View.VISIBLE
                }
            }

            buttonRemove.setOnClickListener {
                buttonAdd.visibility = View.VISIBLE
                buttonRemove.visibility = View.GONE
                layout.layoutParams.height = convertDpToPixel(77f, requireContext())
                valueTextView.visibility = View.GONE
                midBar.visibility = View.GONE
                payType = null
            }


        }


    }
}
