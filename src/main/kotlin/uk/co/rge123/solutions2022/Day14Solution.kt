package main.kotlin.uk.co.rge123.solutions2022
import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Integer.min
import java.lang.Integer.max


fun main() {
    val filename = "day14"
    val solution = solveItDayFourteenPartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
    //val solution2 = solveItDayFourteenPartTwo2022(splitFileToLines(filename))
    //print ("Solution is $solution2")
}

// 61,114 -> 461,113 -> 461,114 -> 463,

fun solveItDayFourteenPartOne2022(lines: List<String>): Int {

    val rocks = mutableSetOf<Point>()
    var printIt = true

    var maxY = 0

    for (line in lines) {
        val words = line.split("->")
        var prevPoint: Point? = null
        var curPoint: Point?
        for (word in words) {
            val pair = word.trim().split(",")
            val pairx = pair[0].toInt()
            val pairy = pair[1].toInt()
            curPoint = Point(pairx, pairy)
            if (prevPoint != null) {
            // 60,114 -> 60,116
                if (curPoint.x == prevPoint.x) {
                    val starty: Int = min(curPoint.y, prevPoint.y)
                    val endy: Int = max(curPoint.y, prevPoint.y)
                    for (y in starty .. endy) {
                        rocks.add(Point(curPoint.x, y))
                        maxY = max (maxY, y)
                        if (printIt) println("${curPoint.x}, $y")
                    }
                } else {
                    val startx: Int = min(curPoint.x, prevPoint.x)
                    val endx: Int = max(curPoint.x, prevPoint.x)
                    for (x in startx..endx) {
                        rocks.add(Point(x, curPoint.y))
                        maxY = max (maxY, curPoint.y)
                        if (printIt) println("$x, ${curPoint.y}")
                    }
                }
            }
            printIt = false
            prevPoint = curPoint
        }
    }

    println ("maxy = $maxY")

    var numBlockedGrains = 0
    //var isBlocked = false
    do {
        println(numBlockedGrains)
        dropSand2(rocks)
        ++numBlockedGrains
    } while (!rocks.contains(Point(500,0)))
    return numBlockedGrains
}

fun dropSand(rocks: MutableSet<Point>): Boolean {

    val curPoint = Point(500, 0)
    var numSteps = 0
    var blocked = false
    do {
        // try straight down
        if (!rocks.contains(Point(curPoint.x, curPoint.y + 1))) {
            curPoint.y ++
        // diagonally left
        } else if (!rocks.contains(Point(curPoint.x - 1, curPoint.y + 1))) {
            curPoint.x--
            curPoint.y++
        // diagonally right
        } else if (!rocks.contains(Point(curPoint.x + 1, curPoint.y + 1))) {
            curPoint.x++
            curPoint.y++
        } else { // we can't move
            rocks.add(Point(curPoint.x, curPoint.y))
            blocked = true
        }
        ++numSteps
        //println ("$blocked")
        val below = blocksBelow(curPoint, rocks)
        //println("$below")
        //println("$curPoint")
    } while (!blocked && blocksBelow(curPoint, rocks) > 0)
    return blocked
}

fun dropSand2(rocks: MutableSet<Point>): Boolean {

    val curPoint = Point(500, 0)
    var numSteps = 0
    var blocked = false
    do {
        // try straight down
        if (curPoint.y == 170) {
            rocks.add(Point(curPoint.x, curPoint.y))
            blocked = true
        } else if (!rocks.contains(Point(curPoint.x, curPoint.y + 1))) {
            curPoint.y ++
            // diagonally left
        } else if (!rocks.contains(Point(curPoint.x - 1, curPoint.y + 1))) {
            curPoint.x--
            curPoint.y++
            // diagonally right
        } else if (!rocks.contains(Point(curPoint.x + 1, curPoint.y + 1))) {
            curPoint.x++
            curPoint.y++
        } else { // we can't move
            rocks.add(Point(curPoint.x, curPoint.y))
            blocked = true
        }
        ++numSteps
        //println ("$blocked")
        val below = blocksBelow(curPoint, rocks)
        //println("$below")
        //println("$curPoint")
    } while (!blocked)
    return blocked
}

fun blocksBelow (curPoint: Point, rocks: Set<Point>): Int {
    return rocks.count{it.x == curPoint.x && it.y > curPoint.y}
}

