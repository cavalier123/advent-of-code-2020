package main.kotlin.uk.co.rge123.solutions

fun main() {
    val solution = solveItDay23PartTwo("123487596")
    //val solution = solveItDay23PartOne("389125467")
    print ("Solution is $solution")
}

class CupNode (val cupNumber: Int, var next: CupNode?) {
    override fun toString() = "cup-num = $cupNumber, next = ${next!!.cupNumber}"
}

fun solveItDay23PartTwo(input: String): Long {

    val numList = input.map{it.toInt() - 48}.toMutableList()
    for (i in 10 .. 1000000) {
        numList.add(i)
    }

    var curCup = CupNode(numList[0], null)
    var currentCup = curCup
    val cupPtrs = arrayOfNulls<CupNode>(numList.size)
    cupPtrs[0] = currentCup

    for (num in numList.drop(1)) {
        val newCup = CupNode(num, null)
        curCup.next = newCup
        curCup = newCup
        cupPtrs[curCup.cupNumber - 1] = curCup
    }
    curCup.next = currentCup

    //println("made list")
    //println(currentCup)

//    curCup = currentCup
//    do {
//        print("$curCup.cupNumber,")
//        curCup = curCup.next!!
//    } while (curCup != currentCup)
//    println()
//
//    println("ptrs")
//    for (cup in cupPtrs) {
//        println(cup)
//    }

//    val subList = removeFrom(currentCup, 3)
//    curCup = currentCup
//    do {
//        print("$curCup.cupNumber,")
//        curCup = curCup.next!!
//    } while (curCup != currentCup)
//    println()

//    insertInto(currentCup, subList)
//
//    curCup = currentCup
//    do {
//        print("$curCup.cupNumber,")
//        curCup = curCup.next!!
//    } while (curCup != currentCup)
//    println()


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
        //println("gen = $gen")

        // get thbrew cups clockwise from currenbt cup
        val removedList = removeFrom(currentCup, 3)

        //println("removed list = $removedList")
        //println ("cur pos = $curPos")

        // select destination
        var cupLabel = currentCup.cupNumber
        do {
            cupLabel -= 1
            if (cupLabel == 0) cupLabel = 1_000_000
        } while (cupIn(cupLabel, removedList))

        val destCup = cupPtrs[cupLabel - 1]
        //println("dest pos = $destPos")

        //Insert the els we cut at the required destination

        insertInto(destCup!!, removedList)

        currentCup = currentCup.next!!

        //println ("num list after insertion = $numList")
//        for ((i,num) in numList.withIndex()) {
//            hash[num] = (destPos + i + 1) % numList.size
//        }

    } while (gen != 10_000_000)
////
//    //println("final list = $numList")
//
//    val indexOfOne = numList.indexOf(1)
//    val nextCupIndex = (indexOfOne + 1) % 1000000
//    val nextCupPlusOneIndex = (indexOfOne + 2) % 1000000
//
//    val altAnswer = numList[1].toLong() * numList[2].toLong()
//    println("Alternative answer = $altAnswer")
//
//    return numList[nextCupIndex].toLong() * numList[nextCupPlusOneIndex].toLong()

    val oneCup = cupPtrs[0]
    return oneCup!!.next!!.cupNumber.toLong() * oneCup.next!!.next!!.cupNumber.toLong()
//    curCup = oneCup!!.next!!
//    val buf = StringBuffer()
//    do {
//        buf.append("${curCup.cupNumber}")
//        curCup = curCup.next!!
//    } while (curCup != oneCup)

    //return buf.toString()
}


fun removeFrom(curNode: CupNode, len: Int): CupNode {
    val ret = curNode.next
    for (i in 1 .. len) {
        curNode.next = curNode.next!!.next
    }
    return ret!!
}
//
fun insertInto (curNode: CupNode, subList: CupNode) {
    val nodeAfterCurNode = curNode.next
    val finalNodeInSubList = subList.next!!.next
    curNode.next = subList
    finalNodeInSubList!!.next = nodeAfterCurNode
}

fun cupIn(cupNumber:Int, curNode: CupNode): Boolean {
    return  cupNumber == curNode.cupNumber ||
            cupNumber == curNode.next!!.cupNumber ||
            cupNumber == curNode.next!!.next!!.cupNumber
}

