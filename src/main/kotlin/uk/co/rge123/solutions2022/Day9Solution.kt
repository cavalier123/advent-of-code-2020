package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Math.abs

data class Point (var x: Int, var y: Int)

fun main() {
    val filename = "dayNine"
    val solution = solveItDayNinePartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayNinePartOne(lines: List<String>): Int {

    val posHead = Point(0, 0)
    val posTails = Array(9) { Point (0, 0) }

    val visitedPoints = mutableSetOf<Point>()
    visitedPoints.add(Point(0,0))

    for (line in lines) {
        val words = line.split(" ")
        val dir = words[0].toCharArray().first()
        val steps = words[1].toInt()
        println("dir=$dir, steps=$steps")
        for (moves in 1 .. steps) {
            if (dir == 'U') {
                posHead.y += 1
            } else if (dir == 'D') {
                posHead.y -= 1
            } else if (dir == 'L') {
                posHead.x -= 1
            } else if (dir == 'R') {
                posHead.x += 1
            }
            moveTail (posHead, posTails[0])
            for (tails in 0..7) {
                moveTail(posTails[tails], posTails[tails + 1])
            }

            visitedPoints.add(Point(posTails[8].x, posTails[8].y))
        }
    }

    return visitedPoints.size

}

fun moveTail (posHead: Point, posTail: Point) {

    if (adjacent(posHead, posTail)) {
        println ("adjacent")
    } else if (posHead.x == posTail.x) {
        if (posHead.y == posTail.y + 2) {
            posTail.y += 1
        } else if (posHead.y == posTail.y - 2) {
            posTail.y -= 1
        }
    } else if (posHead.y == posTail.y) {
        if (posHead.x == posTail.x + 2) {
            posTail.x += 1
        } else if (posHead.x == posTail.x - 2) {
            posTail.x -= 1
        }
    } else { // diagonal step
        if (posHead.x > posTail.x) {
            ++posTail.x
        } else if (posHead.x < posTail.x) {
            --posTail.x
        }
        if (posHead.y > posTail.y) {
            ++posTail.y
        } else if (posHead.y < posTail.y) {
            --posTail.y
        }
    }

    println ("Head = $posHead")
    println ("Tail = $posTail")

}

fun adjacent (p1: Point, p2: Point): Boolean {
    val dx = abs(p1.x - p2.x)
    val dy = abs(p1.y - p2.y)
    return (dx in 0..1 && dy in 0 .. 1)
}

