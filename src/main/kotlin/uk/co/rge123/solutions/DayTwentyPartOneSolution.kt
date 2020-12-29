package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayTwentyData"
    val solution = solveItDayTwentyPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

//Tile 1217:
//.#....#..#
//#.#....###
//##.#....#.
//##...###..
//###..#..#.
//.#...##...
//#.#..##...
//#...#.##..
//#..##....#
//.##.#..#..
// width = 10
// height = 10

enum class Dir {TOP, LEFT, RIGHT, BOTTOM}

data class Tile(val number: Int, var entries: Array<Array<Char>>,
                val nonMatchingEdges: MutableSet<Dir> = mutableSetOf<Dir>(), var used: Boolean = false) {
    fun print() {
        println ("Tile = $number")
        for (row in 0 .. 9) {
            for (col in 0 ..9) {
                print(entries[row][col])
            }
            println()
        }
        println("Non matching edges = $nonMatchingEdges")
    }
    fun isCorner(): Boolean {
        return nonMatchingEdges.size == 2
    }
    fun isSide(): Boolean {
        return nonMatchingEdges.size == 1
    }
    fun isInside(): Boolean {
        return nonMatchingEdges.size == 0
    }
    fun rotate90Clockwise() {
        val newEntries = Array(10) { j -> Array<Char>(10){ i -> '.'} }
        for (row in 0 .. 9) {
            for (col in 0 .. 9) {
                newEntries[row][col] = entries[9 - col][row]
            }
        }
        entries = newEntries
    }
    fun rotate90AntiClockwise() {
        val newEntries = Array(10) { j -> Array<Char>(10){ i -> '.'} }
        for (row in 0 .. 9) {
            for (col in 0 .. 9) {
                newEntries[row][col] = entries[9 - col][9 - row]
            }
        }
        entries = newEntries
    }

    fun flipHorizontal() {
        val newEntries = Array(10) { j -> Array<Char>(10){ i -> '.'} }
        for (row in 0 .. 9) {
            for (col in 0 .. 9) {
                newEntries[row][col] = entries[row][9-col]
            }
        }
        entries = newEntries

    }
    fun flipVertical() {
        val newEntries = Array(10) { j -> Array<Char>(10){ i -> '.'} }
        for (row in 0 .. 9) {
            for (col in 0 .. 9) {
                newEntries[row][col] = entries[9-row][col]
            }
        }
        entries = newEntries
    }

    fun matchesTop(other: Tile): Boolean {
        if (number == other.number) return false
        for (col in 0 .. 9) {
            if (entries[0][col] != other.entries[9][col]) return false
        }
        return true
    }

    fun matchesBottom(other: Tile): Boolean {
        if (number == other.number) return false
        for (col in 0 .. 9) {
            if (entries[9][col] != other.entries[0][col]) return false
        }
        return true
    }

    fun matchesLeft(other: Tile): Boolean {
        if (number == other.number) return false
        for (row in 0 .. 9) {
            if (entries[row][0] != other.entries[row][9]) return false
        }
        return true
    }

    fun matchesRight(other: Tile): Boolean {
        if (number == other.number) return false
        for (row in 0 .. 9) {
            if (entries[row][9] != other.entries[row][0]) return false
        }
        return true
    }

    fun matches(other: Tile, direction: Dir): Boolean {
        if (number == other.number) return false

        for (change in 0 .. 9) {
            when (direction) {
                Dir.TOP -> if (entries[0][change] != other.entries[9][change]) return false
                Dir.LEFT -> if (entries[change][0] != other.entries[change][9]) return false
                Dir.BOTTOM -> if (entries[9][change] != other.entries[0][change]) return false
                Dir.RIGHT -> if (entries[change][9] != other.entries[change][0]) return false
            }
        }
        return true
    }
    fun matchesAny(other: Tile): Boolean {
        return Dir.values().count{ matches(other,it) } > 0
    }

    fun copyOf(): Tile {
        val copyOfEntries = entries.copyOf()
        return Tile(number, copyOfEntries)
    }

    fun matchesOtherTileInDirection(otherTile: Tile, direction: Dir): Boolean {
        val otherRotAndFlips = getAllRotationsAndFlips(otherTile)

        for (otherRotAndFlip in otherRotAndFlips) {
           if (matches(otherRotAndFlip, direction)) return true
        }

        return false
    }

    fun setNonMatchingEdges(tiles: List<Tile>) {
        nonMatchingEdges.addAll(Dir.values())
        for (dir in Dir.values()) {
            for (curTile in tiles) {
                if (curTile.number == number) continue
                if (matchesOtherTileInDirection(curTile, dir)) {
                    nonMatchingEdges.remove(dir)
                    break
                }
            }
        }
    }

}

fun solveItDayTwentyPartOne(lines: List<String>): Int {
    val tileList = mutableListOf<Tile>()
    var curTile = Tile(0, Array<Array<Char>>(10) { j -> Array<Char>(10){ i -> '.'} })
    var tileRow = 0

    for (line in lines) {
        if (line.isEmpty()) {
            tileList.add(curTile)
            //tileList.addAll(getAllRotationsAndFlips(curTile))
        } else if (line.startsWith("Tile")) {
            val tileNumber = line.removePrefix("Tile ").removeSuffix(":").toInt()
            curTile = Tile(tileNumber, Array<Array<Char>>(10) { j -> Array<Char>(10){ i -> '.'} })
            tileRow = 0
        } else {
            for ((across, char) in line.withIndex()) {
                curTile.entries[tileRow][across] = char
            }
            tileRow++
        }
    }
    tileList.add(curTile)
    //tileList.addAll(getAllRotationsAndFlips(curTile))

    var filteredCornerList = tileList.filter{ hasMaxMatchingSides(it, tileList, 2)}.flatMap { getAllRotationsAndFlips(it)}.toMutableList()
    println("filtered len = ${filteredCornerList.size}")

    var filteredEdgeList = tileList.filter{ hasMaxMatchingSides(it, tileList, 3)}.flatMap { getAllRotationsAndFlips(it)}.toMutableList()
    println("filtered len = ${filteredEdgeList.size}")

    var filteredInnerList = tileList.filter{ hasMaxMatchingSides(it, tileList, 4)}.flatMap { getAllRotationsAndFlips(it)}.toMutableList()
    println("filtered len = ${filteredInnerList.size}")

    val allTiles = mutableListOf<Tile>()
    allTiles.addAll(filteredCornerList)
    allTiles.addAll(filteredEdgeList)
    allTiles.addAll(filteredInnerList)
    for (tile in filteredCornerList) {
        tile.setNonMatchingEdges(allTiles)
        if (tile.nonMatchingEdges.size != 2) println ("bummer")
    }
    for (tile in filteredEdgeList) {
        tile.setNonMatchingEdges(allTiles)
        if (tile.nonMatchingEdges.size != 1) println ("bummer")
    }
//    for (tile in filteredInnerList) {
//        tile.setNonMatchingEdges(allTiles)
//        if (tile.nonMatchingEdges.size != 0) println ("bummer")
//    }

//    for (edge in filteredEdgeList) {
//        println(edge.nonMatchingEdges)
//    }

    val bigPicture = Array(12) { arrayOfNulls<Tile>(12) }
    val finalbigPicture = Array(12) { arrayOfNulls<Tile>(12) }
    //val posStarts = filteredCornerList.filter { it.nonMatchingEdges.equals(setOf(Dir.TOP, Dir.LEFT))}




//    fun recursiveMatch(pos: Int, curBigPicture: Array<Array<Tile?>>, finalBigPicture: Array<Array<Tile?>>,
//                       corners: MutableList<Tile>, edges: MutableList<Tile>, inner: MutableList<Tile>): Boolean {


    // val ret = recursiveMatch(0, bigPicture, finalbigPicture, filteredCornerList, filteredEdgeList, filteredInnerList)
//  println ("recursive match result = $ret")



    for (row in 0..11) {
        for (col in 0..11) {
            //println("$row, $col")

            val tileSet = findMatch(row, col, bigPicture,
                        filteredCornerList, filteredEdgeList, filteredInnerList)
            if (tileSet.isNotEmpty()) {
                //println("found with size ${tileSet.size}")
            } else {
                println("not found")
            }
            val tile = tileSet.getOrNull(tileSet.lastIndex)
            filteredCornerList.removeIf { it.number == tile?.number }
            filteredEdgeList.removeIf { it.number == tile?.number }
            filteredInnerList.removeIf { it.number == tile?.number }

            bigPicture[row][col] = tile
        }
    }

//    for (row in 0 .. 11) {
//        for (col in 0 .. 11) {
//            print("${bigPicture[row][col]?.number},")
//        }
//        println()
//    }

    val bigGrid = Array(96) { j -> Array(96){ i -> '.'} }

    // val newEntries = Array(10) { j -> Array<Char>(10){ i -> '.'} }

    for (row in 0 .. 11) {
        for (col in 0 .. 11) {
            val currentTile = bigPicture[row][col]
            for (across in 1 .. 8) {
                for (down in 1 .. 8) {
                    val targetColumn = col * 8 + across - 1
                    val targetRow = row * 8 + down - 1
                    bigGrid[targetRow][targetColumn] = currentTile!!.entries[down][across]
                }
            }
        }
    }

//    bigPicture[0][0]?.print()
//    bigPicture[0][1]?.print()
//
//    for (row in 0 .. 95) {
//        for (col in 0 ..95) {
//            print(bigGrid[row][col])
//        }
//        println()
//    }

    var numMonsters = searchGridForSeaMonsters(bigGrid)
    println("num monsters == $numMonsters")
    var numWaves = bigGrid.flatMap{ it.asList() }.count { it == '#'}
    println ("Num waves = $numWaves")

    var curGrid: Array<Array<Char>> = bigGrid

    (1 .. 3).forEach { i ->
        curGrid = rotateBigGrid90Clockwise(curGrid)
        numMonsters = Math.max(numMonsters, searchGridForSeaMonsters(curGrid))
        println("num monsters == $numMonsters")
    }

    curGrid = flipBigGridHorizontal(curGrid)
    numMonsters = Math.max(numMonsters, searchGridForSeaMonsters(curGrid))
    println("num monsters == $numMonsters")

    (1 .. 3).forEach { i ->
        curGrid = rotateBigGrid90Clockwise(curGrid)
        numMonsters = Math.max(numMonsters, searchGridForSeaMonsters(curGrid))
        println("num monsters == $numMonsters")
    }

    return numWaves - (numMonsters * 15)


//    val topLeftTile = findMatch(0, 0, bigPicture,
//            filteredCornerList, filteredEdgeList, filteredInnerList)
//
//    if (topLeftTile == null) println ("bummer")
//    topLeftTile?.print()
//    bigPicture[0][0] = topLeftTile
//
//    val nextTile = findMatch(0, 1, bigPicture,
//            filteredCornerList, filteredEdgeList, filteredInnerList)
//
//    if (nextTile == null) println ("bummer")
//    nextTile?.print()

    // println ("Num tiles = ${tileList.size}")

    //return filteredCornerList.fold(1L) { cur, new -> cur * new.number.toLong() }
}

fun getAllRotationsAndFlips(curTile: Tile): List<Tile> {
    val tileList = mutableListOf<Tile>()

    tileList.add(curTile.copyOf())

    (1 .. 3).forEach { i ->
        curTile.rotate90Clockwise()
        tileList.add(curTile.copyOf())
    }

    curTile.flipHorizontal()
    tileList.add(curTile.copyOf())

    (1 .. 3).forEach { i ->
        curTile.rotate90Clockwise()
        tileList.add(curTile.copyOf())
    }
    return tileList
}

fun hasMaxMatchingSides(tile: Tile, tiles: List<Tile>, max: Int): Boolean {

    val rotAndFlips = getAllRotationsAndFlips(tile)

    var maxMatchingSides = 0
    for (rotAndFlip in rotAndFlips) {
        val numSidesThatMatch = numSidesThatMatch(rotAndFlip, tiles)
        maxMatchingSides = Math.max(maxMatchingSides, numSidesThatMatch)
    }

    return maxMatchingSides == max

}


fun numSidesThatMatch(tile: Tile, tiles: List<Tile>): Int {

    var numSidesThatMatch = 0

    for (dir in Dir.values()) {
        for (curTile in tiles) {
            if (curTile == tile) continue
            if (tile.matchesOtherTileInDirection(curTile, dir)) {
                numSidesThatMatch += 1
                break
            }
        }
    }

    return numSidesThatMatch
}


fun setNonMatchingEdges(tile: Tile, tiles: List<Tile>) {
    for (dir in Dir.values()) {
        for (curTile in tiles) {
            if (curTile == tile) continue
            if (tile.matchesOtherTileInDirection(curTile, dir)) {
                tile.nonMatchingEdges.add(dir)
                break
            }
        }
    }
}

fun findMatch(row: Int, col:Int, bigPicture: Array<Array<Tile?>>,
        corners: List<Tile>, edges: List<Tile>, inner: List<Tile>): List<Tile> {

    val topLeftCornerDirs = setOf(Dir.TOP, Dir.LEFT)
    val topRightCornerDirs = setOf(Dir.TOP, Dir.RIGHT)
    val bottomLeftCornerDirs = setOf(Dir.BOTTOM, Dir.LEFT)
    val bottomRightCornerDirs = setOf(Dir.BOTTOM, Dir.RIGHT)

    val topDirs = setOf(Dir.TOP)
    val leftDirs = setOf(Dir.LEFT)
    val rightDirs = setOf(Dir.RIGHT)
    val bottomDirs = setOf(Dir.BOTTOM)

    val emptyDirs = setOf<Dir>()

    // do corners
    if (row == 0 && col == 0) {
        return findFineMatch(topLeftCornerDirs, null, null, corners)
    } else if (row == 0 && col == 11) {
        return findFineMatch(topRightCornerDirs, null, bigPicture[0][10], corners)
    } else if (row == 11 && col == 0) {
        return findFineMatch(bottomLeftCornerDirs, bigPicture[10][0], null, corners)
    } else if (row == 11 && col == 11) {
        return findFineMatch(bottomRightCornerDirs, bigPicture[10][11], bigPicture[11][10], corners)
    } else if (col == 0) { // left edge
        return findFineMatch(leftDirs, bigPicture[row - 1][0], null, edges)
    } else if (col == 11) {
        return findFineMatch(rightDirs, bigPicture[row - 1][11], bigPicture[row][10], edges)
    } else if (row == 0) {
        return findFineMatch(topDirs, null, bigPicture[0][col- 1], edges)
    } else if (row == 11) {
        return findFineMatch(bottomDirs, bigPicture[10][col], bigPicture[row][col - 1], edges)
    } else { // inner case
        return findFineMatch(emptyDirs, bigPicture[row-1][col], bigPicture[row][col - 1], inner)
    }

    return mutableListOf<Tile>() // not found

}

fun findFineMatch(unmatchedDirs: Set<Dir>, topTile: Tile?, leftTile: Tile?, tiles: List<Tile>) : List<Tile> {

    var ret = mutableListOf<Tile>()
    for (tile in tiles) {
        val dirMatch = tile.nonMatchingEdges.equals(unmatchedDirs)
        val topMatch = topTile == null || tile.matchesTop(topTile)
        val leftMatch = leftTile == null || tile.matchesLeft(leftTile)
        if (dirMatch && topMatch && leftMatch && !tile.used) {
            ret.add(tile)
        }
    }
    return ret
}

fun recursiveMatch(pos: Int, curBigPicture: Array<Array<Tile?>>, finalBigPicture: Array<Array<Tile?>>,
                   corners: MutableList<Tile>, edges: MutableList<Tile>, inner: MutableList<Tile>): Boolean {

    //println("at pos $pos")

    val tileSet = findMatch(pos / 12, pos % 12, curBigPicture,
            corners, edges, inner)
    if (tileSet.isEmpty()) {
        //println("no tiles found at at pos $pos")
        return false// no match found on this route
    }
    println("${tileSet.size} tiles found at at pos $pos")

    // foun d a match in final pos we are finished!
    if (pos == 143) {
        curBigPicture[pos / 12][pos % 12] = tileSet[0]

        for (row in 0 .. 11) {
            for (col in 0 .. 11) {
                finalBigPicture[row][col] = curBigPicture[row][col]
            }
        }
        return true
    }

    for (tile in tileSet) {

        curBigPicture[pos / 12][pos % 12] = tile

        if (tile.isCorner()) {
            corners.filter { it.number == tile.number}.forEach {it.used = true}
        } else if (tile.isSide()) {
            edges.filter { it.number == tile.number}.forEach {it.used = true}
        } else {
            inner.filter { it.number == tile.number}.forEach {it.used = true}
        }

        val ret = recursiveMatch(pos + 1, curBigPicture, finalBigPicture, corners, edges, inner)

        if (tile.isCorner()) {
            corners.filter { it.number == tile.number}.forEach {it.used = false}
        } else if (tile.isSide()) {
            edges.filter { it.number == tile.number}.forEach {it.used = false}
        } else {
            inner.filter { it.number == tile.number}.forEach {it.used = false}
        }

        curBigPicture[pos / 12][pos % 12] = null

        if (ret) return true
    }

    return false

//    val tile = tileSet.getOrNull(tileSet.lastIndex)
//    filteredCornerList.removeIf { it.number == tile?.number }
//    filteredEdgeList.removeIf { it.number == tile?.number }
//    filteredInnerList.removeIf { it.number == tile?.number }
//

}

fun rotateBigGrid90Clockwise(bigGrid: Array<Array<Char>>): Array<Array<Char>> {
    val newGrid = Array(96) { j -> Array(96){ i -> '.'} }
    for (row in 0 .. 95) {
        for (col in 0 .. 95) {
            newGrid[row][col] = bigGrid[95 - col][row]
        }
    }
    return newGrid
}

fun flipBigGridHorizontal(bigGrid: Array<Array<Char>>): Array<Array<Char>> {
    val newGrid = Array(96) { j -> Array<Char>(96){ i -> '.'} }
    for (row in 0 .. 95) {
        for (col in 0 .. 95) {
            newGrid[row][col] = bigGrid[row][95-col]
        }
    }
    return newGrid
}


//"                  # "             20 across,  3 down
//"#    ##    ##    ###"
//" #  #  #  #  #  #   "

val monsterStrings = listOf("                  # ", "#    ##    ##    ###", " #  #  #  #  #  #   ")


fun searchGridForSeaMonsters(bigGrid: Array<Array<Char>>): Int {
    var seaMonsterCount = 0
    for (row in 0 .. 93) {
        for (col in 0 .. 76) {
            if (isSeaMonsterAt(row, col,bigGrid)) ++ seaMonsterCount
        }
    }
    return seaMonsterCount
}

fun isSeaMonsterAt(row: Int, col: Int, bigGrid: Array<Array<Char>>): Boolean {
    for ((curRow, monStr) in monsterStrings.withIndex()) {
        for ((curCol, monChar) in monStr.withIndex()) {
            if (monChar == '#' && bigGrid[row + curRow][col + curCol] != '#') return false
        }
    }
    return true
}





