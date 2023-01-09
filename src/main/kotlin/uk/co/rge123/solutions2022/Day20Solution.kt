package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Math.abs
import kotlin.math.sign

fun main() {
    val filename = "day20"
    val solution = solveItDay20PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

data class CircularNumber(val value: Long, val originalPos: Int, var nextNum: CircularNumber?, var prevNum: CircularNumber?)
val valMap = mutableMapOf<Long, CircularNumber>()
val indexMap = mutableMapOf<Int, CircularNumber>()

fun solveItDay20PartOne2022(lines: List<String>): Long {

    var firstNum: CircularNumber? = null
    var prevNum: CircularNumber? = null

    for ((index, num) in lines.withIndex()) {
        val curNum = num.toLong() * 811589153L
        if (curNum == 0L) {
            println ("Stoat = $index")
        }
        val curCircularNumber = CircularNumber(curNum, index, null, prevNum)
        valMap[curNum] = curCircularNumber
        indexMap[index] = curCircularNumber
        if (index == 0) {
            firstNum = curCircularNumber
        }
        if (prevNum != null) {
            prevNum.nextNum = curCircularNumber
        }
        prevNum = curCircularNumber
    }
    prevNum!!.nextNum = firstNum
    firstNum!!.prevNum = prevNum

    var curNum = firstNum
    do {
        print("${curNum!!.value} ")
        curNum = curNum?.nextNum
    } while (curNum != firstNum)
    println()

    val numNums = indexMap.size

    println ("BADGER = $numNums")
    println ("BADGER2 = ${valMap.size}")

    for (loops in 1..10) {
        for (index in 0 until numNums) {
            val circularNumber = indexMap[index]
            var positionsToMove = circularNumber!!.value
    //        positionsToMove = if (circularNumber!!.value < 0) {
    //            numNums + circularNumber.value - 1
    //        } else {
    //            circularNumber.value
    //        }
            positionsToMove = if (positionsToMove > 0) positionsToMove % (numNums - 1) else -(abs(positionsToMove) % (numNums - 1))

            if (positionsToMove > 0) {
                // remove the number
                circularNumber.prevNum!!.nextNum = circularNumber.nextNum
                circularNumber.nextNum!!.prevNum = circularNumber.prevNum

                var nextNum = circularNumber.nextNum
                for (moves in 1 until positionsToMove) {
                    nextNum = nextNum!!.nextNum!!
                }

                // reinsert after nextNum
                nextNum!!.nextNum!!.prevNum = circularNumber
                circularNumber.nextNum = nextNum.nextNum
                nextNum.nextNum = circularNumber
                circularNumber.prevNum = nextNum
            } else if (positionsToMove < 0) {
                // remove the number
                circularNumber.prevNum!!.nextNum = circularNumber.nextNum
                circularNumber.nextNum!!.prevNum = circularNumber.prevNum

                var prevNum = circularNumber.prevNum
                for (moves in 1 until abs(positionsToMove)) {
                    prevNum = prevNum!!.prevNum!!
                }

                // reinsert before prevNum
                circularNumber.prevNum = prevNum!!.prevNum
                circularNumber.nextNum = prevNum
                prevNum.prevNum!!.nextNum = circularNumber
                prevNum.prevNum = circularNumber
            }

    //        var curNum = firstNum
    //        do {
    //            print("${curNum!!.value} ")
    //            curNum = curNum?.nextNum
    //        } while (curNum != firstNum)
    //        println()
        }
    }

    val zero = valMap[0]
    var sum: Long = 0
    var cur: CircularNumber? = zero

    for (moves in 1 .. 1000) {
        cur = cur!!.nextNum
    }
    println (cur!!.value)
    sum += cur.value
    for (moves in 1 .. 1000) {
        cur = cur!!.nextNum
    }
    println (cur!!.value)
    sum += cur.value
    for (moves in 1 .. 1000) {
        cur = cur!!.nextNum
    }
    println (cur!!.value)
    sum += cur.value

    return sum
}