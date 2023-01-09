package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

data class Rock (val width: Int, val height: Int, val points: Set<Point>) {
    fun materialise(point: Point): Set<Point> {
        return points.map {Point(it.x + point.x, it.y + point.y)}.toSet()
    }
}

data class Universe(val points: MutableSet<Point> = mutableSetOf()) {

    val maxes = Array<Int>(7) {0}

    fun addRock (rock: Rock, point: Point) {
        val matRock = rock.materialise(point)
        points.addAll(matRock)
        for (point in matRock) {
            if (point.y > maxes[point.x]) {
                maxes[point.x] = point.y
            }
        }
    }

    fun isLevel() = maxes.maxOf{it} == maxes.minOf{it}

    fun checkRockHits (rock: Rock, point: Point): Boolean {
        if (point.x < 0) return true
        if (point.x + rock.width > 7) return true
        if (point.y < 0) return true

        val matRock = rock.materialise(point)
        return matRock.intersect(points).isNotEmpty()
    }

    fun getMaxHeight(): Int {
        if (points.isEmpty()) {
            return 0
        } else {
            return points.maxOf{it.y} + 1
        }
    }
}

//####
//
//.#.
//###
//.#.
//
//..#
//..#
//###
//
//#
//#
//#
//#
//
//##
//##


val rock1List = setOf(Point(0,0), Point(1, 0), Point(2,0), Point(3,0))
val rock2List = setOf(Point(1,0), Point(0, 1), Point(1,1), Point(2,1), Point(1,2))
val rock3List = setOf(Point(0,0), Point(1,0), Point(2,0), Point(2,1), Point(2,2))
val rock4List = setOf(Point(0,0), Point(0,1), Point(0,2), Point(0,3))
val rock5List = setOf(Point(0,0), Point(0,1), Point(1,0), Point(1,1))

val rock1 = Rock(4,1, rock1List)
val rock2 = Rock(3,3, rock2List)
val rock3 = Rock(3,3, rock3List)
val rock4 = Rock(1,4, rock4List)
val rock5 = Rock(2,2, rock5List)

val rockList = listOf (rock1, rock2, rock3, rock4, rock5)
val universe = Universe()

fun main() {
    val filename = "day17"
    val solution = solveItDay17PartOne2022(splitFileToLines(filename).first().toCharArray().toList())
    print ("Solution is $solution")
}

fun solveItDay17PartOne2022(dirs: List<Char>): Int {

    var curDirIndex = 4661

    val numsRemaining = 1000000000000L - 796L
    val blocksOf1760: Long = numsRemaining / 1760L
    val lastUsedIndex: Long = 796L + (1760L * blocksOf1760)
    println ("Last used index = $lastUsedIndex")
    // 999999998716
    val numInBlocks = blocksOf1760 * 2737L
    val numAtStart = 1273
    println ("Num at start = 1273")
    println ("Total in blocks = $numInBlocks")
    val numAtEnd = 1983
    val answer = numAtStart.toLong() + numInBlocks + numAtEnd.toLong()
    println ("ANSWER = $answer")

    var rockIndex = 2
    for (rockNum in 999999998717L .. 1000000000000L) {
        // println("rock num = $rockNum")
        val curRock = rockList[(rockIndex - 1) % 5]
        ++rockIndex
        val curPoint = Point(2, universe.getMaxHeight() + 3)
        var blocked = false
        do {
            val curDir = dirs[curDirIndex % dirs.size]
            curDirIndex++
            if (curDir == '>') {
                if (!universe.checkRockHits(curRock, Point(curPoint.x + 1, curPoint.y))) {
                    curPoint.x++
                }
            } else {
                if (!universe.checkRockHits(curRock, Point(curPoint.x -1, curPoint.y))) {
                    curPoint.x--
                }
            }
            if (!universe.checkRockHits(curRock, Point(curPoint.x, curPoint.y - 1))) {
                curPoint.y--
            } else {
                blocked = true
                universe.addRock(curRock, curPoint)
                if (universe.isLevel()) {
                    val effectiveIndex = curDirIndex % dirs.size
                    println ("LEVEL at $rockNum and $effectiveIndex")
                }
            }
        } while (!blocked)
    }

    return universe.getMaxHeight()
}