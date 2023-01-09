package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

enum class HEADING {N, S, E, W;
    companion object {
        fun getHeading(char: Char): HEADING {
            return when (char) {
                '>' -> HEADING.E
                '^' -> HEADING.N
                '<' -> HEADING.W
                'v' -> HEADING.S
                else -> HEADING.S
            }
        }
        fun getNewPos(curPoint: Point, heading: HEADING): Point {
            return when(heading) {
                N -> if(curPoint.y == 1) Point(curPoint.x, NR - 2) else Point(curPoint.x, curPoint.y - 1)
                E -> if(curPoint.x == NC - 2) Point(1, curPoint.y) else Point(curPoint.x + 1, curPoint.y)
                S -> if(curPoint.y == NR - 2) Point(curPoint.x, 1) else Point(curPoint.x, curPoint.y + 1)
                W -> if(curPoint.x == 1) Point(NC - 2, curPoint.y) else Point(curPoint.x - 1, curPoint.y)
            }
        }
    }
}

fun getAdjacentPoints(current: Point): Set<Point> {

    val adjacent = mutableSetOf<Point>()
    for (inc in setOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1)    )) {
       if ( ((current.x + inc.x) == startPoint.x && (current.y + inc.y) == startPoint.y) ||
           ((current.x + inc.x) == endPoint.x && (current.y + inc.y) == endPoint.y) ||
           (current.x + inc.x in 1 .. NC -2 && current.y + inc.y in 1 .. NR - 2))
       {
            adjacent.add(Point(current.x + inc.x, current.y + inc.y))
        }
    }
    return adjacent
}

data class Storm (var pos: Point, val heading: HEADING)

val stormSystem = mutableListOf<Storm>()
val stormPoints = mutableSetOf<Point>()
var visited = mutableSetOf<Point>()

//private const val NR = 6
//private const val NC = 8

private const val NR = 27
private const val NC = 122

private var startPoint = Point(1, 0)
private var endPoint = Point (NC - 2, NR - 1)

fun main() {
    val filename = "day24"
    val solution = solveItDay24PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun printMap() {
    for (rowIndex in 0 .. NR -1) {
        for (colIndex in 0 .. NC -1) {
            if (visited.contains(Point(colIndex, rowIndex))) {
                print("E")
            } else if (rowIndex == 0 || rowIndex == NR - 1 || colIndex == 0 || colIndex == NC -1) {
                print("#")
            } else if (stormPoints.contains(Point(colIndex, rowIndex))) {
                print("S")
            } else {
                print(" ")
            }
        }
        println()
    }
}

fun solveItDay24PartOne2022(lines: List<String>): Int {

    println ("num rows = ${lines.size}")
    println ("num cols = ${lines[0].length}")

    for ((rowIndex, line) in lines.withIndex()) {
        for ((colIndex, char) in line.withIndex()) {
            if ("<>^v".contains(char)) {
                val heading = HEADING.getHeading(char)
                val storm = Storm(Point(colIndex, rowIndex), heading)
                stormSystem.add(storm)
            }
        }
    }

    stormPoints.removeAll { true }
    for (storm in stormSystem) {
        stormPoints.add(storm.pos)
    }

    visited.add(startPoint)

    printMap()

    for (storm in stormSystem) {
        println(storm)
    }

    var minute = 0
    var numTimesAtEndpoint = 0
    do {
        minute++
        println(minute)
        // move storm system
        for (storm in stormSystem) {
            storm.pos = HEADING.getNewPos(storm.pos, storm.heading)
        }
        // get storm points
        stormPoints.removeAll { true }
        for (storm in stormSystem) {
            stormPoints.add(storm.pos)
        }
        // expand visited to all adjacent points that do not have a storm
        val newVisited = visited.toMutableSet()
        for (point in visited) {
            //println("Visited = $point")
            val adj = getAdjacentPoints(point)
            //println ("adj = $adj")
            newVisited.addAll(adj)
        }
        // remove points from visited if storm is currently there
        visited = newVisited.minus(stormPoints).toMutableSet()
        //println ("${visited.size}")
        //println ("$visited")

        printMap()

        if (visited.contains(endPoint)) {
            numTimesAtEndpoint++
            if (numTimesAtEndpoint < 3) {
                var tempPoint = Point(0,0)
                tempPoint = startPoint
                startPoint = endPoint
                endPoint = tempPoint
                visited.removeAll{true}
                visited.add(startPoint)
            }
        }

    } while (numTimesAtEndpoint < 3)

    return minute
}
