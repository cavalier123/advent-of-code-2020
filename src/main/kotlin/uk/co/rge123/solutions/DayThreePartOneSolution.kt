package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

/**
 * This is a hacky solution and I'm not proud of it.
 */

fun main() {
    val filename = "DayThreeData"
    val solution = solveItDayThreePartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

var grid = Array(3000){i -> Array(3000) {j -> 0}}

fun solveItDayThreePartOne(lines: List<String>): Int {
    var row = 0
    var column = 0

    for (line in lines) {
        val repeatedLine = line.repeat(80)
        print("Line = $row:")
        println (repeatedLine)
        column = 0;
        for (char in repeatedLine) {
            if (char == '#') {
                grid[column][row] = 1
            }
            ++column
        }
        row++
    }

    // 323
    var curRow = 0
    var curCol = 0
    var treeCount = 0

    while (curRow <= 323) {
        curRow += 2
        curCol += 1
        if (grid[curCol][curRow] == 1) ++ treeCount
    }

    return treeCount
}