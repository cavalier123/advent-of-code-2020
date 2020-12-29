package main.kotlin.uk.co.rge123.solutions

import java.util.*

fun main() {
    val solution = solveItDayFifteenPartOne()
    print ("Solution is $solution")
}

//Given your starting numbers, what will be the 2020th number spoken?
//
//Your puzzle input is 5,1,9,18,13,8,0.

fun solveItDayFifteenPartOne(): Int {

    val hash = mutableMapOf<Int, Pair<Int, Int?>>(5 to Pair(1, null), 1 to Pair(2,null), 9 to Pair(3,null),
            18 to Pair(4,null), 13 to Pair(5,null), 8 to Pair(6,null), 0 to Pair(7,0))

    var curTurn = 7
    var curValue = 0

    println (Date().time)
    do {
        curValue = getNextNum(curValue, curTurn++, hash)
    } while (curTurn < 30000000)
    println (Date().time)

    return curValue
}

fun getNextNum (prevNum: Int, prevIndex: Int, hash: MutableMap<Int, Pair<Int, Int?>>): Int {
    val hashEntry = hash[prevNum]
    if (hashEntry == null) {
        hash[prevNum] = Pair(prevIndex, null)
        return 0
    } else {
        val penultIndex = hashEntry.first
        hash[prevNum] = Pair(prevIndex, penultIndex)
        return prevIndex - penultIndex
    }
}
