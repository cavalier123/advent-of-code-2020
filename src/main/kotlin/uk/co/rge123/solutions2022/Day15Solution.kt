package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Math.*

const val ROW_NUM = 2000000
//const val ROW_NUM = 10

const val MAX_DIM = 4000000


data class Range (val start: Int, val end: Int, var used: Boolean = false)

data class Diamond(val point: Point, val dist: Int)

fun main() {
    val filename = "day15"
    val solution = solveItDay15PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
    //val solution2 = solveItDayFourteenPartTwo2022(splitFileToLines(filename))
    //print ("Solution is $solution2")
}

// Sensor at x=2209379, y=3030851: closest beacon is at x=2121069, y=3230302

fun solveItDay15PartOne2022(lines: List<String>): Int {

    val diamondList = mutableListOf<Diamond>()

    for (line in lines) {
        val words = line.split(" ")

        // curr
        val curXPart = words[2]
        val curX = curXPart.removeSuffix(",").substring(2).toInt()
        val curYPart = words[3]
        val curY = curYPart.removeSuffix(":").substring(2).toInt()
        val curPoint = Point(curX, curY)

        // beacon
        val beaconXPart = words[8]
        val beaconX = beaconXPart.removeSuffix(",").substring(2).toInt()
        val beaconYPart = words[9]
        val beaconY = beaconYPart.substring(2).toInt()
        val beaconPoint = Point(beaconX, beaconY)

        val dist: Int = abs (curX - beaconX) + abs (curY - beaconY)

        val diamond = Diamond(curPoint, dist)
        diamondList.add(diamond)
        //println ("cur = $curPoint, beacon = $beaconPoint")
    }

//    for (range in ranges) {
//        println("range = $range")
//    }
//    println("---------------------")

    var foundRow = 0
    for (row in 0 .. MAX_DIM) {
        // println ("Index = $row")
        var ranges = mutableListOf<Range>()
        for (diamond in diamondList) {
            val pRange = getRangeOnRow(diamond, row)
            if (pRange != null) ranges.add (pRange)
        }
        do {
            var numMerges = 0
            val newRanges = mutableListOf<Range>()

            loop@ for ((index, range) in ranges.withIndex()) {
                for (innerIndex in index + 1 until ranges.size) {
                    //println ("$index, $innerIndex")
                    val otherRange = ranges.elementAt(innerIndex)
                    if (otherRange.used) continue
                    if (overlaps(range, otherRange)) {
                        newRanges.add(merge(range, otherRange))
                        range.used = true
                        otherRange.used = true
                        //println("Merging $range and $otherRange")
                        ++numMerges
                        continue@loop
                    }
                }
                if (!range.used) {
                    newRanges.add(range)
                }
            }
            ranges = newRanges.toMutableList()
        } while (numMerges > 0)

        var totalOccupied = 0
        for (range in ranges) {
            //println("range = $range")
            totalOccupied += (range.end - range.start + 1)
        }
        // println("Total occupied = $totalOccupied")
        if (totalOccupied < (MAX_DIM + 1)) {
            println("BINGO at $row")
            var across = 0
            if (ranges[0].start < ranges[1].start) {
                across = ranges[0].end + 1
            } else {
                across = ranges[1].end + 1
            }
            println ("across = $across")
            val answer: Long = (across * MAX_DIM.toLong()) + row
            println(" answer = $answer")
            foundRow = row
            //break
        }
    }

//    val mergedRanges = mutableListOf<Point>()
//    val rangeIterator = ranges.iterator()
//    while (rangeIterator.hasNext()) {
//        val range = rangeIterator.next()
//
//
//
//    }

    //val count = map.count {it.y == 2000000

    return foundRow
}

fun overlaps (r1: Range, r2: Range): Boolean {
    return r1.end >= r2.start && r1.start <= r2.end
}

fun merge(r1: Range, r2: Range): Range {
    return Range(min(r1.start, r2.start), max (r1.end, r2.end))
}

fun addAllPoints(cur: Point, beacon: Point): Range? {

    val dist: Int = abs (cur.x - beacon.x) + abs (cur.y - beacon.y)

    if (ROW_NUM in (cur.y - dist) .. (cur.y + dist)) {
        val rowAt = abs(cur.y - ROW_NUM)
        val xDiff = dist - rowAt
        val startX = cur.x - xDiff
        val endX = cur.x + xDiff
        var range = Range(startX, endX)
        if (overlaps(range, Range(0, 20))) {
            range = Range(max(0, range.start), min(20, range.end))
            return range
        } else {
            return null
        }
//        for (x in 0..dist) {
//            for (y in 0..dist - x) {
//                map.add(Point(cur.x + x, cur.y + y))
//                map.add(Point(cur.x,x + x, cur.y-y))
//                map.add(Point(cur.x + y, cur.y + x))
//                map.add(Point(cur.x + -y, cur.y + x))
//            }
//        }
    } else {
        return null
    }
}

fun getRangeOnRow(diamond: Diamond, row: Int): Range? {

    if (row in (diamond.point.y - diamond.dist) .. (diamond.point.y + diamond.dist)) {
        val rowAt = abs(diamond.point.y - row)
        val xDiff = diamond.dist - rowAt
        val startX = diamond.point.x - xDiff
        val endX = diamond.point.x + xDiff
        var range = Range(startX, endX)
        if (overlaps(range, Range(0, MAX_DIM))) {
            range = Range(max(0, range.start), min(MAX_DIM, range.end))
            return range
        } else {
            return null
        }
    } else {
        return null
    }
}


