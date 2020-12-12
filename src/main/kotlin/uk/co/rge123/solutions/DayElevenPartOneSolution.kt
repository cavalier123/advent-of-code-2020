package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.util.*

fun main() {
    val filename = "DayElevenData"
    val solution = solveItDayElevenPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

enum class State(var rep: Char) {
    FLOOR('.'), EMPTY_SEAT('L'), FULL_SEAT('#');
    companion object {
        fun toState(char: Char): State {
            return values().find { it.rep == char } ?: State.FLOOR
        }
    }
}

const val NUM_COLUMNS = 93
const val NUM_ROWS = 90

typealias GridType = Array<Array<State>>

fun solveItDayElevenPartOne(lines: List<String>): Int {

    var firstGrid = Array(NUM_COLUMNS) {Array(NUM_ROWS) {State.EMPTY_SEAT} }
    var secondGrid = Array(NUM_COLUMNS) {Array(NUM_ROWS) {State.EMPTY_SEAT} }

    for ((row, line) in lines.withIndex()) {
        //println("$row $line")
        for ((column, char) in line.withIndex()) {
            secondGrid[column][row] = State.toState(char)
        }
    }

    println(Date().time)
    var genCount = 0
    do {
        firstGrid = secondGrid.also {secondGrid = firstGrid}
        ++ genCount
    } while (populateGrid(firstGrid, secondGrid))
    println(Date().time)
    println("$genCount")

    return countOccupied(firstGrid)
}

//If a seat is empty (L) and there are no occupied seats adjacent to it, the seat becomes occupied.
//If a seat is occupied (#) and four or more seats adjacent to it are also occupied, the seat becomes empty.
//Otherwise, the seat's state does not change.

fun populateGrid(firstGrid: GridType, secondGrid: GridType): Boolean {
    var changed = false
    for (downIndex in 0 until NUM_ROWS) {
        for (acrossIndex in 0 until NUM_COLUMNS) {
            val curState = firstGrid[acrossIndex][downIndex]
            if (curState == State.EMPTY_SEAT && numOccupiedAdjacent(firstGrid, acrossIndex, downIndex) == 0) {
                secondGrid[acrossIndex][downIndex] = State.FULL_SEAT
                changed = true
            } else if (curState == State.FULL_SEAT && numOccupiedAdjacent(firstGrid, acrossIndex, downIndex) >= 5) {
                secondGrid[acrossIndex][downIndex] = State.EMPTY_SEAT
                changed = true
            } else {
                secondGrid[acrossIndex][downIndex] = firstGrid[acrossIndex][downIndex]
            }
        }
    }
    return changed
}

fun numOccupiedAdjacent(grid: GridType, across: Int, down: Int): Int {
    return (-1 .. 1).flatMap{ l -> (-1 .. 1).map { r -> l to r} }
            .filter { (it != Pair(0,0)) && checkOccupiedInDir(grid, it.first, it.second, across, down)}
                .count()
}

fun checkOccupiedInDir(grid: GridType, acrossChange: Int, downChange: Int, across: Int, down: Int) :Boolean {

    var curAcross = across + acrossChange
    var curDown = down + downChange

    while (curAcross in 0 until NUM_COLUMNS && curDown in 0 until NUM_ROWS && grid[curAcross][curDown] != State.EMPTY_SEAT) {
        if (grid[curAcross][curDown] == State.FULL_SEAT) {
            return true
        }
        curAcross += acrossChange
        curDown += downChange
    }

    return false
}

fun countOccupied(grid: GridType) = grid.flatten().count{ it == State.FULL_SEAT}

fun printGrid(firstGrid: GridType) {

    for (downIndex in 0 until NUM_ROWS) {
        for (acrossIndex in 0 until NUM_COLUMNS) {
            print(firstGrid[acrossIndex][downIndex].rep)
        }
        println()
    }
}
