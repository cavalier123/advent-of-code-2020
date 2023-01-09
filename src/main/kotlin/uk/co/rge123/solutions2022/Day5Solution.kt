package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import main.kotlin.uk.co.rge123.utils.splitFileToSetOfIntPairs

//[N]         [C]     [Z]
//[Q] [G]     [V]     [S]         [V]
//[L] [C]     [M]     [T]     [W] [L]
//[S] [H]     [L]     [C] [D] [H] [S]
//[C] [V] [F] [D]     [D] [B] [Q] [F]
//[Z] [T] [Z] [T] [C] [J] [G] [S] [Q]
//[P] [P] [C] [W] [W] [F] [W] [J] [C]
//[T] [L] [D] [G] [P] [P] [V] [N] [R]
//1   2   3   4   5   6   7   8   9

// move 6 from 2 to 1

fun main (args: Array<String>) {
    val lines = splitFileToLines("dayFive")

    val stack1 = ArrayDeque(listOf('T', 'P', 'Z', 'C', 'S', 'L', 'Q', 'N'))
    val stack2 = ArrayDeque(listOf('L', 'P', 'T', 'V', 'H', 'C', 'G'))
    val stack3 = ArrayDeque(listOf('D', 'C', 'Z', 'F'))
    val stack4 = ArrayDeque(listOf('G', 'W', 'T', 'D', 'L', 'M', 'V', 'C'))
    val stack5 = ArrayDeque(listOf('P', 'W', 'C'))
    val stack6 = ArrayDeque(listOf('P', 'F', 'J', 'D', 'C', 'T', 'S', 'Z'))
    val stack7 = ArrayDeque(listOf('V', 'W', 'G', 'B', 'D'))
    val stack8 = ArrayDeque(listOf('N', 'J', 'S', 'Q', 'H', 'W'))
    val stack9 = ArrayDeque(listOf('R', 'C', 'Q', 'F', 'S', 'L','V'))

    val stackArray = arrayOf(stack1, stack2, stack3, stack4, stack5, stack6, stack7, stack8, stack9)

    for (line in lines) {
       val words = line.split(" ")
       val numToMove = words[1].toInt()
       val from = words[3].toInt()
       val to = words[5].toInt()
       moveIt (stackArray, numToMove, from, to)
   }

   for (stack in stackArray) {
       print("${stack.last()}")
   }
    println ()
}

fun moveIt (stackArray: Array<ArrayDeque<Char>>, numToMove: Int, from: Int, to: Int) {

    val fromStack = stackArray[from - 1]
    val toStack = stackArray[to - 1]

    val midStack = ArrayDeque<Char>()

    for (i in 1..numToMove) {
        val fromChar = fromStack.removeLast()
        midStack.addLast(fromChar)
    }

    for (i in 1..numToMove) {
        val fromChar = midStack.removeLast()
        toStack.addLast(fromChar)
    }

}


//fun contains (p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
//    return (p1.first <= p2.first && p1.second >= p2.second) || (p2.first <= p1.first && p2.second >= p1.second)
//}
//
//fun overlaps (p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
//    return ((p1.second >= p2.first) && (p1.first <= p2.second))
//}



//fun solveItDayTwoPartOne(lines: List<String>) = lines.filter { getPasswordInfo(it).isValid() }.count()
//
//fun getPasswordInfo(input: String): PasswordInfo {
//    val words = input.split(" ")
//    val rangeNumbers = words[0].split("-")
//    return PasswordInfo(rangeNumbers[0].toInt(), rangeNumbers[1].toInt(), words[1][0], words[2])
//}
//
//data class PasswordInfo(val rangeStart: Int, val rangeEnd: Int, val checkedChar: Char, val password: String) {
//    fun isValid() = password.count { it == checkedChar } in rangeStart..rangeEnd
//}