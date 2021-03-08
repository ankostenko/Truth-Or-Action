package com.jetbrains.handson.app.truthoraction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ActionsViewModel(application: Application) : AndroidViewModel(application), ItemViewModel {
    private val _actions = MutableLiveData(
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

    private val _customActions = MutableLiveData<MutableList<String>>(mutableListOf())
    override val customItems: LiveData<MutableList<String>> = _customActions

    override fun addItem(item: String, index: Int) {
        if (index != -1) {
            // We edit question not add a new one
            _customActions.value?.set(index, item)
        } else {
            // We add a new question
            _customActions.value?.add(item)
        }
        val temp = _customActions
        _customActions.value = temp.value
    }

    override fun removeItem(index: Int) {
        _customActions.value?.removeAt(index)
        val temp = _customActions
        _customActions.value = temp.value
    }

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