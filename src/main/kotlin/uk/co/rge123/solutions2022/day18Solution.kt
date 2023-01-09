package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Math.abs

fun main() {
    val filename = "day18"
    val solution = solveItDay18PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

//Valve KF has flow rate=7; tunnels lead to valves YB, RZ, IK, PG, VI

fun solveItDay18PartOne2022(lines: List<String>): Int {

    val points = mutableSetOf<Triple<Int, Int, Int>>()

    for (line in lines) {
        val words = line.split(",")
        val triple = Triple(words[0].toInt(), words[1].toInt(), words[2].toInt())
        //println(triple)
        points.add(triple)
    }

    val maxX = points.maxOf {it.first}
    val maxY = points.maxOf {it.second}
    val maxZ = points.maxOf {it.third}

    println ("Maxes = $maxX, $maxY, $maxZ")

    val minX = points.minOf {it.first}
    val minY = points.minOf {it.second}
    val minZ = points.minOf {it.third}

    println ("Mins = $minX, $minY, $minZ")

    for (x in 0 .. 20) {
        for (y in 0..20) {
            for (z in 0..20) {
                val curPoint = Triple(x,y,z)
                if (isClearExteriorPoint(curPoint, points)) {
                    println("Sploge $curPoint")
                }
            }
        }
    }

    val exPoints = getExteriorPointsFromClearPoint(Triple(10, 9, 18), points)

    println("sizey wizey = ${exPoints.count()}")

    var totalNumExposedFaces = 0
    for (point in points) {
        val adjacentPoints = generateAdjacentPoints(point)
        var numExposedFaces = 0
        for (curPoint in adjacentPoints) {
            if (!points.contains(curPoint) && exPoints.contains(curPoint)) {
                numExposedFaces++
            }
        }
        totalNumExposedFaces += numExposedFaces
    }

    // (10, 9, 18)

//    for (x in 1 .. 3) {
//        for (y in 0 ..3) {
//            for (z in 3 .. 6) {
//                val curPoint = Triple(x,y,z)
//                println("curPoint = $curPoint")
//                if (isInteriorPoint(curPoint, points)) {
//                    println("found $curPoint")
//                }
////                if (isClearExteriorPoint(curPoint, points)) {
////                    println("is clear exterior")
////                }
////                if (pathExistsToClearPoint(curPoint, points)) {
////                    println("path exists to clear")
////                }
//            }
//        }
//    }


    return totalNumExposedFaces
}

fun adjacent(p1: Triple<Int, Int, Int>, p2: Triple<Int, Int, Int>): Boolean {
    return (  (p1.first == p2.first && p1.second == p2.second && abs(p1.third - p2.third) == 1) ||
            (p1.first == p2.first && p1.third == p2.third && abs(p1.second - p2.second) == 1) ||
    (p1.second == p2.second && p1.third == p2.third && abs(p1.first - p2.first) == 1))
}

fun numAdjacentPoints(p: Triple<Int, Int, Int>, universe: Set<Triple<Int, Int, Int>>): Int {
    val adjacentPoints = generateAdjacentPoints(p)
    var numAdjacent = 0
    for (point in adjacentPoints) {
        if (universe.contains(point)) {
            ++numAdjacent
        }
    }
    return numAdjacent
}


fun generateAdjacentPoints(p: Triple<Int, Int, Int>): List<Triple<Int,Int,Int>> {
    val pList = mutableListOf<Triple<Int,Int,Int>>()
    pList.add(Triple(p.first, p.second, p.third + 1))
    pList.add(Triple(p.first, p.second, p.third - 1))

    pList.add(Triple(p.first, p.second + 1, p.third, ))
    pList.add(Triple(p.first, p.second - 1, p.third, ))

    pList.add(Triple(p.first + 1, p.second, p.third ))
    pList.add(Triple(p.first - 1, p.second, p.third))

    return pList
}

fun isClearExteriorPoint(p: Triple<Int, Int, Int>, universe: Set<Triple<Int,Int,Int>>): Boolean {
    return (
            universe.count{it.first > p.first} == 0 ||
                    universe.count{it.second > p.second} == 0 ||
                    universe.count{it.third > p.third} == 0 ||
                    universe.count{it.first < p.first} == 0 ||
                    universe.count{it.second < p.second} == 0 ||
                    universe.count{it.third < p.third} == 0
            )
}

fun isInteriorPoint(p: Triple<Int, Int, Int>, universe: Set<Triple<Int,Int,Int>>): Boolean {
    return (!universe.contains(p)) && (!isClearExteriorPoint(p, universe)) && !pathExistsToClearPoint(p, universe)
}

fun pathExistsToClearPoint(p: Triple<Int, Int, Int>, universe: Set<Triple<Int,Int,Int>>): Boolean {

    val visited = mutableSetOf<Triple<Int, Int, Int>>()
    var frontier = mutableSetOf<Triple<Int, Int, Int>>()
    val newFrontier = mutableSetOf<Triple<Int, Int, Int>>()

    frontier.add(p)
    visited.add(p)

    do {
        for (fPoint in frontier) {
            val nearList = generateAdjacentPoints(fPoint)
            for (nearPoint in nearList) {

                if (!visited.contains(nearPoint) && !universe.contains(nearPoint)) {
                    if (isClearExteriorPoint(nearPoint, universe)) {
                        // println ("path to clear = $nearPoint")
                        return true
                    }
                    newFrontier.add(nearPoint)
                    visited.add(nearPoint)
                }
            }
        }

        frontier = newFrontier.toMutableSet()
        newFrontier.removeAll {true}

    } while (frontier.isNotEmpty())

    return false
}

fun getExteriorPointsFromClearPoint(p: Triple<Int, Int, Int>, universe: Set<Triple<Int,Int,Int>>): Set<Triple<Int,Int,Int>> {

    val visited = mutableSetOf<Triple<Int, Int, Int>>()
    var frontier = mutableSetOf<Triple<Int, Int, Int>>()
    val newFrontier = mutableSetOf<Triple<Int, Int, Int>>()

    frontier.add(p)
    visited.add(p)

    var numLoops = 0

    do {
        println("num loops = $numLoops")
        for (fPoint in frontier) {
            val nearList = generateAdjacentPoints(fPoint)
            for (nearPoint in nearList) {
                if (!visited.contains(nearPoint) && !universe.contains(nearPoint)) {
                    newFrontier.add(nearPoint)
                    visited.add(nearPoint)
                }
            }
        }

        frontier = newFrontier.toMutableSet()
        newFrontier.removeAll {true}
        ++ numLoops

    } while (numLoops < 50)

    return visited
}
