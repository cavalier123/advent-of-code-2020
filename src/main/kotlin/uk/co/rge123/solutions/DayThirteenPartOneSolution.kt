package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToWords
import main.kotlin.uk.co.rge123.utils.splitFileToWordsWhitespaceSeparated

fun main() {
    val filename = "DayThirteenData"
    //val solution = solveItDayThirteenPartOne(splitFileToWords(filename, ",").filter { it != "x" }.map { it.toInt() })
    val solution = solveItDayThirteenPartOne()
    print ("Solution is $solution")
}

fun solveItDayThirteenPartOne(): Long {

//    100000000000000
//    773 - 8 = 23
//    773 - 12 = 19
//    773 - 29 = 29
//    773 - 31 = 449
//    773 - 37 = 37
//    773 - 44 = 13
//
//    773 + 10 = 41
//    773 + 17 = 17
    // 13,x,x,x,x,x,x,37,x,x,x,x,x,449,x,29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,19,x,x,x,23,x,x,x,x,x,x,x,773,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,17

    // 1, 13,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // 8,   37,
    // x,
    // x,
    // x,
    // x,
    // x,
    // 14,     449
    // ,x,
    // 16,       29,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // 33,    19,
    // x,
    // x,
    // x,
    // 37,     23
    // ,x
    // ,x
    // ,x
    // ,x
    // ,x
    // ,x
    // ,x,
    // 45,      773
    // ,x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // 55,   41
    // ,x,
    // x,
    // x,
    // x,
    // x,
    // x,
    // 62,     17


    // 7154511043394797568



    // LCMs
    // 13,  8,  37,   14,      449,



    // 773 at 45

    //100566365477785

    // pulse at 773
    // 742 * n must be a multiple of 449 - LCM = 333,158
    // 449 + 31 = 480 must be multiple of 773 - LCM = 371,040
    // 449 - 6 = 443 must be a mutiple of 37 - LCM = 16,391
    // combined lcm = 6081716640

    // 449 is the anchor
    // 449 + 31 = 480 must be a multople of 773. LCM = 371,040
    // 449 - 6 = 443 must be a multple of 37. LCM = 16,391
    // combined lcm = 6081716640

    // 451 LCM 29 = 13,079
// 79542771934560

    var count = (100000000000000L / 79542771934560) * 79542771934560
    println ("Start count == $count")

    do {
        count += 79542771934560
        println ("$count")

        if ( (count + 2) % 29 == 0L &&
                (count + 19) %19 == 0L &&
                (count + 23) %23 == 0L &&
                (count + 31) %773 == 0L &&
                (count + 41) %41 == 0L &&
                (count + 48) %17 == 0L &&

                (count - 6) %37 == 0L &&
                (count - 13) %13 == 0L
        ) {
            break
        }


    } while (true)



    // val mintime = numbers.map { Pair(it - (1002576 % it), it) }.minByOrNull { it.first } ?: Pair(0,0)
    //return mintime.first * mintime.second

    return count
}