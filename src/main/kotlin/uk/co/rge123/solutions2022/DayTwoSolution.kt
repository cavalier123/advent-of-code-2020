package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main (args: Array<String>) {

    val lines = splitFileToLines("dayTwo")

    var totalScore = 0

    for (line in lines) {
        println (line)
        val theres = line[0]
        val yours = line [2]
        val score = getScore(theres, yours)
        totalScore += score
    }
    println ("total = $totalScore")
}

fun getScore (theres: Char, yours: Char): Int {

    // means you need to lose, Y means you need to end the round in a draw, and Z means you need to win. Good luck!"
    // rock paper scissors

    println ("theres = $theres, yours = $yours")

    var totalScore = 0
    var newYours = 'X'

    newYours = when (yours) {
        'X' -> {
            when(theres) {
                'A' -> 'Z'
                'B' -> 'X'
                'C' -> 'Y'
                else -> 'X'
            }
        }
        'Y' -> {
            when(theres) {
                'A' -> 'X'
                'B' -> 'Y'
                'C' -> 'Z'
                else -> 'X'
            }
        }
        'Z' -> {
            when(theres) {
                'A' -> 'Y'
                'B' -> 'Z'
                'C' -> 'X'
                else -> 'X'
            }
        }
        else -> 'X'
    }

     totalScore += when(newYours) {
         'X' -> 1
         'Y' -> 2
         'Z' -> 3
         else -> 0
    }

    // rock paer scissors

    if (theres == 'A' && newYours == 'X') totalScore += 3
    if (theres == 'A' && newYours == 'Y') totalScore += 6
    if (theres == 'A' && newYours == 'Z') totalScore += 0
    if (theres == 'B' && newYours == 'X') totalScore += 0
    if (theres == 'B' && newYours == 'Y') totalScore += 3
    if (theres == 'B' && newYours == 'Z') totalScore += 6
    if (theres == 'C' && newYours == 'X') totalScore += 6
    if (theres == 'C' && newYours == 'Y') totalScore += 0
    if (theres == 'C' && newYours == 'Z') totalScore += 3

    println ("totlaScore = $totalScore")

    return totalScore

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