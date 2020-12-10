package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWordsWhitespaceSeparated

fun main() {
    val filename = "DayNineData"
    val solution = solveItDayNinePartOne(splitFileToWordsWhitespaceSeparated(filename).map {it.toLong()})
    print ("Solution is $solution")
}

fun solveItDayNinePartOne(numbers: List<Long>): Long {
    var required = 0L
    for (index in 25 .. numbers.size - 1) {
        if (!specialNumber(numbers[index], numbers.slice(index - 25 .. index - 1) ))
        {
            required = numbers[index]
            break;
        }
    }
    return required

    // 393911906
}

fun specialNumber(required: Long, numbers: List<Long>): Boolean {

    for (i in 0 .. numbers.size - 2) {
        for (j in i + 1 until numbers.size) {
            if (numbers[i] != numbers[j] &&  (numbers[i] + numbers[j] == required) ) {
                return true
            }
        }
    }
    return false
}




