package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Integer.min
import kotlin.math.max

const val NUM_C = 154
const val NUM_R = 41

typealias GridType2 = Array<Array<Char>>

fun main() {
    val filename = "day12"
    val solution = solveItDayTwelvePartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayTwelvePartOne(lines: List<String>): Int {

    println("numlines = ${lines.size}")
    println("len = ${lines[0].length}")

    var startPoint = Point(0,0)
    var endPoint = Point(0,0)

    val visited = mutableSetOf<Point>()
    var frontier = mutableSetOf<Point>()
    var newFrontier = mutableSetOf<Point>()

    var map = Array(NUM_C) { Array(NUM_R) { 'a' } }

    for ((row, line) in lines.withIndex()) {
        //println("$row $line")
        for ((column, char) in line.withIndex()) {
            map[column][row] = char
            if (char == 'S') {
                endPoint = Point(column, row)
            }
            if (char == 'E') {
                startPoint = Point(column, row)
            }
        }
    }

    println ("Found a jobby")

    var distance = 0
    frontier.add(startPoint)
    visited.add(startPoint)

    var finished = false

    do {
        println ("run = $distance")

        for (point in frontier) {
            val pointsToAdd = getAdjacentFrontierPoints(point, map, visited)
            println("Adding")
            for (point in pointsToAdd) {
                println(point)
            }
            newFrontier.addAll(pointsToAdd)
            visited.addAll(pointsToAdd)
        }

        frontier = newFrontier.toMutableSet()
        newFrontier.removeAll { true }

        ++distance

        println("Checking")
        for (fp in frontier) {
            println(fp)
            println(map[fp.x][fp.y])
            if (map[fp.x][fp.y] == 'S' || map[fp.x][fp.y] == 'a') {
                println("bingo")
                finished = true
                break
            }
        }

    } while (!finished)

    println ("Start = $startPoint")
    println ("Endpoint = $endPoint")

    return distance
}

fun visited(cur: Point, visited: Set<Point>): Boolean {
    return visited.contains(cur)
}

fun getAdjacentFrontierPoints(current: Point, grid: GridType2, visited: Set<Point>): Set<Point> {

    val curHeight = if (grid[current.x][current.y] == 'E') 'z'.code else grid[current.x][current.y].code
    val adjacent = mutableSetOf<Point>()

    for (inc in setOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1)    )) {
        if ((current.x + inc.x) in 0 until NUM_C && (current.y + inc.y) in 0 until NUM_R) {
            if (!visited(Point(current.x + inc.x, current.y + inc.y), visited)) {
                val newHeight = if (grid[current.x +inc.x][current.y +inc.y] == 'S') 'a'.code else grid[current.x + inc.x][current.y + inc.y].code
                if (newHeight >= curHeight || newHeight == curHeight - 1) {
                    adjacent.add(Point(current.x + inc.x, current.y + inc.y))
                }
            }
        }
    }

    return adjacent
}

