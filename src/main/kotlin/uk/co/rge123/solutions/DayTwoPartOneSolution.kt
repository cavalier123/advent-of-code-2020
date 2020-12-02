package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayTwoData"
    val solution = solveItDayTwoPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayTwoPartOne(lines: List<String>)= lines.filter{getPasswordInfo(it).isValid()}.count()

fun getPasswordInfo(input: String): PasswordInfo {
    val words = input.split(" ")
    val rangeNumbers = words[0].split("-")
    return PasswordInfo(rangeNumbers[0].toInt(), rangeNumbers[1].toInt() ,words[1][0], words[2])
}

data class PasswordInfo(val rangeStart: Int, val rangeEnd: Int, val checkedChar: Char, val password: String) {
    fun isValid() = password.count{it == checkedChar} in rangeStart .. rangeEnd
    fun isValidPartTwo() = (password[rangeStart -1] == checkedChar).xor(password[rangeEnd -1] == checkedChar)
}