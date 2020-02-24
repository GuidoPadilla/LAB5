package com.example.lab5


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.lab5.databinding.FragmentAnswerBinding

/**
 * A simple [Fragment] subclass.
 */
class AnswerFragment : Fragment() {

    private lateinit var binding: FragmentAnswerBinding
    private lateinit var viewModel: AnswerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_answer,
            container,
            false)
        viewModel = activity?.run {
            ViewModelProviders.of(this).get(AnswerViewModel::class.java)
        } ?: throw Exception("HOOLA")

        viewModel._cont.observe(viewLifecycleOwner, Observer {
            binding.numText.text = viewModel._cont.value.toString()
            binding.ratingText.text = (viewModel._rating.value!! / viewModel._cont.value!!).toString()
        })

        binding.answerButton.setOnClickListener{
            Toast.makeText(context,viewModel._answers.value.toString(), Toast.LENGTH_SHORT).show()
        }

        binding.newButton.setOnClickListener{
            it.findNavController().navigate(R.id.action_answerFragment_to_gameFragment)
        }
        return binding.root
    }


}
