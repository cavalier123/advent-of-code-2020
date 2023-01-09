package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "day23"
    val solution = solveItDay23PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

val elves = mutableSetOf<Point>()
val putativeMoves = mutableMapOf<Point, Pair<Int, Point>>()

fun solveItDay23PartOne2022(lines: List<String>): Int {

    println("num rows = ${lines.size}")
    println("num cols = ${lines[0].length}")

    for ((rowIndex, line) in lines.withIndex()) {
        for ((colIndex, char) in line.withIndex()) {
            if (char == '#') {
                elves.add(Point(colIndex, rowIndex))
            }
        }
    }

    var round = 0
    var numMoved = 0

    do {
        println(round)
        numMoved = 0

        putativeMoves.clear()
        // get putative moves

        for (elf in elves) {
            val putativeMove = getPutativeMove(elf, round)
            if (putativeMove != elf) {
                val curMove = putativeMoves[putativeMove]
                if (curMove == null) {
                    putativeMoves[putativeMove] = Pair(1, elf)
                } else {
                    putativeMoves[putativeMove] = Pair(2, elf)
                }
            }
        }
        // make putative moves
        for (entry in putativeMoves) {
            if (entry.value.first == 1) {
                elves.remove(entry.value.second)
                elves.add(entry.key)
                ++numMoved
            }
        }
//        for (elf in elves) {
//            println (elf)
//        }

        round ++
    } while (numMoved > 0)

    val elvesMinX = elves.minOf {it.x}
    val elvesMaxX = elves.maxOf {it.x}

    val elvesMinY = elves.minOf {it.y}
    val elvesMaxY = elves.maxOf {it.y}

    val gridSize = (elvesMaxX - elvesMinX + 1) * (elvesMaxY - elvesMinY + 1)
    val freeSpaces = gridSize - elves.size

    return round + 1
}

fun getAdjPoints(current: Point): Set<Point> {
    val adjacent = mutableSetOf<Point>()
    for (inc in setOf(
            Point(-1, 0),
            Point(-1, -1),
            Point(-1, 1),

            Point(0, -1),
            Point(0, 1) ,

            Point(1, -1),
            Point(1, 1),
            Point(1, 0)
    )) {
        adjacent.add(Point(current.x + inc.x, current.y + inc.y))
    }
    return adjacent
}

fun getAdjPointsNorth(current: Point): Set<Point> {
    val adjacent = mutableSetOf<Point>()
    for (inc in setOf(
        Point(0, -1),
        Point(-1, -1),
        Point(1, -1)
    )) {
        adjacent.add(Point(current.x + inc.x, current.y + inc.y))
    }
    return adjacent
}

fun getAdjPointsEast(current: Point): Set<Point> {
    val adjacent = mutableSetOf<Point>()
    for (inc in setOf(
        Point(1, -1),
        Point(1, 1),
        Point(1, 0)
    )) {
        adjacent.add(Point(current.x + inc.x, current.y + inc.y))
    }
    return adjacent
}

fun getAdjPointsSouth(current: Point): Set<Point> {
    val adjacent = mutableSetOf<Point>()
    for (inc in setOf(
        Point(-1, 1),
        Point(0, 1) ,
        Point(1, 1)
    )) {
        adjacent.add(Point(current.x + inc.x, current.y + inc.y))
    }
    return adjacent
}

fun getAdjPointsWest(current: Point): Set<Point> {
    val adjacent = mutableSetOf<Point>()
    for (inc in setOf(
        Point(-1, 0),
        Point(-1, -1),
        Point(-1, 1)
    )) {
        adjacent.add(Point(current.x + inc.x, current.y + inc.y))
    }
    return adjacent
}


//During the first half of each round, each Elf considers the eight positions adjacent to themself.
// If no other Elves are in one of those eight positions, the Elf does not do anything during this round.
// Otherwise, the Elf looks in each of four directions in the following order and proposes moving one step
// in the first valid direction:
//
//If there is no Elf in the N, NE, or NW adjacent positions, the Elf proposes moving north one step.
//If there is no Elf in the S, SE, or SW adjacent positions, the Elf proposes moving south one step.
//If there is no Elf in the W, NW, or SW adjacent positions, the Elf proposes moving west one step.
//If there is no Elf in the E, NE, or SE adjacent positions, the Elf proposes moving east one step.

fun getNumAdjacentElves(curPoint: Point) = elves.intersect(getAdjPoints(curPoint)).size

fun northFun(curPoint: Point) = if (getAdjPointsNorth(curPoint).intersect(elves).isEmpty()) Point(curPoint.x, curPoint.y - 1) else curPoint
fun eastFun(curPoint: Point) = if (getAdjPointsEast(curPoint).intersect(elves).isEmpty()) Point(curPoint.x + 1, curPoint.y) else curPoint
fun southFun(curPoint: Point) = if (getAdjPointsSouth(curPoint).intersect(elves).isEmpty()) Point(curPoint.x, curPoint.y + 1) else curPoint
fun westFun(curPoint: Point) = if (getAdjPointsWest(curPoint).intersect(elves).isEmpty()) Point(curPoint.x - 1, curPoint.y) else curPoint

val ruleList = listOf<(Point) -> Point>(::northFun, ::southFun, ::westFun, ::eastFun)

fun getPutativeMove(curPoint: Point, round: Int): Point {
    if (getNumAdjacentElves(curPoint) == 0) return curPoint
    for (ruleIndex in 0..3) {
        val curRule = ruleList[(ruleIndex + round) % 4]
        val newPoint = curRule(curPoint)
        if (newPoint != curPoint) return newPoint
    }
    return curPoint
}
