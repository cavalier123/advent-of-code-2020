package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayTwentyData"
    val solution = solveItDayTwentyPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

enum class Dir {TOP, LEFT, RIGHT, BOTTOM;
    fun rotate90Clockwise() = when(this) {
            TOP -> RIGHT
            RIGHT -> BOTTOM
            BOTTOM -> LEFT
            LEFT -> TOP
        }
    fun flipHorizontal() = when(this) {
        TOP -> BOTTOM
        RIGHT -> RIGHT
        BOTTOM -> TOP
        LEFT -> LEFT
    }
}

data class Tile(val number: Int, var entries: Array<Array<Char>>,
                var nonMatchingEdges: MutableSet<Dir> = mutableSetOf<Dir>(), var used: Boolean = false) {
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
        rotateNonMatchingEdges90ClockWise()
        entries = rotateGrid90Clockwise(entries)
    }
    private fun rotateNonMatchingEdges90ClockWise() {
        val newNonMatchingEdges = mutableSetOf<Dir>()
        for (dir in nonMatchingEdges) {
            newNonMatchingEdges.add(dir.rotate90Clockwise())
        }
        nonMatchingEdges = newNonMatchingEdges
    }

    fun flipHorizontal() {
        flipNonMatchingEdgesHorizontal()
        entries = flipGridHorizontal(entries)
    }
    private fun flipNonMatchingEdgesHorizontal() {
        val newNonMatchingEdges = mutableSetOf<Dir>()
        for (dir in nonMatchingEdges) {
            newNonMatchingEdges.add(dir.flipHorizontal())
        }
        nonMatchingEdges = newNonMatchingEdges
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

    fun matches(other: Tile, direction: Dir) = when (direction) {
                Dir.TOP -> matchesTop(other)
                Dir.LEFT -> matchesLeft(other)
                Dir.BOTTOM -> matchesBottom(other)
                Dir.RIGHT -> matchesRight(other)
            }

    fun matchesAny(other: Tile): Boolean {
        return Dir.values().count{ matches(other,it) } > 0
    }

    fun copyOf(): Tile {
        val copyOfEntries = entries.copyOf()
        return Tile(number, copyOfEntries, nonMatchingEdges.toMutableSet(), used)
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

    tileList.forEach {it.setNonMatchingEdges(tileList)}

    val corners = tileList.filter{ it.isCorner() }.flatMap { getAllRotationsAndFlips(it)}.toMutableList()
    val edges = tileList.filter{ it.isSide() }.flatMap { getAllRotationsAndFlips(it)}.toMutableList()
    val inners = tileList.filter{ it.isInside()}.flatMap { getAllRotationsAndFlips(it)}.toMutableList()

    val bigPicture = Array(12) { arrayOfNulls<Tile>(12) }

    for (row in 0..11) {
        for (col in 0..11) {
            //println("$row, $col")

            val tileSet = findMatch(row, col, bigPicture,
                        corners, edges, inners)
            if (tileSet.isNotEmpty()) {
                //println("found with size ${tileSet.size}")
            } else {
                println("not found")
            }
            val tile = tileSet.getOrNull(0)
            corners.removeIf { it.number == tile?.number }
            edges.removeIf { it.number == tile?.number }
            inners.removeIf { it.number == tile?.number }

            bigPicture[row][col] = tile
        }
    }

    val bigGrid = Array(96) { j -> Array(96){ i -> '.'} }

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

    var numMonsters = searchGridForSeaMonsters(bigGrid)
    println("num monsters == $numMonsters")
    var numWaves = bigGrid.flatMap{ it.asList() }.count { it == '#'}
    println ("Num waves = $numWaves")

    var curGrid: Array<Array<Char>> = bigGrid

    (1 .. 3).forEach { i ->
        curGrid = rotateGrid90Clockwise(curGrid)
        numMonsters = Math.max(numMonsters, searchGridForSeaMonsters(curGrid))
        println("num monsters == $numMonsters")
    }

    curGrid = flipGridHorizontal(curGrid)
    numMonsters = Math.max(numMonsters, searchGridForSeaMonsters(curGrid))
    println("num monsters == $numMonsters")

    (1 .. 3).forEach { i ->
        curGrid = rotateGrid90Clockwise(curGrid)
        numMonsters = Math.max(numMonsters, searchGridForSeaMonsters(curGrid))
        println("num monsters == $numMonsters")
    }

    return numWaves - (numMonsters * 15)
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

fun rotateGrid90Clockwise(grid: Array<Array<Char>>): Array<Array<Char>> {
    val origGridHeight = grid.size
    val origGridWidth = grid[0].size
    val newGrid = Array(origGridWidth) { row -> Array(origGridHeight) { '.' } }
    (0 until origGridHeight).flatMap { row -> (0 until origGridWidth).map { col -> Pair(row, col) }}
            .forEach{newGrid[it.second][it.first] = grid[origGridHeight - 1 - it.first][it.second]}
    return newGrid
}

fun flipGridHorizontal(grid: Array<Array<Char>>): Array<Array<Char>> {
    val gridHeight = grid.size
    val gridWidth = grid[0].size
    val newGrid = Array(gridWidth) { row -> Array(gridHeight) { '.' } }
    (0 until gridHeight).flatMap { row -> (0 until gridWidth).map { col -> Pair(row, col) }}
            .forEach{newGrid[gridHeight - 1 - it.first][it.second] = grid[it.first][it.second]}
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





