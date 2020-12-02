package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWordsWhitespaceSeparated

fun main() {
    val filename = "DayOneData"
    val words = splitFileToWordsWhitespaceSeparated(filename)
    println ("Number of words before sneaky filter = ${words.size}")
    val numbers = words.map{it.toInt()}.filter{ it <= (2020-254-457) }
    println ("Number of numbers after sneaky filter = ${numbers.size}")

    val solution = solveItDayOnePartTwo(numbers)
    print ("Solution for Day One Part two is $solution")
}

fun solveItDayOnePartTwo(numbers: List<Int>): Int {

    var answer = 0
    loop@ for (i in 0 until numbers.size - 2) {
        for (j in i + 1 until numbers.size - 1) {
            for (k in j + 1 until numbers.size) {
                if (numbers[i] + numbers[j] + numbers[k] == 2020) {
                    println("The numbers we want are ${numbers[i]} ${numbers[j]} ${numbers[k]}")
                    answer = (numbers[i] * numbers[j] * numbers[k])
                    break@loop
                }
            }
        }
    }
    return answer
}