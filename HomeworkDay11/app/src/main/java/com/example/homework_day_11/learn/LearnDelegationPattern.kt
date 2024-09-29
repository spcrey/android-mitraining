package com.example.homework_day_11.learn

object LearnDelegationPattern {
    interface DB {
        fun save()
    }
    class SqlDB : DB {
        override fun save() {
        println("save to sql")
        }
    }
    class GreenGaoDB : DB {
        override fun save() {
            println("save to green gao")
        }
    }
    class UniversalDB(db: DB): DB by db

    @JvmStatic
    fun main(args: Array<String>) {
        UniversalDB(SqlDB()).save()
        UniversalDB(GreenGaoDB()).save()
    }
}