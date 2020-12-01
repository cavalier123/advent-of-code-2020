package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWordsWhitespaceSeparated

fun main() {
    val filename = "DayOneData"
    val words = splitFileToWordsWhitespaceSeparated(filename)
    println ("Number of words before sneaky filter = ${words.size}")
    val numbers = words.map{it.toInt()}.filter{ it <= (2020-254) }
    println ("Number of numbers after sneaky filter = ${numbers.size}")

    val solution = solveItDayOnePartOne(numbers)
    print ("Solution for Day One Part two is $solution")
}

fun solveItDayOnePartOne(numbers: List<Int>): Int {

    var answer = 0
    loop@ for (i in numbers.indices) {
        for (j in i until numbers.size) {
            if (numbers[i] + numbers[j] == 2020) {
                println("The numbers we want are ${numbers[i]} ${numbers[j]}")
                answer = (numbers[i] * numbers[j])
                break@loop
            }
        }
    }
    return answer
}
