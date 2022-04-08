package com.example.weatherzadravec.ui.base

interface BaseAdapter<T> {

    var items: List<T>?

    fun getItemAt(index: Int): T?

    fun getItemPosition(item: T): Int
}