package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.solutions.GridState.Companion.toState
import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.util.*

fun main() {
    val filename = "DaySeventeenData"
    val solution = `solveItDaySeventeenPartOne`(splitFileToLines(filename))
    print ("Solution is $solution")
}

typealias Point = MultiComponent

data class MultiComponent(val first: Int, val second: Int, val third: Int, val fourth: Int)

enum class GridState(var rep: Char) {
    INACTIVE('.'), ACTIVE('#');
    companion object {
        fun toState(char: Char): GridState {
            return values().find { it.rep == char } ?: INACTIVE
        }
    }
}

fun solveItDaySeventeenPartOne(lines: List<String>): Int {

    var firstHyperGrid = mutableSetOf<Point>()
    var secondHyperGrid = mutableSetOf<Point>()

    for ((row, line) in lines.withIndex()) {
        //println("$row $line")
        for ((column, char) in line.withIndex()) {
            if (toState(char) == GridState.ACTIVE) {
                secondHyperGrid.add(Point(column, row, 0, 0))
            }
        }
    }

    for (point in firstHyperGrid) {
        println(point)
    }

    println("---------")

//    val adjs = getPointsNear(Triple(2,2,2, 2))
//    for (adj in adjs) {
//        println(adj)
//    }

    //println(Date().time)
    var genCount = 0
    do {
        // wap grids
        firstHyperGrid = secondHyperGrid.also {secondHyperGrid = firstHyperGrid}
        // clear second grid
        secondHyperGrid.clear()
        populateHyperGrid(firstHyperGrid, secondHyperGrid)
        ++ genCount
    } while (genCount < 6)

    //println(Date().time)

    return secondHyperGrid.size
}

//If a cube is active and exactly 2 or 3 of its neighbors are also active, the cube remains active. Otherwise, the cube becomes inactive.
//If a cube is inactive but exactly 3 of its neighbors are active, the cube becomes active. Otherwise, the cube remains inactive.


fun populateHyperGrid(firstHyperGrid: Set<Point>, secondHyperGrid: MutableSet<Point>) {

    for (point in firstHyperGrid) {
        if (countActiveNeighbours(point, firstHyperGrid) in (2..3)) {
            secondHyperGrid.add(point)
        }
    }

    val inactivePointsToCheck = generateAdajacentPoints(firstHyperGrid.toList(), GridState.INACTIVE, firstHyperGrid)

    for (point in inactivePointsToCheck) {
        if (countActiveNeighbours(point, firstHyperGrid) == 3) {
            secondHyperGrid.add(point)
        }
    }
}

fun getPointsNear(point: Point): List<Point> {
    return (-1 .. 1).flatMap{ d -> (-1 .. 1).map { a -> d to a} }
            .flatMap{pair -> (-1 .. 1).map { z -> Triple(pair.first, pair.second, z)} }
            .flatMap{triple -> (-1 .. 1).map { w -> Point(triple.first, triple.second, triple.third, w)} }
            .filter {it != Point(0,0,0, 0)}
            .map {Point(it.first + point.first, it.second + point.second,
                    it.third + point.third, it.fourth + point.fourth)}
}
// .filter {it != Triple(0,0,0)}


fun generateAdajacentPoints(points: List<Point>, state: GridState, activePoints: Set<Point>): Set<Point> {

    val ret = mutableSetOf<Point>()

    for (point in points) {
        val adjacents = getPointsNear(point)
        for (adj in adjacents) {
            if (adj in activePoints && state == GridState.ACTIVE) {
                ret.add(adj)
            } else if (!(adj in activePoints) && state == GridState.INACTIVE) {
                ret.add(adj)
            }
        }
    }
    return ret
}

fun countActiveNeighbours(point: Point, activePoints: Set<Point>): Int {
    return generateAdajacentPoints(listOf(point), GridState.ACTIVE, activePoints).size
}



//fun numOccupiedAdjacent(grid: GridType, across: Int, down: Int): Int {
//    return (-1 .. 1).flatMap{ l -> (-1 .. 1).map { r -> l to r} }
//            .filter { (it != Pair(0,0)) && checkOccupiedInDir(grid, it.first, it.second, across, down)}
//            .count()
//}