package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

const val directions = "9L2R19R17L43L44L33L9L25R31L10L3R33R11L48R32L46R30L5L40L13R37R2R34L11L30L15R11R3L31R26R44L5R42L10L36L8R31L45L36R26L17L37L36L50L28L40L14L46L37L38R11L14L16L9L21R27R5L35R34L11R32R50R10L6R47R49L32R43R7R15L29R32R2L3L32L46R7L15R23L48R31R19L5L32R13R45L31L30R18L22L36L14R50L19R18L47L40R23R8R14R24R25L34R19L38R48R7L31R23L5L17L28R12R40R20L15R42L9L3R29L4R20R45R21L36L30R48L38L36L32R20L13L26R15R37R17L11R23R4R45L22L26L17R14R47L30R14L42L44L17L1L2R11R9L50R22R6R36R3L47L45L8R45R46L26L31R32L21R44L26R12R33R11L3L27R28R37R14L28R29R30L10R34R20R10R6L21L15L28R33R35L50R2L30L9R5R9L36L3L20R26L5L41L35R3R12L17L38L40L46L17L45R35R16R42L22L27L15R46R6R38L1L30L36R38L11L22L30L37L31L9R29R45L16L23R36R30R28R15L23R14L49R47L39L31R33L46R11L36L1R34R13L50L14R11L30R35R48R34R2R22R46R44L39R35R50R7R29L21R43R16R48L15L19R5R35L3R20R1L9R10L19L22R36R40R14L50L26R27L26L46L39L11R32L48R32L39L24L35R41L37R40R1L10L10R37R16R33L45L1L27L24L42L6L50R38R40L35L42R32R23L43L41L1L32L30R38L2R31L26L19L21L19R34L16L32L6R50L30L24R7L35L37R33R20L42L48R42L41L8R45R31L37R44R22L20L49R37L49L42R1L28L32R43L3R20R17L36L38L14L14L25R16R43R37R21R25L13R36L37L18R12L32R1R36L17R11R32R43R46L35R46R5R15L15L16L42L41L32R15R18L40R12L15R46R2L35L42R26L48L31R48L1R48L48R48L45L4R18R22L34R42L40L17R22R20R46L11R48R16R21R25L3L19R19L41L18L48L16L32R6L8R26L15R29L20R42R4L19L23L15R40L39L17L21R45L31L23R17L1L34R21L16R37L15R40R9L33R43R45R23L41R38L46R25R42L47R33R50L32R23R1R28R18L22L41R45L39R45L15L28R11R33L50R10R50R37R40R32R36L46L14R6L28L23R24L39L3L30L46L50L2L30L19R36L9R21R45L36R32R8R24R44R29R20L24R16L25L8R3R26L49R35R23L33L13R2L43L50R9L12L47R20R21L26L27L38L35L29R25R46R39L9R49R8R36L49L18L36R15L41L22R32R28R2L48R9L49L16R39L36R42R29L35L17R30L30R13L28R42L44R50R33L32R50R16L26R39L2R2L40L27R46L35L33R6L13L33R15R50L38R40R3R1L26R13R14L14R29R34L37L30L24R35R49R27R36L32R8R28R3L40L17L28L20R11L45L19R36R24R2R33R15R32R39R6L24L48L1L21R20L19R31R33R11R31R47L33R20R19L20L36R42L7L23L31L1R2L27L48L10L9L3R28L25L42R22R19L3R40R45L49R20R25R31R18R15R16L45L14L18L47L9L12L10R13R6L28L26L11L5L25L38R25L5R39L37L31R45L28R42R7L19L37L32L9L36L45R8R14R5R15L26R33R24L16L4L43L37L47R13R22L48L46L41R25R30R33L46L1L2R37R31L28R40L27R33R14R13R43L37R12L25L43R10L5L15L13R31R10L2L14L46R34R20R2R20R4R39L25R14R15L38L28R30R3L16L37R16L13L49L40L43L2R42R25R3L30R36L44L43R18L9L31L26R23R38R22R40L47R37L40R23L25L8R27R10L20R11L41L45L22L18L13L45L24L48R12R15L45R14R42R13R37R11L18L38L1L38R22R12L30L47L25L42R19L3L40R41L43R22R15R32L20R5R50L39L30R16R8L7R14R26R30R44R46L32R12R25R47L13L28R2L36L43R36R8R24L38R41L46L2L13L49R9R33R45L19R1R8R47L3L26R35R45L15R26L30R16R34L26L45L7R50R46R39L1R26L14R29L44L37L24L4L21L15L36R31L30R8R17L15L19L27R4L19R43R5R12L47R31L18R29R42R21L24L22L44L4L1L19R14L38R31R30L3L14L4L45R39R17L17L11R28L3R6L15L25L11R33R4L5R38L46L33R9L39R19R48L41L20L41L11L43R41R9R16L32R16L17R22R9R9L22R42R5R44L44L5R11R35R48L6R1L26L4L27L23R7L50R30R23R31R29R35L20R17L31R38L50L2R29R29R1R44L48R17L6R1L50R19R46R43R11R33L18L2R48L25R3L29R9R27R15L35R42R40L11L21R39L26R48L39L7L8L33R46L3R7R34L48R46R9R37L7R13R36R46R48L28R20L19R28R5L33R19L2L10R28L23L10L28L12R19L6L45L31R40L42R24L36R28R46R23L33R15L14L48R41L5R45L29L46R2R15R21L13L16R50L31R26R47L18L28R28R44L17R9R27L47L33L41R23L25L16R20L40L33R42R4R47L35R49R26R7R26L20L26L8L32L27R3L8L17L42R45L6L34R7R46R8L49L41L46R46L38L31L6R47L33L4L11L37L44L39R45L10L47R42R7R47L22L9R17R47R25L39R24L50L39R36L6L18R13L7L31R9R36R24R33R39L18L7R5L7L30L21R42R25L7R5R11L31R9L11R13L9L7R42L13L16L20R50L25R38L36R4L35R30R46L47R44L33L41L13L5R48L50L49R44R39R49R28L44R17R39R7R47L26L32R28L48L9R29L7R20L8R13L20L1R32R15L5L2L48L3L24L1R50R12L6R50R39R46R11R45L25R1R19L20L26L11L8R48L30R6R13R1L10L29L40R6R9L28L46L24R41R17L24R4R11R27L49L46L50L39L27L9L2R2L7R26R20L17L27R20L5L37L25L39L11L49R3R38L43R2L14R28L4L34R32R10L13L25R13L34L32L46L34R32L48L13L39L3R11L14R20R30R8R24L15R50R19R30L14R49R38L27L12R2L29L29R21R26R9L26L34L20L49L21L43R11R8R23R3L2L7R49R44R35R8R18R40R42L7L44L24L13R19L30R36R19R25R2L48L7R25L33R29L15L42R35R3R15R26R6R48R17R5L14R5R27L25R14R36R23R45R12R17L49L38R2R14L31R8L39L48R9L15L36L38R5R16L19L45R11L8R49R31R32R27R11R16L41R46R8L14R25L40R48L11R9L50L27L9L15R49L40R36R1L45R20R36L24L49L15L42L6L50L48L24L21L30L32L50R23R10R20R27R31L50R23R26L19R41R35R33L35R32L2R27L39R25L45R46L45R45R38R28R3R29L8R39R19L19R6L22R14R46L25L41L29L1L34L8L48R30R13L30L17R27R24R17L10R6R38L25R32L19L11L35R14L7L15R12L2R29R5L17L44R34R48R26L15L16R38L20R23L25R28R14L36L30L17L21R25L50L38R7R7R33R4L34L40R45R13L48L31R13L9R47L37R21L23R6L17R18R2R44R17L45L11L25L15L6L28R35R41L46R15R8L25R43R31L43L16R28L45R35R5L2R43L6R34R1L13R21L27R30R10L43R1L4R7L40R34R4L30R46R28L6L46L48L14R19R23R5L10L12R12L13L31L17L16L47L23R11R42R4L49L49L33R43L45R10R38L8R9R24R22R20L13L5L1R31L30R19L5R13L42R19L36R12R5L50L29L20L34L35R42L16R5R26R19L19L1L20R23R37L27R11R38L39R32R2L1L40L19R26R33R10R11L10R43R44L3L7R46R14R45L22L10L49L9R15R35L45R29L14L15L49L43L45R26R6R38R9R24R44L44L50R50L46L41L5L1R19R39R9R20L29L4L19R27L40R49R25R29L14L50L28L2L46R44R6L34L17L30R29L38L13R29R43R1L48R31R30L30L13R30L14R44L38R10L13L43L44R33L45R1L12R8R2R14R37L24L48R10R38R41R9L36R8R7R26R11L7R19R17L17L5L29R41L50R34L6L37R23R20R48L22R36R5L35R37R49L32L18L31L14L47R39R49L42L16L38R24R27L19R19R17L42R22L16L15L32R22R37L37R1L38L33R10R4L46R46R19R10L43R4L20R46L41L42R25R21R13L33L19R19L45R6R31L28R4L7R23L28L32R23R8R5L35L7L26L17R36R26R49L49L2R13R50L29R4R38R33R7R33L47L16L46L34L20L1L37R18L18R24R11R44R35L30L34L46L24R17L3L4R2L45R34L36L4L41R12R34R31L30L42R43R31R17R38R3L37R9R46R1L48R38L45R26L43R31L12L38L24R48L38R42R5L48R17R13L2R43L32R40R36R3L29R48R45R8L29L33R44R46R18R3L20R27L10L27L24L49L37L11L5L43L45L10R11R41L24R43R44L25R38L43R45R41R45R37L37L15R20L31L30R36L3L47L39R10L23L44L30R19L8L14L13R42R2R2L40L38L50L44R43L43L32L46R2L20R15L9L33L14R35L45R44L31L34L2L8R22R41R45R19L11R1L17L29R26L14R20L9L28R25L19L12L8L14L14R43L7R7R3R38"

// const val directions = "9L2R19R17L43L44L33L9L25R31"
//const val directions = "10R5L5R10L4R5L5"

enum class SquareType {SPACE, EMPTY, ROCK;
    companion object {
        fun getSquareType(type: Char): SquareType {
            return when (type) {
                '.' -> SPACE
                '#' -> ROCK
                ' ' -> EMPTY
                else -> EMPTY
            }
        }
        fun getChar(type: SquareType): Char {
            return when (type) {
                SPACE -> '.'
                ROCK -> '#'
                EMPTY -> '-'
            }
        }
    }
}
enum class DIRECTION {UP, DOWN, LEFT, RIGHT;
    //   Facing is 0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^).

    companion object {
        fun getFacing(dir: DIRECTION): Int {
            return when (dir) {
                UP -> 3
                DOWN -> 1
                LEFT -> 2
                RIGHT -> 0
            }
        }
        fun getDir(dir: Char): DIRECTION {
            return when (dir) {
                'L' -> LEFT
                'R' -> RIGHT
                'U' -> UP
                'D' -> DOWN
                else -> DOWN
            }
        }
        fun getDirAfterTurn(dir: DIRECTION, turn: Char): DIRECTION {
            return when (dir) {
                UP -> { if(turn == 'L') LEFT else RIGHT }
                DOWN -> { if(turn == 'L') RIGHT else LEFT }
                LEFT -> { if(turn == 'L') DOWN else UP }
                RIGHT -> { if(turn == 'L') UP else DOWN }
            }
        }
    }
}

const val NUMBER_ROWS = 200 + 2
const val NUMBER_COLS = 150 + 2
const val SIDE_LEN = 50

//const val NUMBER_ROWS = 16 + 2
//const val NUMBER_COLS = 12 + 2
//const val SIDE_LEN = 4

val map = Array(NUMBER_COLS) {Array(NUMBER_ROWS) { SquareType.EMPTY} }

fun main() {
    val filename = "day22"
    val solution = solveItDay22PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

//enum class DIR {UP, DOWN, LEFT, RIGHT}
//enum class SQUARE {SPACE, EMPTY, ROCK}
//
//data class MapSquare(val point: Point)
//
//val map = mutableSetOf<Point>()
//
fun move(curPoint: Point, dir: DIRECTION, steps: Int): Pair<Point, DIRECTION> {
    println ("Move from, point = $curPoint, dir = $dir, steps = $steps")
    val retPoint = Point(curPoint.x, curPoint.y)
    var curDir = dir
    for (step in 1.. steps) {
        var newDir = curDir
        //println ("Move from, point = $retPoint, dir = $curDir, CURSTEP = $step")
        when (curDir) {
            DIRECTION.UP -> {
                var prosY = retPoint.y - 1
                val curType = map[retPoint.x][prosY]
                if (curType == SquareType.EMPTY) {
                    var wrapPoint = Point(0, 0)
                    if (retPoint.x in 1..SIDE_LEN) {
                        val across = retPoint.x
                        wrapPoint = Point(SIDE_LEN + 1, SIDE_LEN + across)
                        newDir = DIRECTION.RIGHT
                    } else if (retPoint.x in SIDE_LEN + 1..2 * SIDE_LEN) {
                        val across = retPoint.x - SIDE_LEN
                        wrapPoint = Point(1, 3 * SIDE_LEN + across)
                        newDir = DIRECTION.RIGHT
                    } else if (retPoint.x in 2 * SIDE_LEN + 1..3 * SIDE_LEN) {
                        val across = retPoint.x - 2 * SIDE_LEN
                        wrapPoint = Point(across, 4 * SIDE_LEN)
                        newDir = DIRECTION.UP
                    }
                    if (map[wrapPoint.x][wrapPoint.y] != SquareType.ROCK) {
                        //println ("Move up and wrapping from $retPoint to $wrapPoint new cuRdIr = $curDir")
                        retPoint.x = wrapPoint.x
                        retPoint.y = wrapPoint.y
                        curDir = newDir
                    } else {
                        // println ("Hit a rock")
                        break
                    }
                } else if (curType == SquareType.SPACE) {
                    retPoint.y--
                    //println("Move up")
                } else if (curType == SquareType.ROCK) {
                    //println ("Hit a rock")
                    break;
                }
            }

            DIRECTION.DOWN -> {
                var prosY = retPoint.y + 1
                val curType = map[retPoint.x][prosY]
                if (curType == SquareType.EMPTY) {
                    var wrapPoint = Point(0, 0)
                    if (retPoint.x in 1..SIDE_LEN) {
                        val across = retPoint.x
                        wrapPoint = Point(2 * SIDE_LEN + across, 1)
                        newDir = DIRECTION.DOWN
                    } else if (retPoint.x in SIDE_LEN + 1..2 * SIDE_LEN) {
                        val across = retPoint.x - SIDE_LEN
                        wrapPoint = Point(SIDE_LEN, 3 * SIDE_LEN + across)
                        newDir = DIRECTION.LEFT
                    } else if (retPoint.x in 2 * SIDE_LEN + 1..3 * SIDE_LEN) {
                        val across = retPoint.x - 2 * SIDE_LEN
                        wrapPoint = Point(2 * SIDE_LEN, SIDE_LEN + across)
                        newDir = DIRECTION.LEFT
                    }
                    if (map[wrapPoint.x][wrapPoint.y] != SquareType.ROCK) {
                        //println ("Move down and wrapping from $retPoint to $wrapPoint new cuRdIr = $curDir")
                        retPoint.x = wrapPoint.x
                        retPoint.y = wrapPoint.y
                        curDir = newDir
                        //println("Move down and wrap")
                    } else {
                        //println ("Hit a rock")
                        break
                    }
                } else if (curType == SquareType.SPACE) {
                    retPoint.y++
                    //println("Move down")
                } else if (curType == SquareType.ROCK) {
                    //println ("Hit a rock")
                    break;
                }
            }
            DIRECTION.LEFT -> {
                var prosX = retPoint.x - 1
                val curType = map[prosX][retPoint.y]
                if (curType == SquareType.EMPTY) {
                    var wrapPoint = Point(0, 0)
                    if (retPoint.y in 1..SIDE_LEN) {
                        val down = retPoint.y
                        wrapPoint = Point(1, 3 * SIDE_LEN + 1 - down)
                        newDir = DIRECTION.RIGHT
                    } else if (retPoint.y in SIDE_LEN + 1..2 * SIDE_LEN) {
                        val down = retPoint.y - SIDE_LEN
                        wrapPoint = Point(down, 2 * SIDE_LEN + 1)
                        newDir = DIRECTION.DOWN
                    } else if (retPoint.y in 2 * SIDE_LEN + 1..3 * SIDE_LEN) {
                        val down = retPoint.y - 2 * SIDE_LEN
                        wrapPoint = Point(SIDE_LEN + 1, SIDE_LEN + 1 -down)
                        newDir = DIRECTION.RIGHT
                    } else if (retPoint.y in 3 * SIDE_LEN + 1..4 * SIDE_LEN) {
                        val down = retPoint.y - 3 * SIDE_LEN
                        wrapPoint = Point(SIDE_LEN + down, 1)
                        newDir = DIRECTION.DOWN
                    }
                    if (map[wrapPoint.x][wrapPoint.y] != SquareType.ROCK) {
                        //println ("Move left and wrapping from $retPoint to $wrapPoint new cuRdIr = $curDir")
                        retPoint.x = wrapPoint.x
                        retPoint.y = wrapPoint.y
                        curDir = newDir
                        //println("Move left and wrap")
                    } else {
                        //println ("Hit a rock")
                        break
                    }
                } else if (curType == SquareType.SPACE) {
                    retPoint.x--
                    //println("Move left")
                } else if (curType == SquareType.ROCK) {
                    //println ("Hit a rock")
                    break;
                }
            }
            DIRECTION.RIGHT -> {
                var prosX = retPoint.x + 1
                val curType = map[prosX][retPoint.y]
                if (curType == SquareType.EMPTY) {
                    var wrapPoint = Point(0, 0)
                    if (retPoint.y in 1..SIDE_LEN) {
                        val down = retPoint.y
                        wrapPoint = Point(2 * SIDE_LEN, 3 * SIDE_LEN + 1 - down)
                        newDir = DIRECTION.LEFT
                    } else if (retPoint.y in SIDE_LEN + 1..2 * SIDE_LEN) {
                        val down = retPoint.y - SIDE_LEN
                        wrapPoint = Point(2 * SIDE_LEN + down, SIDE_LEN)
                        newDir = DIRECTION.UP
                    } else if (retPoint.y in 2 * SIDE_LEN + 1..3 * SIDE_LEN) {
                        val down = retPoint.y - 2 * SIDE_LEN
                        wrapPoint = Point(3 * SIDE_LEN, SIDE_LEN + 1 -down)
                        newDir = DIRECTION.LEFT
                    } else if (retPoint.y in 3 * SIDE_LEN + 1..4 * SIDE_LEN) {
                        val down = retPoint.y - 3 * SIDE_LEN
                        wrapPoint = Point(SIDE_LEN + down, 3 * SIDE_LEN)
                        newDir = DIRECTION.UP
                    }
                    if (map[wrapPoint.x][wrapPoint.y] != SquareType.ROCK) {
                        //println ("Move right and wrapping from $retPoint to $wrapPoint new cuRdIr = $curDir")
                        retPoint.x = wrapPoint.x
                        retPoint.y = wrapPoint.y
                        curDir = newDir
                        // println("Move right and wrap")
                    } else {
                        //println ("Hit a rock")
                        break
                    }
                } else if (curType == SquareType.SPACE) {
                    retPoint.x++
                    //println("Move right")
                } else if (curType == SquareType.ROCK) {
                    //println ("Hit a rock")
                    break;
                }
            }
        }
    }
    // println ("moveTo ret value = $retPoint")
    return Pair(retPoint, curDir)
}

fun solveItDay22PartOne2022(lines: List<String>): Int {

//    for ((rowIndex, line) in lines.withIndex()) {
//        for ((colIndex, char) in line.withIndex()) {
//            println ("col = $colIndex, row = $rowIndex")
//            val curType = SquareType.getSquareType(char)
//            map[colIndex + 1][rowIndex + 1] = curType
//        }
//    }
//
//    for (index in 0 until NUMBER_COLS) {
//        map[index][0] = SquareType.EMPTY
//        map[index][NUMBER_ROWS - 1] = SquareType.EMPTY
//    }
//    for (index in 0 until NUMBER_ROWS) {
//        map[0][index] = SquareType.EMPTY
//        map[NUMBER_COLS - 1][index] = SquareType.EMPTY
//    }
//
//    var curPoint = Point(1, 16)
//    var curDir = DIRECTION.LEFT
//    val rayMap = mutableSetOf<Point>()
//
//    for (step in 1 .. 16) {
//        val move = move(curPoint, curDir, 1)
//        curPoint = move.first
//        curDir = move.second
//        rayMap.add(curPoint)
//    }
//
//    for (rowIndex in 0 until NUMBER_ROWS) {
//        for (colIndex in 0 until NUMBER_COLS) {
//            if (rayMap.contains(Point(colIndex, rowIndex))) print ("X")
//            else print(SquareType.getChar(map[colIndex][rowIndex]))
//        }
//        println()
//    }
//
//    return 1

    println ("num rows = ${lines.size}")
    println ("num cols = ${lines[0].length}")

    var startPoint: Point? = null

    for ((rowIndex, line) in lines.withIndex()) {
        for ((colIndex, char) in line.withIndex()) {
            val curType = SquareType.getSquareType(char)
            map[colIndex + 1][rowIndex + 1] = curType
            if (rowIndex == 0 && curType == SquareType.SPACE && startPoint == null) {
                startPoint = Point(colIndex + 1, 1)
            }
        }
    }

    for (index in 0 until NUMBER_COLS) {
        map[index][0] = SquareType.EMPTY
        map[index][NUMBER_ROWS - 1] = SquareType.EMPTY
    }
    for (index in 0 until NUMBER_ROWS) {
        map[0][index] = SquareType.EMPTY
        map[NUMBER_COLS - 1][index] = SquareType.EMPTY
    }

    var curDir = DIRECTION.RIGHT
    println ("start = $startPoint")

    var curNum = 0
    var curPoint = Point(startPoint!!.x, startPoint!!.y)

//    for (rowIndex in 0 until NUMBER_ROWS) {
//        for (colIndex in 0 until NUMBER_COLS) {
//            if (curPoint == Point(colIndex, rowIndex)) print ("X")
//            else print(SquareType.getChar(map[colIndex][rowIndex]))
//        }
//        println()
//    }

    for (char in directions) {
        if (char.isDigit()) {
            curNum = curNum * 10 + char.digitToInt()
        } else {
            val move = move (curPoint, curDir, curNum)
            curPoint = move.first
            curDir = move.second
            curNum = 0
            curDir = DIRECTION.getDirAfterTurn(curDir, char)

//            for (rowIndex in 0 until NUMBER_ROWS) {
//                for (colIndex in 0 until NUMBER_COLS) {
//                    if (curPoint == Point(colIndex, rowIndex)) print ("X")
//                    else print(SquareType.getChar(map[colIndex][rowIndex]))
//                }
//                println()
//            }
        }
    }
    // And not forgetting
    val move = move (curPoint, curDir, curNum)
    curPoint = move.first
    curDir = move.second

    //   Facing is 0 for right (>), 1 for down (v), 2 for left (<), and 3 for up (^).
    //   The final password is the sum of 1000 times the row, 4 times the column, and the facing.

    val answer = (1000 * curPoint.y) + (4 * curPoint.x) + DIRECTION.getFacing(curDir)

    return answer
}