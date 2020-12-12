package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import kotlin.math.absoluteValue

fun main() {
    val filename = "DayTwelveData"
    val solution = solveItDayTwelvePartTwo(splitFileToLines(filename))
    println ("Solution is $solution")
}


fun solveItDayTwelvePartTwo(lines: List<String>): Int {

//Action N means to move the waypoint north by the given value.
//Action S means to move the waypoint south by the given value.
//Action E means to move the waypoint east by the given value.
//Action W means to move the waypoint west by the given value.
//Action L means to rotate the waypoint around the ship left (counter-clockwise) the given number of degrees.
//Action R means to rotate the waypoint around the ship right (clockwise) the given number of degrees.
//Action F means to move forward to the waypoint a number of times equal to the given value.

    var curAcrossBoat = 0
    var curDownBoat = 0

    var curAcrossWP = 10
    var curDownWP = -1

    for (line in lines) {

        val instruction = line[0]
        val num = line.substring(1).toInt()

        when (instruction) {
            'N' -> { curDownWP -= num }
            'S' -> { curDownWP += num}
            'E' -> { curAcrossWP += num}
            'W' -> { curAcrossWP -= num }
            'L', 'R' -> {
                if ((num == 90 && instruction == 'L') || (num == 270 && instruction == 'R')) {
                    curAcrossWP = curDownWP.also {curDownWP = curAcrossWP}
                    curDownWP *= -1
                } else if (num == 180) {
                    curAcrossWP *= -1
                    curDownWP *= -1
                } else if ((num == 90 && instruction == 'R') || (num == 270 && instruction == 'L')) {
                    curAcrossWP = curDownWP.also {curDownWP = curAcrossWP}
                    curAcrossWP *= -1
                }
            }
            'F' -> {
                curAcrossBoat += num * curAcrossWP
                curDownBoat += num * curDownWP

            }
        }
    }
    return curAcrossBoat.absoluteValue + curDownBoat.absoluteValue
}