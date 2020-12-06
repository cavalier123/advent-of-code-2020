package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DaySixData"
    val solution = solveItDaySixPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDaySixPartOne(lines: List<String>): Int {
    var count = 0

    var voteSet: Set<Char> = HashSet()
    var firstLineInGroup = true

    for (line in lines) {
        if (line.isNotEmpty()) {
            if (firstLineInGroup) {
                voteSet = line.toSet()
                firstLineInGroup = false
            } else {
                voteSet = voteSet union line.toSet() // change union to intersect for solution to part two
            }
        } else {
            count += voteSet.size
            voteSet = emptySet()
            firstLineInGroup = true
        }
    }
    count += voteSet.size

    return count

}


