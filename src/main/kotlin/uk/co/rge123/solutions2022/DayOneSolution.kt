package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main (args: Array<String>) {

    val numbers = splitFileToLines("dayOne").map {if (it.isEmpty()) -1 else it.toInt()  }

    var sum = 0;

    val listSums = MutableList(0) {0}

    for (number in numbers) {
        println(number)
        if (number == -1) {
           listSums.add(sum)
            sum = 0
        } else {
            sum += number;
        }
    }
    listSums.add(sum)

    for (num in listSums) {
        println (num)
    }

    listSums.sortDescending();

    val topThree = listSums[0] + listSums[1] + listSums [2];

    println ("maxSum = $topThree")
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