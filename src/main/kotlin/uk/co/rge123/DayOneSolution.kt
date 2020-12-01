package main.kotlin.uk.co.rge123

fun main() {
    val filename = "DayOneData"
    val words = splitFileToWordsWhitespaceSeparated(filename)
    val numbers = words.map{it.toInt()}

    val solution = solveItWithSomeWords(numbers)
    print ("Solution is $solution")
}

fun solveItWithSomeWords(numbers: List<Int>): Int {

    for (i in 0 .. numbers.size - 2) {
        for (j in i + 1 .. numbers.size - 1) {
            for (k in 0 .. numbers.size - 1) {
                if (numbers[i] + numbers[j] + numbers[k] == 2020) println(numbers[i] * numbers[j] * numbers[k])
            }
        }
    }
   return 0
}