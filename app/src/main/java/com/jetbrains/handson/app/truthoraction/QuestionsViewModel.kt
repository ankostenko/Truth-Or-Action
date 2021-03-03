package com.jetbrains.handson.app.truthoraction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class QuestionsViewModel : ViewModel() {
    private val _questions = MutableLiveData<List<String>>(
        mutableListOf(
            "Если бы у вас была машина времени, в какой период вы бы поехали?",
            "Если бы вам пришлось выбирать ум или красоту, что бы вы выбрали?",
            "Если бы вы могли жить где угодно в мире, где бы вы жили?",
            "Вы бы когда-нибудь начали использовать сайт знакомств?",
            "Вы бы предпочли быть умным или счастливым, и почему?",
            "Что в вас самое странное? Вы этим гордитесь?",
            "Кто ваш любимый персонаж Диснея?",
            "Что бы вы сделали, если бы на день были невидимы?",
            "Если бы вы могли быть любым динозавром, кем бы вы были?",
            "Опишите себя через 30 лет.",
            "Если бы вы могли попробовать на день любую работу, что бы это было?",
        )
    )
    private var _availableQuestions: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())
    val availableQuestions: LiveData<MutableList<String>> = _availableQuestions

    private val _customQuestions = MutableLiveData<MutableList<String>>(mutableListOf())
    val customQuestions: LiveData<MutableList<String>> = _customQuestions

    init {
        availableQuestions.value?.addAll(_questions.value?: mutableListOf("Couldn't initialize available questions"))
    }

    fun reinitializeAvailableQuestions() {
        availableQuestions.value?.clear()
        availableQuestions.value?.addAll(_questions.value?: mutableListOf("Couldn't initialize available questions"))
        availableQuestions.value?.addAll(customQuestions.value?: mutableListOf("Couldn't initialize available questions with custom questions"))
    }

    fun chooseRandomQuestion(): String {
        var randomIndex = 0
        if (availableQuestions.value?.lastIndex == -1) {
            availableQuestions.value?.addAll(_questions.value?: mutableListOf())
            randomIndex = Random.nextInt(0, availableQuestions.value?.lastIndex?:1)
        } else if (availableQuestions.value?.lastIndex != 0) {
            randomIndex = Random.nextInt(0, availableQuestions.value?.lastIndex?:1)
        }
        val nextQuestion = availableQuestions.value?.get(randomIndex)?: "[Error]: No questions found"
        availableQuestions.value?.removeAt(randomIndex)
        return nextQuestion
    }

    fun addQuestion(question: String) {
        customQuestions.value?.add(question)
    }
}