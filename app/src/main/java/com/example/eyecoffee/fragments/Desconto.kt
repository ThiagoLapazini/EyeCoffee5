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

    // Variable to store the previous discount percentage
    private var previousDiscountPercentage = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar o layout do fragmento de edição
        val view = inflater.inflate(R.layout.fragment_desconto, container, false)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        val valorDescontoTextView: TextView = view.findViewById(R.id.valorDesconto)
        valorDescontoTextView.text = "$currentDiscountPercentage%"

        val numericButtons = arrayOf(
            R.id.numero0, R.id.numero1, R.id.numero2,
            R.id.numero3, R.id.numero4, R.id.numero5,
            R.id.numero6, R.id.numero7, R.id.numero8, R.id.numero9
        )

        for (buttonId in numericButtons) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                handleNumericButtonClick(buttonId)
                valorDescontoTextView.text = "$currentDiscountPercentage%"
            }
        }

        // Set click listener for the clear button
        view.findViewById<Button>(R.id.removerQuantidade).setOnClickListener {
            handleClearButtonClick()
            valorDescontoTextView.text = "$currentDiscountPercentage%"
        }

        // Set click listener for the delete button (btApagar)
        view.findViewById<ImageButton>(R.id.btApagar).setOnClickListener {
            handleDeleteButtonClick()
            valorDescontoTextView.text = "$currentDiscountPercentage%"
        }

        // Configurar o clique no botão "Ok"
        view.findViewById<Button>(R.id.btOk).setOnClickListener {
            // 1. Calcular o valor total atualizado com o desconto
            val desconto = currentDiscountPercentage.toDouble() / 100.0
            val novoTotal = calcularNovoTotal(desconto)

            // 2. Set the discount value in the SharedViewModel
            sharedViewModel.setDiscountValue(desconto)

            // 3. Fechar o diálogo
            dismiss()
        }

        return view
    }
    private fun calcularNovoTotal(desconto: Double): Double {
        // Obter o valor total do ViewModel
        val totalAtual = sharedViewModel.totalSelectedValue.value ?: 0.0
        Log.d("testando desconto", "teste desconto valor atual $totalAtual $desconto")
        // Calcular o novo valor total com o desconto
        return totalAtual * (1 - desconto)

    }


    private fun handleNumericButtonClick(buttonId: Int) {
        // Find the Button by its ID
        val button = view?.findViewById<Button>(buttonId)

        // Get the text from the button
        val buttonText = button?.text?.toString()

        // Save the current state as the previous state
        previousDiscountPercentage = currentDiscountPercentage

        // If currentDiscountPercentage is the default value, replace it with the new value
        if (currentDiscountPercentage == 10) {
            currentDiscountPercentage = buttonText?.toIntOrNull() ?: 0
        } else {
            // Check if the current discount percentage has already two digits
            if (currentDiscountPercentage in 10..99) {
                // If it has two digits, do nothing (or handle it as needed)
                return
            }

            // Concatenate the numeric value to the current discount percentage, limit to two digits
            currentDiscountPercentage =
                (currentDiscountPercentage * 10 + (buttonText?.toIntOrNull() ?: 0))
                    .coerceIn(0, 99)
        }
    }


    private fun handleClearButtonClick() {
        // Reset the discount percentage to the default value (10%)
        currentDiscountPercentage = 10
    }

    private fun handleDeleteButtonClick() {
        // Extract the last digit of the current discount percentage
        val lastDigit = currentDiscountPercentage % 10

        // Remove the last digit from the current discount percentage
        currentDiscountPercentage /= 10

        // Ensure the percentage is never less than 0
        currentDiscountPercentage = currentDiscountPercentage.coerceIn(0, 99)

        // If there was only one digit, set the currentDiscountPercentage back to the default
        if (currentDiscountPercentage == 0) {
            currentDiscountPercentage = 10
        }
    }
}
