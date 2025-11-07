package ru.justneedcoffee.simplelist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val items = mutableStateListOf<Int>()

    fun addItem() {
        items.add(items.size + 1)
    }
}