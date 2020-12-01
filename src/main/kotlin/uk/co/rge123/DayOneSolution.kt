package main.kotlin.uk.co.rge123

fun main() {
    val filename = "DayOneData"
    val words = splitFileToWordsWhitespaceSeparated(filename)
    val numbers = words.map{it.toInt()}

    val solution = solveItWithSomeNumbers(numbers)
    print ("Solution is $solution")
}

fun solveItWithSomeNumbers(numbers: List<Int>): Int {

    for (i in numbers.indices) {
        for (j in i + 1 .. numbers.size - 1) {
            for (k in numbers.indices) {
                if (numbers[i] + numbers[j] + numbers[k] == 2020) println(numbers[i] * numbers[j] * numbers[k])
            }
        }
    }
   return 0
}