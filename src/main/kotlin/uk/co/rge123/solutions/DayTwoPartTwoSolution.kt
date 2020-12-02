package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayTwoData"
    val solution = solveItDayTwoPartTwo(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayTwoPartTwo(lines: List<String>) = lines.filter{getPasswordInfo(it).isValidPartTwo()}.count()