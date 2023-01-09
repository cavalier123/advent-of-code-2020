package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Math.abs

fun main() {
    val filename = "day10"
    val solution = solveItDayTenPartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayTenPartOne2022(lines: List<String>): Int {

    var cycle = 1
    var x = 1
    var totalScore = 0;

    for (line in lines) {
        val words = line.split(" ")
        val op = words[0]
        if (op == "noop") {
            if (isSpriteInPosition((cycle - 1)% 40, x)) print("#") else print(".")
            cycle += 1
            if ((cycle - 1)  % 40 == 0) println()
        } else {
            val arg = words[1].toInt()
            // first tick
            if (isSpriteInPosition((cycle - 1)% 40, x)) print("#") else print(".")
            cycle += 1
            if ((cycle - 1) % 40 == 0) println()

            // second tick
            if (isSpriteInPosition((cycle - 1)% 40, x)) print("#") else print(".")
            cycle += 1
            if ((cycle  - 1) % 40 == 0) println()

            x += arg
        }
    }

    return totalScore
}

// Find the signal strength during the 20th, 60th, 100th, 140th, 180th, and 220th cycles.
// What is the sum of these six signal strengths?
fun isCountCycle(cycle: Int): Boolean {
    return cycle == 20 || cycle ==60 || cycle == 100 ||  cycle == 140 || cycle == 180 || cycle == 220
}

fun isSpriteInPosition(cycle: Int, pos:Int): Boolean {
    return abs(cycle-pos) <= 1
}





