package com.example.homework_day_11.learn

import android.view.View

object LearnFun {
    @JvmStatic
    fun main(args: Array<String>) {
        fun add(a: Int, b: Int): Float = (a + b).toFloat()
        val function: (Int, Int) -> Float  = ::add;
        fun onClick(v: View): Unit {
            val a = function(2,3)
        }
    }
}