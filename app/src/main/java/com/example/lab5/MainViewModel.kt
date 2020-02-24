package com.example.lab5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val questions:  MutableList<String> = mutableListOf<String>()
    val _questions:MutableList<String>
        get() = questions

    private val word = MutableLiveData<String>()
    val _word:MutableLiveData<String>
        get() = word

    private val size = MutableLiveData<Int>()
    val _size:MutableLiveData<Int>
        get() = size

    private val pos = MutableLiveData<Int>()
    val _pos:MutableLiveData<Int>
        get() = pos

    init {
        _size.value = 0
        _pos.value = 0
    }

    fun getQuestions() : MutableList<String>{
        return questions
    }

    fun addQuestion(string: String) {
        questions.add(string)
        _size.value = (_size.value)?.plus(1)
    }

    fun getQuestion(): String {
        return questions.get(pos.value!!.toInt())
    }

    fun nextQuestion(){
        _pos.value = (_pos.value)?.plus(1)
    }
}

