package com.example.lab5


import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.lab5.databinding.FragmentOptionBinding
import java.security.acl.Owner

/**
 * A simple [Fragment] subclass.
 */
class OptionFragment : Fragment() {

    private lateinit var binding: FragmentOptionBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_option,
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
            ViewModelProviders.of(this).get(MainViewModel::class.java)
        } ?: throw Exception("HOOLA")*/

        /*viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)*/
        /*viewModel._word.value
        binding.setLifecycleOwner { this.lifecycle }
        binding.gameVIewModel = viewModel*/

        viewModel._qtns.observe(viewLifecycleOwner, Observer {
            binding.textView.text =  viewModel._qtns.value.toString()
        })
        /*viewModel._word.observe(viewLifecycleOwner, Observer {

        })*/
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item!!.itemId)
        {
            /*R.id.OptionFragment -> viewModel.onClear()*/
            R.id.OptionFragment -> viewModel.onStartTrackingQuestion(binding.editText.text.toString(),1)
        }
        return super.onOptionsItemSelected(item)
    }

}
