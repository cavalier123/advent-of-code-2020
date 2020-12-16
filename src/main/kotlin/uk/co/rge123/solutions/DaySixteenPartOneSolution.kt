package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DaySixteenData"
    val solution = solveIt(splitFileToLines(filename).filter {isValidLine(it)})
    print ("Solution is $solution")
}

val fieldRanges = arrayOf(
        (36 ..269).plus(275 .. 973),
        (25..237).plus(245..972),
        (34..576).plus(586..967),
        (48..199).plus(206..959),
        (31..172).plus(194..962),
        (34..448).plus(454..955),
        (42..400).plus(419..965),
        (36..528).plus(551..956),
        (46..456).plus(466..960),
        (48..293).plus(303..966),
        (50..796).plus(818..950),
        (46..589).plus(610..957),
        (38..55).plus(66..957),
        (37..144).plus(154..961),
        (48..832).plus(853..949),
        (40..495).plus(516..952),
        (32..429).plus(441..971),
        (27..338).plus(355..955),
        (42..473).plus(488..973),
        (26..379).plus(386..972) )

fun solveIt(validLines: List<String>): Int {

    val colSets = Array(20) { mutableSetOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20) }

    validLines.forEachIndexed { i, l ->

        val listNums = l.split(",").map { it.toInt() }
        for ((index, num) in listNums.withIndex()) {

            for((fieldIndex, fieldRange) in fieldRanges.withIndex()) {
                if (!(num in fieldRange)) {
                    colSets[index].remove(fieldIndex + 1)
                }
            }
        }
    }

    while (colSets.map {it.size}.maxOrNull() ?: 0 > 1) {
        val singletons = colSets.filter {it.size == 1}.map {it.toList()[0]}.toSet()
        colSets.filter{ it.size > 1 }.forEach{it.removeAll(singletons)   }
    }

    for (colSet in colSets) {
        println(colSet)
    }
    return 0
}

fun isValidLine(line: String): Boolean {
    val listNums = line.split(",").map {it.toInt()}
    return listNums.find {!isValidSeatNum(it)} == null
}

fun isValidSeatNum(num: Int) = fieldRanges.filter {it.contains(num)}.count() > 0
