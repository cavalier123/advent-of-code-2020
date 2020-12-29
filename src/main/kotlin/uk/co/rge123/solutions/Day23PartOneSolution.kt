package main.kotlin.uk.co.rge123.solutions

fun main() {
    val solution = solveItDay23PartOne("123487596")
    //val solution = solveItDay23PartOne("389125467")
    print ("Solution is $solution")
}

fun solveItDay23PartOne(input: String): Long {

    val numList = input.map{it.toInt() - 48}.toMutableList()
    for (i in 10 .. 1000000) {
        numList.add(i)
    }

    var curPos = 0

//    val ret = removeFrom(letterList, 7, 3)
//    println(letterList)
//    println(ret.first)
//    println(ret.second)
//    insertInto(letterList, listOf(1,2,3), 0)
//    println(letterList)

//    The crab picks up the three cups that are immediately clockwise of the current cup. They are removed
//    from the circle; cup spacing is adjusted as necessary to maintain the circle.

//    The crab selects a destination cup: the cup with a label equal to the current cup's label minus one.
//    If this would select one of the cups that was just picked up, the crab will keep subtracting one until it
//    finds a cup that wasn't just picked up. If at any point in this process the value goes below the lowest value
//    on any cup's label, it wraps around to the highest value on any cup's label instead.

//    The crab places the cups it just picked up so that they are immediately clockwise of the destination cup.
//    They keep the same order as when they were picked up.

//    The crab selects a new current cup: the cup which is immediately clockwise of the current cup.
//

    var gen = 0

    //val cupIndices = Array(1_000_000) {i -> i }
    //input.map{it.toInt() - 48}.forEachIndexed { i, cup -> cupIndices[cup - 1] = i }
//    val hash = mutableMapOf<Int,Int>()
//    input.map{it.toInt() - 48}.forEachIndexed { i, cup -> hash[cup] = i }
//    for (i in 10 .. 1_000_000) {
//        hash[i] = i - 1
//    }

    do {
        ++gen
        println("gen = $gen")

        // get thbrew cups clockwise from currenbt cup
        val ret = removeFrom(numList, curPos, 3)
        val removedList = ret.first
        curPos = ret.second

        //println("removed list = $removedList")
        //println ("cur pos = $curPos")

        // select destination
        var cupLabel = numList[curPos]
        do {
            cupLabel -= 1
            if (cupLabel == 0) cupLabel = 1_000_000
        } while (cupLabel in removedList)

        val destPos = numList.indexOf(cupLabel)
        //println("dest pos = $destPos")

        //Insert the els we cut at the required destination
        insertInto(numList, removedList, destPos)
        //println ("num list after insertion = $numList")
//        for ((i,num) in numList.withIndex()) {
//            hash[num] = (destPos + i + 1) % numList.size
//        }

        if (destPos < curPos) curPos += removedList.size

        // select new curPos
        curPos = (curPos + 1) % numList.size
        //println ("New curpos = $curPos")

    } while (gen != 10000000)

    //println("final list = $numList")

    val indexOfOne = numList.indexOf(1)
    val nextCupIndex = (indexOfOne + 1) % 1000000
    val nextCupPlusOneIndex = (indexOfOne + 2) % 1000000

    val altAnswer = numList[1].toLong() * numList[2].toLong()
    println("Alternative answer = $altAnswer")

    return numList[nextCupIndex].toLong() * numList[nextCupPlusOneIndex].toLong()

//    var retString = ""
//    if (indexOfOne == 0) {
//        retString = numList.drop(1).joinToString("")
//    } else if (indexOfOne == numList.size - 1) {
//        retString = numList.dropLast(1).joinToString("")
//    } else {
//        val ret1 = removeSlice(numList, indexOfOne + 1, numList.size - 1)
//        val ret2 = removeSlice(numList, 0, (indexOfOne + numList.size - 1) % numList.size)
//        ret1.addAll(ret2)
//        retString = ret1.joinToString("")
//    }

    //return retString
}

fun removeSlice(list: MutableList<Int>, from: Int, end: Int): MutableList<Int> {
    val toRemove = list.subList(from, end + 1)
    val ret = toRemove.toMutableList()
    toRemove.clear()
    return ret
}

fun removeFrom(list: MutableList<Int>, curPos: Int, len:Int): Pair<List<Int>, Int> {

    var retList = mutableListOf<Int>()
    var retCurPos = curPos

    if (curPos + len < list.size) {
        retList = removeSlice(list, curPos + 1, curPos + len)
    } else {
        val ret1 = removeSlice(list, curPos + 1, list.size - 1)
        val ret2 = removeSlice(list, 0, (curPos + len - ret1.size) % (list.size))
        retList = ret1.toMutableList()
        retList.addAll(ret2)
        retCurPos -= ret2.size
    }
    return Pair(retList, retCurPos)
}

fun insertInto (list: MutableList<Int>, toInsert: List<Int>, curPos: Int) {
    list.addAll(curPos + 1, toInsert)
}

