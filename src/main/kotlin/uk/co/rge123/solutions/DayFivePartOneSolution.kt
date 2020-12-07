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
    //return word.reversed().mapIndexed { i, c -> if (c in "BR") 2.0.pow(i).toInt() else 0}.sum()
    return word.reversed().foldIndexed(0) { i, a, c -> a + if (c in "BR") 2.0.pow(i).toInt() else 0}
}