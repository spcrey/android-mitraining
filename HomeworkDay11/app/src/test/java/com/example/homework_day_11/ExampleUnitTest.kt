package com.example.homework_day_11

import android.util.Log
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        val selfIntroduction = """
            Hello, my name is [Your Name]. 
            I am [Your Age] years old, and I come from [Your City, Country]. 
            I enjoy [Your Hobbies or Interests], and I am passionate about [Your Passion or Interest]. 
            I look forward to getting to know you and engaging in interesting conversations.
        """.trimIndent()
        println(selfIntroduction)
    }
}