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

fun solveItDayThreePartOne(lines: List<String>): Int {

    var grid = Array(31){i -> Array(400) {j -> 0}}
    
    for ((row, line) in lines.withIndex()) {
        for ((column, char) in line.withIndex()) {
            if (char == '#') {
                grid[column][row] = 1
            }
        }
    }

    var curRow = 0
    var curCol = 0
    var treeCount = 0

    while (curRow <= 323) {
        curRow += 1
        curCol += 3
        if (grid[curCol % 31][curRow] == 1) ++ treeCount
    }

    return treeCount
}