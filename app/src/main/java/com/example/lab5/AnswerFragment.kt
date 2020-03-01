package com.example.lab5


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.lab5.databinding.FragmentAnswerBinding

/**
 * A simple [Fragment] subclass.
 */
class AnswerFragment : Fragment() {

    private lateinit var binding: FragmentAnswerBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_answer,
            container,
            false)
        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application

        val dataSource = QuestionDatabase.getInstance(application).questionDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(MainViewModel::class.java)
        /*viewModel = activity?.run {
            ViewModelProviders.of(this).get(AnswerViewModel::class.java)
        } ?: throw Exception("HOOLA")*/
        viewModel._qtns.observe(viewLifecycleOwner, Observer {

        })

        viewModel._polls.observe(viewLifecycleOwner, Observer {

        })

        viewModel._answers.observe(viewLifecycleOwner, Observer {
            if(viewModel._answers.value?.size!! != 0){
            binding.numText.text = viewModel._polls.value?.size.toString()
            var suma: Int = 0
            for(answer in viewModel._answers.value!!){
                var cont: Int = 0
                var final: Int = 0
                for(question in viewModel._qtns.value!!)
                {
                    if(question.Id!! == answer.question_id!!)
                        final = cont
                    cont++
                }
                if (viewModel._qtns.value?.get(final)?.type!! == 2)
                {
                    suma += answer.answer_number!!
                }
            }
            binding.ratingText.text = (suma/viewModel._polls.value?.size!!).toString()
            }
            else
            {
                findNavController().navigate(R.id.action_answerFragment_to_titleFragment)
            }
        })
        /*viewModel._cont.observe(viewLifecycleOwner, Observer {
            binding.numText.text = viewModel._cont.value.toString()
            binding.ratingText.text = (viewModel._rating.value!! / viewModel._cont.value!!).toString()
        })*/

        binding.answerButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_answerFragment_to_finalFragment)
        }

        binding.newButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_answerFragment_to_gameFragment)
        }
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.delete_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId)
        {
            R.id.answers -> viewModel.onClear()

        }
        return super.onOptionsItemSelected(item)
    }


}
