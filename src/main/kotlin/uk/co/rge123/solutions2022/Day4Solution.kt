package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToSetOfIntPairs

fun main (args: Array<String>) {
    val rangeLists = splitFileToSetOfIntPairs("dayFour")
    val totalContainsIn = rangeLists.count { (overlaps(it[0], it[1])) }
    println ("total = $totalContainsIn")
}

fun contains (p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
    return (p1.first <= p2.first && p1.second >= p2.second) || (p2.first <= p1.first && p2.second >= p1.second)
}

fun overlaps (p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
    return ((p1.second >= p2.first) && (p1.first <= p2.second))
}



//fun solveItDayTwoPartOne(lines: List<String>) = lines.filter { getPasswordInfo(it).isValid() }.count()
//
//fun getPasswordInfo(input: String): PasswordInfo {
//    val words = input.split(" ")
//    val rangeNumbers = words[0].split("-")
//    return PasswordInfo(rangeNumbers[0].toInt(), rangeNumbers[1].toInt(), words[1][0], words[2])
//}
//
//data class PasswordInfo(val rangeStart: Int, val rangeEnd: Int, val checkedChar: Char, val password: String) {
//    fun isValid() = password.count { it == checkedChar } in rangeStart..rangeEnd
//}