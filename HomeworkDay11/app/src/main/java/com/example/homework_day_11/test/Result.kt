package com.example.homework_day_11.test

sealed class Result<out R> {
    data class Success<out T>(val data: T, val message: String = "") : Result<T>()
    data class Error(val expression: Expression): Result<Nothing>()
    data class Loading(val time: Long = System.currentTimeMillis()) : Result<Nothing>()
}