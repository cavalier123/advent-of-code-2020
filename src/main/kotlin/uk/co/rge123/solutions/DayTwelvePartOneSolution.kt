package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import kotlin.math.absoluteValue

fun main() {
    val filename = "DayTwelveData"
    val solution = solveItDayTwelvePartOne(splitFileToLines(filename))
    println ("Solution is $solution")
}

fun solveItDayTwelvePartOne(lines: List<String>): Int {

//    Action N means to move north by the given value.
//    Action S means to move south by the given value.
//    Action E means to move east by the given value.
//    Action W means to move west by the given value.
//    Action L means to turn left the given number of degrees.
//    Action R means to turn right the given number of degrees.
//    Action F means to move forward by the given value in the direction the ship is currently facing.
//    The ship starts by facing east.

    var curAcross = 0
    var curDown = 0
    var dirDegrees = 90

    for (line in lines) {

        val instruction = line[0]
        val num = line.substring(1).toInt()

        when (instruction) {
            'N' -> { curDown -= num }
            'S' -> { curDown += num}
            'E' -> { curAcross += num}
            'W' -> { curAcross -= num }
            'L' -> {
                dirDegrees = (dirDegrees - num) % 360
                if (dirDegrees < 0) {
                    dirDegrees = (360 - dirDegrees.absoluteValue) % 360
                }
            }
            'R' -> {
                dirDegrees = (dirDegrees + num) % 360
            }
            'F' -> {
                when (dirDegrees) {
                    0 -> curDown -= num
                    90 -> curAcross += num
                    180 -> curDown += num
                    270 -> curAcross -= num
                }
            }
        }
    }
    return curAcross.absoluteValue + curDown.absoluteValue
}

