
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.eyecoffee.R
import com.example.eyecoffee.model.Produtos

class Lancamento : DialogFragment() {

    private var produto: Produtos? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_lancamento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Verifica se o produto foi passado para o DialogFragment
        if (produto != null) {
            // Configura os campos com as informações do produto
            // Exemplo: Supondo que você tenha TextViews chamados tvNome, tvValor e ImageView chamado imgProduto
            view.findViewById<TextView>(R.id.lannomeProduto).text = produto!!.nomeProduto
            view.findViewById<TextView>(R.id.lanprecoProduto).text = produto!!.precoProduto
            view.findViewById<ImageView>(R.id.lanimagem).setImageResource(produto!!.imagemProduto!!)

            // Configura os botões para aumentar ou diminuir a quantidade
            view.findViewById<Button>(R.id.adicionarQuantidade).setOnClickListener {
                // Lógica para aumentar a quantidade do produto
                // Exemplo: Ajuste conforme necessário
            }

            view.findViewById<Button>(R.id.removerQuantidade).setOnClickListener {
                // Lógica para diminuir a quantidade do produto
                // Exemplo: Ajuste conforme necessário
            }
        }
    }

    // Método para definir o produto a ser exibido no DialogFragment
    fun setProduto(produto: Produtos) {
        this.produto = produto
    }
}


