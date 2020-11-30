package main.kotlin.uk.co.rge123

fun main() {
    val filename = "AoCTestData"
    val solution = solveIt(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveIt(lines: List<String>): Int {
    return lines.size
}