package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import kotlin.math.pow

fun main() {
    val filename = "DayFiveData"
    val solution = solveItDayFivePartTwo(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayFivePartTwo(lines: List<String>): Int {
    val sortedNumbers = lines.map{getPassId(it)}.sorted()
    return sortedNumbers.filterIndexed {i, c -> i < sortedNumbers.size - 1 && (sortedNumbers[i + 1] - c) == 2}.first() + 1
}

