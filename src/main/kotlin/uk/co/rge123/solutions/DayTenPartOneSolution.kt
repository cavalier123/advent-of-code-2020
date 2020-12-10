package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWordsWhitespaceSeparated

fun main() {
    val filename = "DayTenData"
    val solution = solveItDayTenPartOne(splitFileToWordsWhitespaceSeparated(filename).map {it.toInt()}.sorted())
    print ("Solution is $solution")
}

fun solveItDayTenPartOne(numbers: List<Int>): Int {

    var oneDiff = 0
    var threeDiff = 0

    for (index in 0 .. numbers.size - 2) {
        val curNum = numbers[index]
        val nextNum = numbers[index + 1]
        if (nextNum - curNum == 1) ++ oneDiff
        if (nextNum - curNum == 3) ++ threeDiff
    }

    oneDiff++ // to account for extra jump from starting point
    threeDiff++ // to account for extra jump to the socket thingy

    return oneDiff * threeDiff
}
