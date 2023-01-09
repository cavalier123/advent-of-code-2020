package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.math.BigInteger

data class Monkey (val items: ArrayDeque<Long>, val operation: (Long) -> Long, val test: (Long) -> Int, var opCount: Long = 0L)

fun bi (num: Long): BigInteger {
    return BigInteger.valueOf(num)
}

val bigNum: Long = 3 * 13 * 2 * 11 * 5 * 17 * 19 * 7


fun main (args: Array<String>) {
    val lines = splitFileToLines("day11")

    val monkey0 = Monkey(ArrayDeque(listOf(64, 89, 65, 95)), { (it * 7).mod(bigNum) }, { if ( it.mod(3) == 0) 4 else 1 })
    val monkey1 = Monkey(ArrayDeque(listOf(76,66, 74, 87,70, 56, 51,66)), { (it + 5).mod(bigNum) }, { if (it.mod(13L) == 0L) 7 else 3 })
    val monkey2 = Monkey(ArrayDeque(listOf(91, 60, 63)), { (it * it).mod(bigNum) }, { if (it.mod(2L) == 0L) 6 else 5 })
    val monkey3 = Monkey(ArrayDeque(listOf(92, 61, 79, 97, 79)), { (it + 6).mod(bigNum) }, { if (it.mod(11L) == 0L) 2 else 6 })
    val monkey4 = Monkey(ArrayDeque(listOf(93, 54)), { (it * 11).mod(bigNum) }, { if (it.mod(5L) == 0L) 1 else 7 })
    val monkey5 = Monkey(ArrayDeque(listOf(60, 79, 92, 69, 88, 82, 70)), { (it + 8).mod(bigNum) }, { if (it.mod(17L) == 0L) 4 else 0 })
    val monkey6 = Monkey(ArrayDeque(listOf(64, 57, 73, 89, 55, 53)), { (it + 1).mod(bigNum) }, { if (it.mod(19L) == 0L) 0 else 5 })
    val monkey7 = Monkey(ArrayDeque(listOf(62)), { (it + 4).mod(bigNum) }, { if (it.mod(7L) == 0L) 3 else 2 })

    val monkeys = arrayOf(monkey0, monkey1, monkey2, monkey3, monkey4, monkey5, monkey6, monkey7)

//    Monkey 0:
//    Monkey inspects an item with a worry level of 79.
//    Worry level is multiplied by 19 to 1501.
//    Monkey gets bored with item. Worry level is divided by 3 to 500.
//    Current worry level is not divisible by 23.

    for (round in 1..10000) {
        for (monkey in monkeys) {
            for (item in monkey.items) {
                monkey.opCount ++
                var worryValue = item
                worryValue = monkey.operation(worryValue)
                //worryValue /= 3
                val monkeyToPassTo = monkey.test(worryValue)
                monkeys[monkeyToPassTo].items.addLast(worryValue)
            }
            monkey.items.removeAll { true }
        }
    }

    val sortedMonkeys = monkeys.sortedByDescending { it.opCount }

    val answer = sortedMonkeys[0].opCount * sortedMonkeys[1].opCount

    val answer2: Double = sortedMonkeys[0].opCount.toDouble() * sortedMonkeys[1].opCount.toDouble()

    println("Answer is $answer")
    println("Answer2 is $answer2")

}

