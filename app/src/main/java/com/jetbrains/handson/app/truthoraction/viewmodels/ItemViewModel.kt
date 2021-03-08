package com.jetbrains.handson.app.truthoraction.viewmodels

import androidx.lifecycle.LiveData

interface ItemViewModel {
    val customItems: LiveData<MutableList<String>>

    fun addItem(item: String, index: Int = -1)
    fun removeItem(index: Int)
}