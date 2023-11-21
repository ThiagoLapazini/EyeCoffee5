
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.eyecoffee.R
import com.example.eyecoffee.adapters.SharedViewModel
import com.google.android.material.imageview.ShapeableImageView

class Lancamento : DialogFragment() {

    private lateinit var imagemProduto: ShapeableImageView
    private lateinit var nomeProduto: TextView
    private lateinit var precoProduto: TextView

    private lateinit var sharedViewModel: SharedViewModel

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

        // Inicializar o SharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Observar mudanças no produto selecionado
        sharedViewModel.selectedProduto.observe(viewLifecycleOwner) { produto ->
            // Atualizar a interface do usuário com as informações do produto
            Glide.with(requireContext())
                .load(produto.productImage)
                .into(imagemProduto)
            nomeProduto.text = produto.productTitle
            precoProduto.text = produto.productPrice
        }

        return view
    }


}





