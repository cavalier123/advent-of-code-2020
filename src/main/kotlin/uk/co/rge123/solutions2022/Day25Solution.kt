package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.math.BigInteger

fun main() {
    val filename = "day25"
    val solution = convertToSnafu(solveItDay25PartOne2022(splitFileToLines(filename)))
    print ("Solution is $solution")
}

fun solveItDay25PartOne2022(lines: List<String>): Long {

    val snafu = convertToSnafu(314159265)
    println("$snafu")
//    addOne(snafu, 0)
//    for (digit in snafu) {
//        print("$digit ")
//    }
//    println()

    val answer = lines.sumOf{getValue(it)}
    return answer
}

fun pow(n: Long, exp: Int): Long{
    return BigInteger.valueOf(n).pow(exp).toLong()
}

fun snafuCharToNum(char: Char): Int {
    return when (char) {
        '0' -> 0
        '1' -> 1
        '2' -> 2
        '-' -> -1
        '=' -> -2
        else -> 0
    }
}

fun numToSnafuChar(char: Int): Char {
    return when (char) {
        0 -> '0'
        1 -> '1'
        2 -> '2'
        -1 -> '-'
        -2 -> '='
        else -> '0'
    }
}

fun getValue(snafu: String): Long {
    var sum = 0L
    for((index, char) in snafu.toCharArray().reversed().withIndex()) {
        val powerOf5 = pow(5, index)
        sum += snafuCharToNum(char) * powerOf5
    }
    return sum
}

fun convertToSnafu(num: Long): String {

    var indexBiggerThan = 1
    while (pow(5, indexBiggerThan) <= num) {
        ++indexBiggerThan
    }
    val indexSmallerThan = indexBiggerThan -1

    var snafuNum = mutableListOf<Int>()
    var remainder = num
    for (index in indexSmallerThan downTo 0) {
        val powerOf5 = pow(5, index)
        val num = remainder / powerOf5
        snafuNum.add(num.toInt())
        remainder -= (num * powerOf5)
    }

    snafuNum = snafuNum.reversed().toMutableList()
    for (digit in snafuNum) {
        print("$digit ")
    }
    println()

    var found = false

    do {
        found = false
        var newSnafuNum = mutableListOf<Int>()
        for ((index, digit) in snafuNum.withIndex()) {
            if (digit == 3) {
                newSnafuNum = snafuNum.toMutableList()
                newSnafuNum[index] = -2
                addOne(newSnafuNum, index + 1)
                found = true
                break
            } else if (digit == 4) {
                newSnafuNum = snafuNum.toMutableList()
                newSnafuNum[index] = -1
                addOne(newSnafuNum, index + 1)
                found = true
                break
            }
        }
        if (found) snafuNum = newSnafuNum.toMutableList()
    } while(found)

    return String(snafuNum.map {numToSnafuChar(it)}.reversed().toCharArray())
}

fun addOne(snafuNum: MutableList<Int>, pos: Int) {
    if (pos == snafuNum.size) {
        snafuNum.add(1)
    } else {
        val num = snafuNum[pos]
        if (num < 4) {
            snafuNum[pos] = snafuNum[pos] + 1
        } else {
            snafuNum[pos] = 0
            if (pos == snafuNum.size - 1) {
                snafuNum.add(1)
            } else {
                addOne(snafuNum, pos + 1)
            }
        }
     }
}




