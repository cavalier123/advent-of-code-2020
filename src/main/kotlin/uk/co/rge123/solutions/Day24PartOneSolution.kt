package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "Day24Data"
    val solution = solveItDay24PartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDay24PartOne(lines: List<String>): Int {

    val tileSet = mutableSetOf<Pair<Int, Int>>()

    for (line in lines) {
        println(line)
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
            print ("$dir, ")
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
        println("")

        val coord = Pair (curAcross, curDown)
        if (tileSet.contains(coord)) {
            tileSet.remove(coord)
        } else {
            tileSet.add(coord)
        }

    }

    return tileSet.size
}