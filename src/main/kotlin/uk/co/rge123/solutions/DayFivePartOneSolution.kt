package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import kotlin.math.pow

fun main() {
    val filename = "DayFiveData"
    val solution = solveItDayFivePartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayFivePartOne(lines: List<String>): Int {
    return lines.map{getPassId(it)}.maxOrNull() ?: 0
}

fun getPassId(word: String): Int {
    val rowTotal = word.mapIndexed { i, c -> if (c == 'B') 2.toDouble().pow(6 - i).toInt() else 0}.sum()
    val colTotal = word.mapIndexed { i, c -> if (c == 'R') 2.toDouble().pow(9 - i).toInt() else 0}.sum()
    return rowTotal * 8 + colTotal
}