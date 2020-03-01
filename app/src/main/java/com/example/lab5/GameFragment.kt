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
import kotlinx.android.synthetic.main.fragment_game.*
import kotlin.math.absoluteValue

/**
 * A simple [Fragment] subclass.
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var  viewModel: MainViewModel/*
    private lateinit var  viewModel2: AnswerViewModel*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_game,
            container,
            false)
        val application = requireNotNull(this.activity).application

        val dataSource = QuestionDatabase.getInstance(application).questionDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.onStartTrackingPoll()

        /*viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("HOOLA")
        viewModel2 = activity?.run {
            ViewModelProviders.of(this).get(AnswerViewModel::class.java)
        } ?: throw Exception("HOOLA")*/
        viewModel._pos.value = 0

        viewModel._qtns.observe(viewLifecycleOwner, Observer {
            binding.questionText.text = viewModel._qtns.value?.get(viewModel._pos.value!!)?.name
        })

        viewModel._polls.observe(viewLifecycleOwner, Observer {

        })

        viewModel._answers.observe(viewLifecycleOwner, Observer {

        })

        viewModel._pos.observe(viewLifecycleOwner, Observer {

            binding.questionText.text = viewModel._qtns.value?.get(viewModel._pos.value!!)?.name

        })

        var texto: String = ""
        var bandera: Boolean = false
        var cont: Int = 0
        var rating: Int = 0
        viewModel._pos.value = 0


        binding.submitButton.setOnClickListener{
            texto = binding.submitText.text.toString()
            val id_q = viewModel._qtns.value?.get(cont)?.Id!!
            val id_p = viewModel._polls.value?.get(viewModel._polls.value?.size!!-1)?.id!!
            if(cont+1 == viewModel._qtns.value?.size){
                cont++
                /*if(bandera){*/
                    rating = binding.simpleRatingBar.rating.toInt()
                    if(viewModel._qtns.value?.get(cont-1)?.type!! == 1) {
                        viewModel.onStartTrackingAnswer(
                            texto,
                            0,
                            viewModel._qtns.value?.get(cont-1)?.Id!!,
                            viewModel._polls.value?.get(0)?.id!!
                        )
                    }
                    else{
                        viewModel.onStartTrackingAnswer(
                            "",
                            rating,
                            viewModel._qtns.value?.get(cont-1)?.Id!!,
                            viewModel._polls.value?.get(0)?.id!!
                        )
                    }
                        /*addAnswer("Encuesta " + viewModel2._cont.value.toString()+" :"+texto+"\n")*/

                    it.findNavController().navigate(R.id.action_gameFragment_to_answerFragment)

                /*}*/
                /*else{
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
                }*/
            }
            else {
                cont++
                viewModel.nextQuestion()
                rating = binding.simpleRatingBar.rating.toInt()
                if(viewModel._qtns.value?.get(cont-1)?.type!! == 1) {

                    viewModel.onStartTrackingAnswer(
                        texto,
                        0,
                        viewModel._qtns.value?.get(cont-1)?.Id!!,
                        viewModel._polls.value?.get(0)?.id!!
                    )
                }
                else{

                    viewModel.onStartTrackingAnswer(
                        "",
                        rating,
                        viewModel._qtns.value?.get(cont-1)?.Id!!,
                        viewModel._polls.value?.get(0)?.id!!
                    )
                }
                if(viewModel._qtns.value?.get(cont)?.type!! == 1) {
                    binding.simpleRatingBar.visibility = View.GONE
                    binding.submitText.visibility = View.VISIBLE
                }
                else{
                    binding.simpleRatingBar.visibility = View.VISIBLE
                    binding.submitText.visibility = View.GONE
                }
                /*texto += "Pregunta " + (cont-1).toString() + ": " + binding.submitText.text.toString() + "  "*/


            }
        }
        return binding.root
    }



}
