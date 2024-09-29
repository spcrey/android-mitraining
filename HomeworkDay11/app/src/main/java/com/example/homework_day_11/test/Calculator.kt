package com.example.homework_day_11.test

enum class Operator {
    ADD, SUB, MUL, DIV
}

fun operatorFromString(operatorString: String): Operator? {
    return when (operatorString) {
        "+" -> Operator.ADD
        "-" -> Operator.SUB
        "*" -> Operator.MUL
        "/" -> Operator.DIV
        else -> null
    }
}

abstract class Expression() {
    abstract fun calc(): Double?
}

class CustomExpression(
    private val num1: Double,
    private val num2: Double,
    private val operator: Operator?
) : Expression() {
    override fun calc(): Double? {
        if (operator == null) {
            return null
        }
        return when (operator) {
            Operator.ADD -> num1 + num2
            Operator.SUB -> num1 - num2
            Operator.MUL -> num1 * num2
            Operator.DIV -> if (num2 != 0.0) num1 / num2 else null
        }
    }
}

class Calculator {
    fun run() {
        println("Calculator running")
        while (true) {
            print("Please input operator (+, -, *, / or exit): ")
            val operatorString = readln()
            if (operatorString == "exit") {
                break
            }

            val operator = operatorFromString(operatorString)
            if (operator == null) {
                println("Invalid operator")
                continue
            }

            print("Please input the first num: ")
            val num1 = readlnOrNull()?.toDoubleOrNull()
            if (num1 == null) {
                println("Invalid num")
                continue
            }

            print("Please input the second num: ")
            val num2 = readlnOrNull()?.toDoubleOrNull()
            if (num2 == null) {
                println("Invalid num")
                continue
            }

            val expression = CustomExpression(num1, num2, operator)
            val result = expression.calc()
            if (result != null) {
                println("Result: $result")
            } else {
                println("Invalid expression")
            }
        }
    }
}
