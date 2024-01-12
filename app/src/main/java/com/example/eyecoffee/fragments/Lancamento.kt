
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eyecoffee.R
import com.example.eyecoffee.adapters.SharedViewModel
import com.example.eyecoffee.model.ModelCarrinho
import com.google.android.material.imageview.ShapeableImageView

class Lancamento : DialogFragment() {

    private lateinit var imagemProduto: ShapeableImageView
    private lateinit var nomeProduto: TextView
    private lateinit var precoProduto: TextView
    private lateinit var totalText: TextView
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var qntCounter: TextView
    private lateinit var adicionarQuantidadeBtn: Button
    private lateinit var removerQuantidadeBtn: Button

    @SuppressLint("LongLogTag")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de lançamento
        val view = inflater.inflate(R.layout.fragment_lancamento, container, false)

        // Inicializar os elementos do layout
        imagemProduto = view.findViewById(R.id.lanimagem)
        nomeProduto = view.findViewById(R.id.lannomeProduto)
        precoProduto = view.findViewById(R.id.lanprecoProduto)
        totalText = view.findViewById(R.id.total_text)
        qntCounter = view.findViewById(R.id.qnt_counter)
        adicionarQuantidadeBtn = view.findViewById(R.id.adicionarQuantidade)
        removerQuantidadeBtn = view.findViewById(R.id.removerQuantidade)

        // Inicializar o SharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // Observar mudanças no produto selecionado
        sharedViewModel.selectedProdutoLan.observe(viewLifecycleOwner) { produto ->
            // Atualizar a interface do usuário com as informações do produto
            Glide.with(requireContext())
                .load(produto.productImage)
                .into(imagemProduto)
            nomeProduto.text = produto.productTitle
            precoProduto.text = produto.productPrice
            totalText.text = produto.productPrice

            view.findViewById<Button>(R.id.btOk).setOnClickListener {
                val quantidadeSelecionada = qntCounter.text.toString().toInt()
                val produto = sharedViewModel.selectedProdutoLan.value

                produto?.let {
                    val carrinhoItem = ModelCarrinho(
                        it.productId,
                        it.productTitle,
                        it.productPrice,
                        quantidadeSelecionada,
                        it.productImage,
                        "teste"
                    )
                    sharedViewModel.addToCarrinhoList(carrinhoItem, quantidadeSelecionada)
                }
                dismiss()
            }
        }
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Adicionar lógica para Adicionar Quantidade
        adicionarQuantidadeBtn.setOnClickListener {
            val quantidadeAtual = qntCounter.text.toString().toInt()
            val novaQuantidade = quantidadeAtual + 1
            qntCounter.text = novaQuantidade.toString()

            // Atualizar o valor total
            updateTotalAndQuantity(novaQuantidade)
        }

        // Adicionar lógica para Remover Quantidade
        removerQuantidadeBtn.setOnClickListener {
            val quantidadeAtual = qntCounter.text.toString().toInt()
            val novaQuantidade = maxOf(quantidadeAtual - 1, 0)
            qntCounter.text = novaQuantidade.toString()

            // Atualizar o valor total
            updateTotalAndQuantity(novaQuantidade)
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun updateTotalAndQuantity(newQuantity: Int) {
        val produto = sharedViewModel.selectedProdutoLan.value
        produto?.let {
            val valorTotal = newQuantity * it.productPrice.toDouble()
            totalText.text = getString(R.string.format_total, valorTotal)



            // Atualizar o SharedViewModel com a nova quantidade e a quantidade total de itens no carrinho
            sharedViewModel.addToTotalSelectedValue(valorTotal)

        }
    }
}