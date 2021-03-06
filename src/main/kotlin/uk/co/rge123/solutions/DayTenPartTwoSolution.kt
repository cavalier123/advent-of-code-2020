package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWordsWhitespaceSeparated

fun main() {
    val filename = "DayTenData"
    val solution = solveItDayTenPartTwo(splitFileToWordsWhitespaceSeparated(filename).map {it.toInt()}.sorted())
    print ("Solution is $solution")
}

fun solveItDayTenPartTwo(numbers: List<Int>): Long {
    val hash = mutableMapOf(Pair(157, 1L))
    for (number in numbers.slice(numbers.size - 2 downTo 0)) {
        hash[number] = getTotalRoutesFromChildren(number, hash)
    }
    return getTotalRoutesFromChildren(0, hash)
}

fun getTotalRoutesFromChildren(startNum: Int, hash: MutableMap<Int, Long>): Long {
    return (startNum + 1 .. startNum + 3).fold(0L){ r, t -> r + (hash[t] ?: 0) }
}