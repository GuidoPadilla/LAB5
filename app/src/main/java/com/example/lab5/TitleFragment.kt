package com.example.lab5


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.lab5.databinding.FragmentTitleBinding

/**
 * A simple [Fragment] subclass.
 */
class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding
    /*private lateinit var viewModel: MainViewModel*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_title,
            container,
            false
        )

        val application = requireNotNull(this.activity).application

        val dataSource = QuestionDatabase.getInstance(application).questionDatabaseDao
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.verifyCount()
        /*viewModel = activity?.run {
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("HOOLA")
        */

        /*viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)*/
        binding.floatingActionButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_titleFragment_to_optionFragment)
        }

        binding.startButton.setOnClickListener{
            val s = viewModel._qtns.value?.size
            if (s != 0) {
                it.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
            }
            else{
                Toast.makeText(context,"Ingrese alguna pregunta",Toast.LENGTH_SHORT).show()
            }
        }

        viewModel._qtns.observe(viewLifecycleOwner, Observer {
            binding.texto1.text =  viewModel._qtns.value.toString()
        })
        return binding.root
    }




}
