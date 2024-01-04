package com.example.eyecoffee.fragments

import Desconto  // Importa a classe Desconto do pacote correspondente
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

    private var originalTotalValue: Double = 0.0

    private fun convertDpToPixel(dp: Float, context: Context): Int {
        return (dp * context.resources.displayMetrics.density).toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Infla o layout do fragmento de pagamento
        val view = inflater.inflate(R.layout.fragment_pagamento, container, false)
        // Inicializa a view e configura os elementos visuais
        initView(view)
        return view
    }

    private fun initView(view: View) {
        // Inicializa o ViewModel compartilhado
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Salva o valor original do carrinho
        originalTotalValue = sharedViewModel.totalSelectedValue.value ?: 0.0
        // Obtém a referência para o TextView que exibe o valor total
        totalValueTextView = view.findViewById(R.id.total_value)
        // Observa as mudanças no LiveData totalSelectedValue para atualizar a UI
        sharedViewModel.totalSelectedValue.observe(viewLifecycleOwner) { totalValue ->
            // Atualiza a UI com o valor total
            totalValueTextView.text = String.format("R$ %.2f", totalValue)
            Log.d("valor total", "valor $totalValue ")

            val restanteDescontoTextView = view.findViewById<TextView>(R.id.restantedesconto)


            // Observa as mudanças no LiveData discountValue para lidar com descontos
            sharedViewModel.discountValue.observe(viewLifecycleOwner) { discount ->
                Log.d("Pagamento", "Received discount value: $discount")

                val restanteDesconto = totalValue * discount
                restanteDescontoTextView.text = String.format("R$ %.2f", restanteDesconto)

                // Define a visibilidade do TextView com base no valor do desconto
                if (discount > 0) {
                    restanteDescontoTextView.visibility = View.VISIBLE
                } else {
                    restanteDescontoTextView.visibility = View.GONE
                }
                // Atualiza a UI com o novo valor total após aplicar o desconto
                val novoTotal = totalValue - (totalValue * discount)
                totalValueTextView.text = String.format("R$ %.2f", novoTotal.coerceAtLeast(0.0))
            }

        }
        // Define o texto inicial com base no valor inicial de totalSelectedValue
        totalValueTextView.text =
            String.format("R$ %.2f", sharedViewModel.totalSelectedValue.value ?: 0.0)
        // Configura o botão de desconto
        val btnDiscount = view.findViewById<Button>(R.id.btn_discount)
        btnDiscount.setOnClickListener {
            // Mostra o diálogo de desconto
            showDescontoDialog()
        }
        // Configura os botões de forma de pagamento
        initButtons(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ... (o resto do seu código)

        // Observa as mudanças no LiveData discountValue para lidar com descontos
        sharedViewModel.discountValue.observe(viewLifecycleOwner) { discount ->
            Log.d("Pagamento", "Received discount value: $discount")
            sharedViewModel.totalSelectedValue.observe(viewLifecycleOwner) {
                Log.d("total atualizado?", "total $totalValue")

            }

            // Define a visibilidade do botão de desconto com base no valor do desconto
            val btnDesconto = view.findViewById<Button>(R.id.btn_discount)
            val btnRemover = view.findViewById<Button>(R.id.removerDesconto)

            if (discount > 0) {
                btnDesconto.visibility = View.GONE
                btnRemover.visibility = View.VISIBLE
            } else {
                btnDesconto.visibility = View.VISIBLE
                btnRemover.visibility = View.GONE
            }

        }
    }

    private fun showDescontoDialog() {
        // Obtém o FragmentManager da atividade pai
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        // Cria e mostra o diálogo de desconto
        val descontoDialog = Desconto()
        descontoDialog.show(fragmentManager, "desconto_dialog")
    }

    private fun initButtons(view: View) {
        // Lista de botões e elementos relacionados
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

        // Encontrar referência ao botão removerDesconto
        val btnRemoverDesconto = view.findViewById<Button>(R.id.removerDesconto)

        // Adicionar OnClickListener ao botão removerDesconto
        btnRemoverDesconto.setOnClickListener {
            // Lógica para resetar o valor e desconto
            resetarValores()
        }


        for (i in buttons.indices) {
            val buttonAdd = buttons[i]
            val buttonRemove = removeButtons[i]
            val layout = layouts[i]
            val valueTextView = valueTextViews[i]
            val midBar = midBars[i]

            buttonAdd.setOnClickListener {
                if (payType == null) {
                    // Esconde o botão de adição, mostra o de remoção e ajusta o layout
                    buttonAdd.visibility = View.GONE
                    buttonRemove.visibility = View.VISIBLE
                    layout.layoutParams.height = convertDpToPixel(133f, requireContext())

                    sharedViewModel.totalSelectedValue.observe(viewLifecycleOwner) { totalValue ->
                        // Observa as mudanças no LiveData discountValue para lidar com descontos
                        sharedViewModel.discountValue.observe(viewLifecycleOwner) { discount ->
                            // Aplica o desconto e atualiza o valor total
                            val novoTotal = totalValue - (totalValue * discount)
                            // Atualiza a UI com o novo valor total após aplicar o desconto
                            totalValueTextView.text =
                                String.format("R$ %.2f", novoTotal.coerceAtLeast(0.0))

                            // Atualiza a variável totalValue
                            this.totalValue = novoTotal

                            // Atualiza o TextView de pagamento específico
                            valueTextView.text =
                                String.format("R$ %.2f", novoTotal.coerceAtLeast(0.0))
                            valueTextView.visibility = View.VISIBLE
                        }
                    }
                }

                buttonRemove.setOnClickListener {
                    // Mostra o botão de adição, esconde o de remoção, ajusta o layout e oculta o TextView
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

    private fun resetarValores() {

        totalValue = originalTotalValue

        // Defina o desconto para 0
        sharedViewModel.setDiscountValue(0.0)

        // Atualize a UI conforme necessário
        totalValueTextView.text = String.format("R$ %.2f", totalValue)
        // Outras atualizações de UI que você possa precisar
    }
}


