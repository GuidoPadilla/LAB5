package com.example.lab5


import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.lab5.databinding.FragmentGameBinding

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var  viewModel: MainViewModel
    private lateinit var  viewModel2: AnswerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_game,
            container,
            false)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("HOOLA")
        viewModel2 = activity?.run {
            ViewModelProviders.of(this).get(AnswerViewModel::class.java)
        } ?: throw Exception("HOOLA")
        viewModel._pos.value = 0

        viewModel._pos.observe(viewLifecycleOwner, Observer {
            binding.questionText.text = viewModel.getQuestion()
        })
        var texto: String = ""
        var bandera: Boolean = false
        var cont: Int = 1
        var rating: Int = 0
        viewModel._pos.value = 0


        binding.submitButton.setOnClickListener{

            if(cont == viewModel._size.value){
                if(bandera){
                    rating = binding.simpleRatingBar.rating.toInt()
                    viewModel2.sumCont()
                    viewModel2.addAnswer("Encuesta " + viewModel2._cont.value.toString()+" :"+texto+"\n")
                    viewModel2.addRating(rating)
                    binding.simpleRatingBar.visibility = View.GONE
                    binding.submitText.visibility = View.VISIBLE
                    it.findNavController().navigate(R.id.action_gameFragment_to_answerFragment)
                }
                else{
                    if(binding.simpleRatingBar.visibility == View.GONE)
                    {
                        if(binding.questionText.text.equals("Comentario o recomendacion")){
                            texto += "Pregunta " + (cont+1).toString() + ": " + binding.submitText.text.toString() + "  "
                            binding.simpleRatingBar.visibility = View.VISIBLE
                            binding.submitText.visibility = View.GONE
                            binding.questionText.text = "RATTING DE LA APP"
                            bandera = true
                        }
                        else {
                            texto += "Pregunta " + (cont).toString() + ": " + binding.submitText.text.toString() + "  "
                            binding.questionText.text = "Comentario o recomendacion"
                        }
                    }
                }
            }
            else {


                cont++
                viewModel.nextQuestion()
                texto += "Pregunta " + (cont-1).toString() + ": " + binding.submitText.text.toString() + "  "


            }
        }
        return binding.root
    }


}
