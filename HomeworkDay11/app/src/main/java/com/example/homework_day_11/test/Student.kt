package com.example.homework_day_11.test

enum class Sex {
    BOY, GIRL;

    override fun toString(): String = when(this) {
        BOY -> "boy"
        GIRL -> "girl"
    }
}

fun isBoy(sex: Sex) = when(sex) {
    Sex.BOY -> true
    Sex.GIRL -> false
}

data class Student(
    val name: String,
    var age: Int,
    var sex: Sex,
)
