package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

const val allChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun main (args: Array<String>) {

    val lines = splitFileToLines("dayThree")

    var totalScore = 0

    val threeLines = HashSet<String>()
    var lineCount = 1

    for (line in lines) {
        threeLines.add(line)
        if (lineCount % 3 == 0) {
            val charInCommon = getCharInCommon(threeLines)
            val charScore=getCharScore(charInCommon)
            println(charScore)
            totalScore += charScore
            threeLines.removeIf { true }
        }
        ++lineCount
    }
    println ("total = $totalScore")
}

fun getCharScore(aChar: Char): Int {
    return allChars.indexOf(aChar) + 1
}

fun getCharInCommon(lines: Set<String>): Char {
    var intersection: Set<Char> = allChars.toSet()
    for (line in lines) {
        intersection = intersection.intersect(line.toSet())
    }
    return intersection.first()
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