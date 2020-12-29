package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "Day24Data"
    val solution = solveItDay24PartTwo(splitFileToLines(filename))
    print ("Solution is $solution")
}

typealias HexPoint = Pair<Int, Int>

fun solveItDay24PartTwo(lines: List<String>): Int {
    var tileSet2 = getInitialTileSet(lines)
    var tileSet1 = mutableSetOf<HexPoint>()

    var genCount = 0
    do {
        // wap grids
        tileSet1 = tileSet2.also {tileSet2 = tileSet1}
        // clear second grid
        tileSet2.clear()
        populateTileSet(tileSet1, tileSet2)
        ++ genCount
        println("$genCount")
    } while (genCount < 100)

    return tileSet2.count()

}

fun getInitialTileSet(lines: List<String>) : MutableSet<Pair<Int,Int>> {
    val tileSet = mutableSetOf<Pair<Int, Int>>()

    for (line in lines) {
        //println(line)
        var curAcross = 0
        var curDown = 0

        var curIndex = 0
        while (curIndex < line.length) {
            var prevDir = ""
            var dir = ""
            if (line[curIndex] == 'w') {
                dir = "w"
                ++curIndex
            } else if (line[curIndex] == 'e') {
                dir = "e"
                ++curIndex
            } else if (line[curIndex] == 'n') {
                if (line[curIndex + 1] == 'e') {
                    dir = "ne"
                    curIndex += 2
                } else if (line[curIndex + 1] == 'w') {
                    dir = "nw"
                    curIndex += 2
                }
            } else if (line[curIndex] == 's') {
                if (line[curIndex + 1] == 'e') {
                    dir = "se"
                    curIndex += 2
                } else if (line[curIndex + 1] == 'w') {
                    dir = "sw"
                    curIndex += 2
                }
            }
            //print ("$dir, ")
            if (dir == "nw") {
                curDown -= 2
            }
            else if (dir == "ne") {
                curAcross += 1
                curDown -= 1
            }
            else if (dir == "e") {
                curAcross += 1
                curDown += 1
            }
            else if (dir == "se") {
                curDown += 2
            } else if (dir == "sw") {
                curAcross -= 1
                curDown += 1
            } else if (dir == "w") {
                curAcross -= 1
                curDown -= 1
            }
        }
        //println("")

        val coord = Pair (curAcross, curDown)
        if (tileSet.contains(coord)) {
            tileSet.remove(coord)
        } else {
            tileSet.add(coord)
        }

    }

    return tileSet

}

//Any black tile with zero or more than 2 black tiles immediately adjacent to it is flipped to white.
//Any white tile with exactly 2 black tiles immediately adjacent to it is flipped to black.

fun populateTileSet(firstTileSet: Set<HexPoint>, secondTileSet: MutableSet<HexPoint>) {

    for (hexPoint in firstTileSet) {
        val activeNeighbours = countActiveHexNeighbours(hexPoint, firstTileSet)
        if (activeNeighbours != 0 && activeNeighbours <= 2) {
            secondTileSet.add(hexPoint)
        }
    }

    val whiteTilesToCheck = generateAdajacentHexPoints(firstTileSet.toList(), false, firstTileSet)

    for (hexPoint in whiteTilesToCheck) {
        if (countActiveHexNeighbours(hexPoint, firstTileSet) == 2) {
            secondTileSet.add(hexPoint)
        }
    }
}

fun getHexPointsNear(point: HexPoint): List<HexPoint> {
    return listOf(Pair(0, -2),Pair(1,-1), Pair(1,1), Pair(0,2), Pair(-1,1), Pair(-1, -1))
            .map { HexPoint(point.first + it.first, point.second + it.second)}
}

fun generateAdajacentHexPoints(points: List<HexPoint>, active: Boolean, blackTiles: Set<HexPoint>): Set<HexPoint> {

    val ret = mutableSetOf<HexPoint>()

    for (hexPoint in points) {
        val adjacents = getHexPointsNear(hexPoint)
        for (adj in adjacents) {
            if (adj in blackTiles && active == true) {
                ret.add(adj)
            } else if (adj !in blackTiles && active == false) {
                ret.add(adj)
            }
        }
    }
    return ret
}

fun countActiveHexNeighbours(point: HexPoint, activePoints: Set<HexPoint>): Int {
    return generateAdajacentHexPoints(listOf(point), true, activePoints).size
}