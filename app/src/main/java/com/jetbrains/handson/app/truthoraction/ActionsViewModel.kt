package com.jetbrains.handson.app.truthoraction

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ActionsViewModel : ViewModel() {
    private val _actions = MutableLiveData<MutableList<String>>(
        mutableListOf(
            "Не говорите «да» или «нет» в течение 1 минуты.",
            "Станцуйте Макарену.",
            "Прочтите таблицу умножения числа 2.",
            "Имитируйте китайский акцент в течение 2ух раундов.",
            "Съешьте ложку соли.",
            "Стойте как фламинго следующие четыре раунда.",
            "Поцелуйте соседа справа в щеку.",
            "Изобразите любимую знаменитость.",
            "Придумайте рассказ о предмете справа.",
            "Позвоните случайному человеку и спойте ему с днем рождения.",
            "Прочтите вслух последнее полученное сообщение.",
        )
    )
    var availableActions: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    init {
        availableActions.value?.addAll(_actions.value?: mutableListOf("Couldn't initialize available actions"))
    }

    fun reinitializeAvailableActions() {
        availableActions.value?.clear()
        availableActions.value?.addAll(_actions.value?: mutableListOf("Couldn't initialize available actions"))
    }

    fun chooseRandomAction(): String {
        var randomIndex = 0
        if (availableActions.value?.lastIndex == -1) {
            availableActions.value?.addAll(_actions.value?: mutableListOf())
            randomIndex = Random.nextInt(0, availableActions.value?.lastIndex?:1)
        } else if (availableActions.value?.lastIndex != 0) {
            randomIndex = Random.nextInt(0, availableActions.value?.lastIndex?:1)
        }
        val nextQuestion = availableActions.value?.get(randomIndex)?: "[Error]: No actions found"
        availableActions.value?.removeAt(randomIndex)
        return nextQuestion
    }
}