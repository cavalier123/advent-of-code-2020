package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Integer.min
import kotlin.math.max

const val NUM_COLUMNS = 99
const val NUM_ROWS = 99

typealias GridType = Array<Array<Int>>

fun main() {
    val filename = "dayEight"
    val solution = solveItDayElevenPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayElevenPartOne(lines: List<String>): Int {

    println("numlines = ${lines.size}")
    println("len = ${lines[0].length}")

    var treeGrid = Array(NUM_COLUMNS) { Array(NUM_ROWS) { 0 } }

    for ((row, line) in lines.withIndex()) {
        //println("$row $line")
        for ((column, char) in line.withIndex()) {
            treeGrid[column][row] = char.digitToInt()
        }
    }

    for (row in 0 until NUM_ROWS) {
        for (column in 0 until NUM_COLUMNS) {
            val thing = treeGrid[column][row]
            print("$thing")
        }
        println()
    }

    var totalVisible = 0
    var maxScore = 0

    for (column in 1 until NUM_COLUMNS - 1) {
        for (row in 1 until NUM_ROWS - 1) {
            val curHeight = treeGrid[column][row]

            val thing = treeGrid[column][row]
            println("$column $row $thing")

            var score1 = 0
            for (curCol in column - 1 downTo 0) {
                if (treeGrid[curCol] [row] < curHeight) ++score1
                else {
                    ++score1
                    break;
                }
            }
            var score2 = 0
            for (curCol in column + 1 .. NUM_COLUMNS - 1) {
                if (treeGrid[curCol] [row] < curHeight) ++score2
                else {
                    ++score2
                    break;
                }
            }
            var score3 = 0
            for (curRow in row - 1 downTo 0) {
                if (treeGrid[column] [curRow] < curHeight) ++score3
                else {
                    ++score3
                    break;
                }
            }
            var score4 = 0
            for (curRow in row + 1 ..NUM_ROWS - 1) {
                if (treeGrid[column] [curRow] < curHeight) ++score4
                else {
                    ++score4
                    break;
                }
            }
            val totalScore = score1 * score2 * score3 * score4
            maxScore = max(maxScore, totalScore)
        }
    }

    return maxScore
}