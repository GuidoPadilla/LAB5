package com.example.lab5

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AnswerViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val answers: MutableLiveData<String> = MutableLiveData<String>()
    val _answers:MutableLiveData<String>
        get() = answers

    private val rating: MutableLiveData<Int> = MutableLiveData<Int>()
    val _rating:MutableLiveData<Int>
        get() = rating

    private val cont: MutableLiveData<Int> = MutableLiveData<Int>()
    val _cont:MutableLiveData<Int>
        get() = cont
    init {
        _cont.value = 0
        _rating.value = 0
        _answers.value = ""
    }

    fun addAnswer(string: String){
        answers.value = answers.value.plus(string)
    }

    fun addRating(int: Int){
        rating.value = rating.value?.plus(int)
    }

    fun sumCont(){
        cont.value = _cont.value?.plus(1)
    }

}