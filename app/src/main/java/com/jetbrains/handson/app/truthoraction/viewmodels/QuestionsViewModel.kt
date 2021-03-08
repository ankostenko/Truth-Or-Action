package com.jetbrains.handson.app.truthoraction.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import kotlin.random.Random

class QuestionsViewModel(application: Application): AndroidViewModel(application), ItemViewModel {
    private val questionsFile: File = File(application.applicationContext.filesDir, "questions.q")

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

    private val _customQuestions = MutableLiveData<MutableList<String>>(mutableListOf())
    override val customItems: LiveData<MutableList<String>> = _customQuestions

    init {
        _availableQuestions.value?.addAll(_questions.value?: mutableListOf("Couldn't initialize available questions"))
    }

    // Read questions from the file
    init {
        if (!questionsFile.exists()) {
            questionsFile.createNewFile()
        }
        runBlocking {
            var list: List<String> = listOf()
            val readFileJob = GlobalScope.launch {
                list = questionsFile.readLines()
            }

            // Read saved questions to the list
            readFileJob.join()
            _customQuestions.value?.addAll(list)
        }
    }

    fun reinitializeAvailableQuestions() {
        _availableQuestions.value?.clear()
        _availableQuestions.value?.addAll(_questions.value?: mutableListOf("Couldn't initialize available questions"))
        _availableQuestions.value?.addAll(customItems.value?: mutableListOf("Couldn't initialize available questions with custom questions"))
    }

    fun chooseRandomQuestion(): String {
        var randomIndex = 0
        if (_availableQuestions.value?.lastIndex == -1) {
            reinitializeAvailableQuestions()
            randomIndex = Random.nextInt(0, _availableQuestions.value?.lastIndex?:1)
        } else if (_availableQuestions.value?.lastIndex != 0) {
            randomIndex = Random.nextInt(0, _availableQuestions.value?.lastIndex?:1)
        }
        val nextQuestion = _availableQuestions.value?.get(randomIndex)?: "[Error]: No questions found"
        _availableQuestions.value?.removeAt(randomIndex)
        return nextQuestion
    }

    override fun addItem(item: String, index: Int) {
        if (index != -1) {
            // We edit question not add a new one
            _customQuestions.value?.set(index, item)
        } else {
            // We add a new question
            _customQuestions.value?.add(item)
        }
        val temp = _customQuestions
        _customQuestions.value = temp.value

        saveListToFile()
    }

    override fun removeItem(index: Int) {
        _customQuestions.value?.removeAt(index)
        val temp = _customQuestions
        _customQuestions.value = temp.value

        saveListToFile()
    }

    private fun saveListToFile() {
        GlobalScope.launch {
            if (_customQuestions.value?.isEmpty() == true) {
                questionsFile.writeText("")
            }

            // Save questions to the file
            var buffer: String = ""
            for (str in _customQuestions.value?: listOf()) {
                buffer += str + "\n"
            }
            questionsFile.writeText(buffer)
        }
    }
}