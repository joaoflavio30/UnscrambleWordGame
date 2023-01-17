package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val wordList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String


    val score = MutableLiveData(0)

    val currentWordCount = MutableLiveData(0)

    val _currentScrambledWord = MutableLiveData<String>()

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }


    override fun onCleared() {

        Log.d("GameFragment", "viewModel Destructive")
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val currentScrambledWord = currentWord.toCharArray()
        currentScrambledWord.shuffle()

        while (currentWord.equals(currentScrambledWord)) {
            currentScrambledWord.shuffle()
        }

        if (wordList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(currentScrambledWord)
            wordList.add(currentWord)
        }

    }

    fun skipWord() {

        nextWord()
    }

    fun nextWord(): Boolean {

        return if (currentWordCount.value!! < MAX_NO_OF_WORDS) {
            currentWordCount.value = (currentWordCount.value)?.inc()
            getNextWord()
            true
        } else false
    }

    private fun rightResponse() {
        score.value = (score.value)?.plus(20)
    }

    fun responseVerification(userResponse: String): Boolean {
        return if (userResponse
            == currentWord
        ) {
            rightResponse()
            true
        } else false
    }

    fun reinitializeData() {
        score.value = 0
        currentWordCount.value = 0
        wordList.clear()
        getNextWord()


    }

}