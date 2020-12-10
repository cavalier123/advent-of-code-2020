package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWordsWhitespaceSeparated

fun main() {
    val filename = "DayNineData"
    val solution = solveItDayNinePartTwo(splitFileToWordsWhitespaceSeparated(filename).map {it.toLong()})
    print ("Solution is $solution")
}

fun solveItDayNinePartTwo(numbers: List<Long>): Long {

   for (index in 0 .. numbers.size - 1) {
       val result = canGetNum(index, numbers.slice(index .. numbers.size - 1))
       if (result.first) {
           return result.second
       }
   }

    return 0L

    // 393911906
}

fun canGetNum(index: Int, numbers: List<Long>): Pair<Boolean, Long> {

    var total = 0L
    var found = false
    for (index in 0 .. numbers.size -1) {
        total += numbers[index]
        if (total == 393911906L) {
            val slice = numbers.slice(0 .. index)
            return Pair(true, (slice.minOrNull() ?: 0) + (slice.maxOrNull() ?: 0))

        } else if (total > 393911906L) {
            break
        }
    }

    return Pair(false, 0L)
}

