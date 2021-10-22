package com.fatme.hyundai.callbacks

interface AdapterViewClickListener<T> {

    fun onClickAdapterView(objectAtPosition: T?, viewType: Int, position: Int)
}