package com.example.lab5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5.databinding.FragmentFinalBinding

/**
 * A simple [Fragment] subclass.
 */
class FinalFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: FragmentFinalBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_final,
            container,
            false)
        setHasOptionsMenu(true)
        val application = requireNotNull(this.activity).application

        val dataSource = QuestionDatabase.getInstance(application).questionDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val adapter = AnswerAdapter()
        binding.answerList.adapter = adapter

        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(MainViewModel::class.java)



        viewModel._polls.observe(viewLifecycleOwner, Observer {

        })

        viewModel._qtns.observe(viewLifecycleOwner, Observer {

        })

        viewModel._answers.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(!it.isNullOrEmpty()) {
                    adapter.data = it
                }
            }
            /*var string: String = ""
            var cont: Int = 1
            for (poll in viewModel._polls.value!!){
                string += "Encuesta " + cont.toString()+ "\n" + "Fecha: "+poll.create_date + ":\n"
                for(answer in viewModel._answers.value!!){
                    if(answer.poll_id!! == poll.id!!){
                        string += answer.toString()+"\n"
                    }
                }
                cont++
            }
            binding.text.text = string*/
        })


        return binding.root
    }

}
