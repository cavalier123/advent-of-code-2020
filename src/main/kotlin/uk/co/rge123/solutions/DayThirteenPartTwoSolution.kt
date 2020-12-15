package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWords

fun main() {
    val filename = "DayThirteenData"
    val busList = splitFileToWords(filename, ",").mapIndexed { i, str -> if (str != "x") Pair(str.toInt(), i) else null}.filterNotNull()
    val solution = solveItDayThirteenPartTwo(busList.toMutableList())
    println ("Solution is $solution")
}

fun solveItDayThirteenPartTwo(buses: MutableList<Pair<Int, Int>>): Long {
    var curStep = buses[0].first.toLong()
    var startTime = 0L
    // don't process tzhe first bus, as we have already taken it into account
    buses.removeFirst()

    for (bus in buses) {
        // find new start time, taking into accouynt the current bus
        // we are solving the equastion (for time t) for which (t + startTime) % busNumber == 0 . If we always step by
        // cur-step, then we know that all previous equations will also be satisfied. This is the clever bit.
        while ((startTime + bus.second) % bus.first != 0L) {
            startTime += curStep
        }
        // update step size. we can juts multiply as all steps sizes are prime i.e. no commonn factors
        curStep *= bus.first
    }
    return startTime
}