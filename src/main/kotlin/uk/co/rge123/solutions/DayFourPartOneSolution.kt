package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

private val requiredSet = HashSet<String>(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))

fun main() {
    val filename = "DayFourData"
    val solution = solveItDayFourPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayFourPartOne(lines: List<String>): Int {
    var curLine = StringBuffer()
    var valCount = 0

    for (line in lines) {
        if (line.length > 0) {
            if (curLine.isNotEmpty()) curLine.append(" ")
            curLine.append(line)
        } else {
            valCount += if (isValid(curLine.toString())) 1 else 0
            curLine = StringBuffer()
        }
    }
    valCount += if (isValid(curLine.toString())) 1 else 0

    return valCount

}

fun isValid(line:String) : Boolean {
    return line.split(" ").map{ it.split(":")[0] }.containsAll(requiredSet)
}


