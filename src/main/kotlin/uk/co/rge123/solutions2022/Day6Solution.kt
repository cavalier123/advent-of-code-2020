package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main (args: Array<String>) {

    val line = splitFileToLines("daySix").first()

    println("$line")

    var pos = 0

    for (i in 0 .. (line.length - 14)) {
        println(i)
        val word = line.substring(i, i + 14)
        println("$word")

        val letterSet = word.toCharArray().toSet()
        if (letterSet.size == 14) {
            pos = i + 14
            break
        }
    }
    println ("position = $pos")
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