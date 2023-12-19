import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.eyecoffee.R
import com.example.eyecoffee.adapters.SharedViewModel

class Desconto : DialogFragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private var currentDiscountPercentage = 10
    private var previousDiscountPercentage = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla o layout do fragmento de desconto
        val view = inflater.inflate(R.layout.fragment_desconto, container, false)

        // Inicializa o ViewModel compartilhado
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Obtém a referência para o TextView que exibe o valor do desconto
        val valorDescontoTextView: TextView = view.findViewById(R.id.valorDesconto)
        valorDescontoTextView.text = "$currentDiscountPercentage%"

        // IDs dos botões numéricos
        val numericButtons = arrayOf(
            R.id.numero0, R.id.numero1, R.id.numero2,
            R.id.numero3, R.id.numero4, R.id.numero5,
            R.id.numero6, R.id.numero7, R.id.numero8, R.id.numero9
        )

        // Configura os listeners de clique para os botões numéricos
        for (buttonId in numericButtons) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                handleNumericButtonClick(buttonId)
                valorDescontoTextView.text = "$currentDiscountPercentage%"
            }
        }

        // Configura o listener de clique para o botão "C"
        view.findViewById<Button>(R.id.removerQuantidade).setOnClickListener {
            handleClearButtonClick()
            valorDescontoTextView.text = "$currentDiscountPercentage%"
        }

        // Configura o listener de clique para o botão de apagar
        view.findViewById<ImageButton>(R.id.btApagar).setOnClickListener {
            handleDeleteButtonClick()
            valorDescontoTextView.text = "$currentDiscountPercentage%"
        }

        // Configura o listener de clique para o botão "OK"
        view.findViewById<Button>(R.id.btOk).setOnClickListener {
            // Calcula o novo valor total com o desconto
            val desconto = currentDiscountPercentage.toDouble() / 100.0
            val novoTotal = calcularNovoTotal(desconto)

            // Define o valor do desconto no ViewModel compartilhado
            sharedViewModel.setDiscountValue(desconto)

            // Fecha o diálogo
            dismiss()
        }

        return view
    }

    // Calcula o novo valor total com o desconto
    private fun calcularNovoTotal(desconto: Double): Double {
        // Obtém o valor total do ViewModel compartilhado
        val totalAtual = sharedViewModel.totalSelectedValue.value ?: 0.0
        Log.d("testando desconto", "teste desconto valor atual $totalAtual $desconto")
        // Calcula o novo valor total com o desconto
        return totalAtual * (1 - desconto)
    }

    // Lida com o clique nos botões numéricos
    private fun handleNumericButtonClick(buttonId: Int) {
        // Encontra o botão pelo ID
        val button = view?.findViewById<Button>(buttonId)

        // Obtém o texto do botão
        val buttonText = button?.text?.toString()

        // Salva o estado atual como estado anterior
        previousDiscountPercentage = currentDiscountPercentage

        // Se o currentDiscountPercentage for o valor padrão, substitua-o pelo novo valor
        if (currentDiscountPercentage == 10) {
            currentDiscountPercentage = buttonText?.toIntOrNull() ?: 0
        } else {
            // Verifica se o percentual de desconto atual já possui dois dígitos
            if (currentDiscountPercentage in 10..99) {
                // Se tiver dois dígitos, não faça nada (ou manipule conforme necessário)
                return
            }

            // Concatena o valor numérico ao percentual de desconto atual, limitando a dois dígitos
            currentDiscountPercentage =
                (currentDiscountPercentage * 10 + (buttonText?.toIntOrNull() ?: 0))
                    .coerceIn(0, 99)
        }
    }

    // Lida com o clique no botão "C" (clear)
    private fun handleClearButtonClick() {
        // Reseta o percentual de desconto para o valor padrão (10%)
        currentDiscountPercentage = 10
    }

    // Lida com o clique no botão de apagar
    private fun handleDeleteButtonClick() {
        // Extrai o último dígito do percentual de desconto atual
        val lastDigit = currentDiscountPercentage % 10

        // Remove o último dígito do percentual de desconto atual
        currentDiscountPercentage /= 10

        // Garante que o percentual nunca seja menor que 0
        currentDiscountPercentage = currentDiscountPercentage.coerceIn(0, 99)

        // Se houver apenas um dígito, define o percentual de desconto atual de volta ao padrão
        if (currentDiscountPercentage == 0) {
            currentDiscountPercentage = 10
        }
    }
}
