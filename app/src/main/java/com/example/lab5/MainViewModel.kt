package com.example.lab5

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*

class MainViewModel (
        val database: QuestionDatabaseDao,
        application: Application
    ) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    /*private val questions:  MutableList<String> = mutableListOf<String>()*/

    private val total = MutableLiveData<Int>()
    val _total:MutableLiveData<Int>
        get() = pos
    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val qtns = database.getAllQuestions()
    val _qtns: LiveData<List<Question>>
        get() = qtns

    private val polls = database.getAllPolls()
    val _polls: LiveData<List<Poll>>
        get() = polls

    private val answer = database.getAllAnswers()
    val _answers: LiveData<List<Answer>>
        get() = answer



    private suspend fun insertQuestion(question: Question) {
        withContext(Dispatchers.IO) {
            database.insertQuestion(question)
        }
    }

    private suspend fun updateQuestion(question: Question) {
        withContext(Dispatchers.IO) {
            database.updateQuestion(question)
        }
    }

    private suspend fun clearQuestion() {
        withContext(Dispatchers.IO) {
            database.clearQuestions()
        }
    }

    private suspend fun insertAnswer(answer: Answer) {
        withContext(Dispatchers.IO) {
            database.insertAnswer(answer)
        }
    }

    private suspend fun updateAnswer(answer: Answer) {
        withContext(Dispatchers.IO) {
            database.updateAnswer(answer)
        }
    }

    private suspend fun clearAnswer() {
        withContext(Dispatchers.IO) {
            database.clearAnswers()
        }
    }

    private suspend fun insertPoll(poll: Poll) {
        withContext(Dispatchers.IO) {
            database.insertPoll(poll)
        }
    }

    private suspend fun updateAnswer(poll: Poll) {
        withContext(Dispatchers.IO) {
            database.updatePoll(poll)
        }
    }

    private suspend fun clearPoll() {
        withContext(Dispatchers.IO) {
            database.clearPolls()
        }
    }

    fun onStartTrackingQuestion(text: String, type: Int) {
        uiScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newQuestion = Question()

            newQuestion.name = text
            newQuestion.type = type
            newQuestion.default = false



            insertQuestion(newQuestion)

        }
    }

    fun onStartTrackingPoll() {
        uiScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newPoll = Poll()



            insertPoll(newPoll)

        }
    }

    fun onStartTrackingAnswer(text: String, number: Int, id_Question: Int, id_Poll: Int) {
        uiScope.launch {
            // Create a new night, which captures the current time,
            // and insert it into the database.
            val newAnswer = Answer()

            newAnswer.poll_id = id_Poll
            newAnswer.question_id =  id_Question
            newAnswer.answer_text = text
            newAnswer.answer_number = number



            insertAnswer(newAnswer)

        }
    }
    /*private suspend fun Ratings(): Int {
        var temp: Int = 0
        withContext(Dispatchers.IO) {
            return@withContext database.getRatings()!!
        }
        return 0
    }*/


    private suspend fun getR(): Int{
        return withContext(Dispatchers.IO){
            var int = database.getRatings()
            int
        }
    }

    fun getRating(){
        uiScope.launch {
            _total.value = 0
            _total.value?.plus(getR())
        }
    }

    fun onClear() {
        uiScope.launch {
            // Clear the database table.
            clearAnswer()
            clearPoll()

            clearQuestion()
        }
    }

    /**
     * Called when the ViewModel is dismantled.
     * At this point, we want to cancel all coroutines;
     * otherwise we end up with processes that have nowhere to return to
     * using memory and resources.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /*private val word = MutableLiveData<String>()
    val _word:MutableLiveData<String>
        get() = word

    private val size = MutableLiveData<Int>()
    val _size:MutableLiveData<Int>
        get() = size*/

    private val pos = MutableLiveData<Int>()
    val _pos:MutableLiveData<Int>
        get() = pos
    var x: Int = 0
    init {
        /*_size.value = 0*/
        _pos.value = 0
    }


    private suspend fun getCountQuestion(): Int {
        var temp:Int = 0
        withContext(Dispatchers.IO) {
            temp = database.getCountQuestion()
        }
        return temp
    }

    /*private suspend fun  getQuestion(int: Int): Question?{
        var question : Question? = null
        withContext(Dispatchers.IO){
            question = database.getQuestion(int)
        }
        return question
    }*/

    /*fun startFetchQuestion(int: Int): Question?{
        var question: Question? = null
        uiScope.launch {
            question = getQuestion(int)
        }
        return question
    }*/

    /*fun getCountQ(): Int
    {
        var temp: Int = 0
        uiScope.launch {
            temp = getCountQuestion()
        }
        return temp
    }*/

    fun verifyCount(){
        uiScope.launch {
            x = getCountQuestion()
            if(x/*database.getCountQuestion()*/ == 0){

                val newQuestion = Question()
                val anotherQuestion = Question()

                newQuestion.name = "Comentario o recomendacion"
                newQuestion.type = 1

                anotherQuestion.name = "Rating"
                anotherQuestion.type = 2

                insertQuestion(newQuestion)
                insertQuestion(anotherQuestion)
            }
        }
    }
    /*fun getQuestions() : MutableList<String>{
        return questions
    }

    fun addQuestion(string: String) {
        questions.add(string)
        _size.value = (_size.value)?.plus(1)
    }

    fun getQuestion(): String {
        return questions.get(pos.value!!.toInt())
    }
*/

    fun nextQuestion(){
        _pos.value = (_pos.value)?.plus(1)
    }
}

