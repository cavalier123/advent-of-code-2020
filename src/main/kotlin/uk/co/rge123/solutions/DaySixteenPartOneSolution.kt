package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DaySixteenData"
    val solution = solveIt(splitFileToLines(filename).filter {isValidLine(it)})
    print ("Solution is $solution")
}
fun solveIt(validLines: List<String>): Int {

    val colSets = Array(20) { mutableSetOf<Int>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20) }

    validLines.forEachIndexed { i, l ->

        val listNums = l.split(",").map { it.toInt() }
        for ((index, num) in listNums.withIndex()) {

            if (!(num in (36..269)) && !(num in (275..973))) {
                colSets[index].remove(1)
            }
            if (!(num in (25..237)) && !(num in (245..972))) {
                colSets[index].remove(2)
            }
            if (!(num in (34..576)) && !(num in (586..967))) {
                colSets[index].remove(3)
            }
            if (!(num in (48..199)) && !(num in (206..959))) {
                colSets[index].remove(4)
            }
            if (!(num in (31..172)) && !(num in (194..962))) {
                colSets[index].remove(5)
            }
            if (!(num in (34..448)) && !(num in (454..955))) {
                colSets[index].remove(6)
            }
            if (!(num in (42..400)) && !(num in (419..965))) {
                colSets[index].remove(7)
            }
            if (!(num in (36..528)) && !(num in (551..956))) {
                colSets[index].remove(8)
            }
            if (!(num in (46..456)) && !(num in (466..960))) {
                colSets[index].remove(9)
            }
            if (!(num in (48..293)) && !(num in (303..966))) {
                colSets[index].remove(10)
            }
            if (!(num in (50..796)) && !(num in (818..950))) {
                colSets[index].remove(11)
            }
            if (!(num in (46..589)) && !(num in (610..957))) {
                colSets[index].remove(12)
            }
            if (!(num in (38..55)) && !(num in (66..957))) {
                colSets[index].remove(13)
            }
            if (!(num in (37..144)) && !(num in (154..961))) {
                colSets[index].remove(14)
            }
            if (!(num in (48..832)) && !(num in (853..949))) {
                colSets[index].remove(15)
            }
            if (!(num in (40..495)) && !(num in (516..952))) {
                colSets[index].remove(16)
            }
            if (!(num in (32..429)) && !(num in (441..971))) {
                colSets[index].remove(17)
            }
            if (!(num in (27..338)) && !(num in (355..955))) {
                colSets[index].remove(18)
            }
            if (!(num in (42..473)) && !(num in (488..973))) {
                colSets[index].remove(19)
            }
            if (!(num in (26..379)) && !(num in (386..972))) {
                colSets[index].remove(20)
            }
        }
    }

    while (colSets.map {it.size}.maxOrNull() ?: 0 > 1) {
        val singletons = colSets.filter {it.size == 1}.map {it.toList()[0]}.toSet()
        colSets.filter{ it.size > 1 }.forEach{it.removeAll(singletons)   }
    }

    return 0
}

fun isValidLine(line: String): Boolean {
    val listNums = line.split(",").map {it.toInt()}
    for (num in listNums) {
        if (!isValidSeatNum(num)) {
            return false
        }
    }
    return true
}

fun isValidSeatNum(num: Int): Boolean {

    return num in (36 ..269) || num in (275 .. 973)
            ||num in(25..237) || num in(245..972)
            ||num in(34..576) || num in(586..967)
            ||num in(48..199) || num in(206..959)
            ||num in(31..172) || num in(194..962)
            ||num in(34..448) || num in(454..955)
            ||num in(42..400) || num in(419..965)
            ||num in(36..528) || num in(551..956)
            ||num in(46..456) || num in(466..960)
            ||num in(48..293) || num in(303..966)
            ||num in(50..796) || num in(818..950)
            ||num in(46..589) || num in(610..957)
            ||num in(38..55) || num in (66..957)
            ||num in(37..144) || num in(154..961)
            ||num in(48..832) || num in(853..949)
            ||num in(40..495) || num in(516..952)
            ||num in(32..429) || num in(441..971)
            ||num in(27..338) || num in(355..955)
            ||num in(42..473) || num in(488..973)
            ||num in(26..379) || num in(386..972)
}