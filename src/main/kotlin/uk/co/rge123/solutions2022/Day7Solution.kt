package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines

data class File(val name: String, val size: Int)
data class Dir(val name: String, val files: MutableList<File>, val subDirs: MutableList<Dir>, val parent: Dir?, var size: Int) {
    fun containsFile(filename: String): Boolean {
        for (file in files) {
            if (file.name == filename) return true
        }
        return false
    }
    fun containsDir(dirname: String): Boolean {
        for (dir in subDirs) {
            if (dir.name == dirname) return true
        }
        return false
    }
}

//$ cd /
//$ ls
//dir brdsppd
//dir dnjqmzgg
//126880 fmftdzrp.fwt
//173625 hhfqgzfj.qvt
//dir lbbcfjl
//dir mzdqcb
//dir npppw
//dir plmb
//6337 rfgtcj.tdn
//dir szfw
//230140 vmc.cdf
//$ cd brdsppd

enum class Mode {COMMAND, LIST}

fun main (args: Array<String>) {
    val root = Dir("root", mutableListOf(), mutableListOf(), null, 0)
    var curDir: Dir = root
    var curMode: Mode = Mode.COMMAND

    val lines = splitFileToLines("daySeven")

    for (line in lines) {
        if (line.startsWith("$")) {
            curMode = Mode.COMMAND
            val commands = line.split(" ")
            if (!line.endsWith("/")) {
                if (commands.size == 2 && commands[1] == "ls") {
                    curMode = Mode.LIST
                } else if (commands.size == 3 && commands[1] == "cd") {
                    val dirToGo = commands[2]
                    if (dirToGo == "..") {
                        curDir = curDir.parent!!
                    } else {
                        for (dir in curDir.subDirs) {
                            if (dir.name == dirToGo) {
                                curDir = dir
                            }
                        }
                    }
                }
            }
        } else { // list mode
            val fileWords = line.split(" ")
            if (fileWords[0] == "dir") {
                val dirName = fileWords[1]
                if (!curDir.containsDir(dirName)) {
                    curDir.subDirs.add(
                        Dir(
                            fileWords[1], mutableListOf(), mutableListOf(), curDir, 0
                        )
                    )
                }
            } else {
                val fileWords = line.split(" ")
                val fileSize = fileWords[0].toInt();
                val fileName = fileWords[1]
                if (!curDir.containsDir(fileName)) {
                    curDir.files.add(File(fileName, fileSize))
                }
            }
        }
    }

    val totalSize = traverseAndFill(root)
    val freeSize = 70000000 - totalSize
    val required = 30000000 - freeSize

    println ("free = $freeSize, =required =  $required")

    val answer = traverseAndFindSmallestDir(root, required, totalSize)

    println ("poop = $answer")
}

fun traverseAndFindSmallestDir(root: Dir, required: Int, totalSize: Int): Int {
    return sploosh(root, required, totalSize)
}

fun sploosh(dir: Dir, required: Int, smallestSoFar: Int): Int {
    if (dir.subDirs.isEmpty()) {
        if (dir.size >= required && dir.size < smallestSoFar) {
            return dir.size
        } else {
            return smallestSoFar
        }
    }

    var smallest = smallestSoFar
    if (dir.size >= required && dir.size < smallest) {
        smallest = dir.size
    }

    for (subDir in dir.subDirs) {
        val smallestSubDir = sploosh(subDir, required, smallest)
        if (smallestSubDir < smallest) {
            smallest = smallestSubDir
        }
    }
    return smallest
}




//fun traverseAndFindDirs(root: Dir, maxSize: Int): Int {
//    return tafd(root, maxSize)
//}
//
//fun tafd(dir: Dir, maxSize: Int): Int {
//    var size = 0
//    if (dir.size <= maxSize) {
//        size += dir.size
//    }
//    for (subDirs in dir.subDirs) {
//        size += tafd (subDirs, maxSize)
//    }
//    return size
//}



fun traverseAndFill(root: Dir): Int {
    return taf(root)
}

fun taf(dir: Dir): Int {
    var size = 0
    for (curFile in dir.files) {
        size += curFile.size
    }
    for (subDirs in dir.subDirs) {
        size += taf (subDirs)
    }
    dir.size = size
    return size
}


//fun contains (p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
//    return (p1.first <= p2.first && p1.second >= p2.second) || (p2.first <= p1.first && p2.second >= p1.second)
//}
//
//fun overlaps (p1: Pair<Int, Int>, p2: Pair<Int, Int>): Boolean {
//    return ((p1.second >= p2.first) && (p1.first <= p2.second))
//}



//fun solveItDayTwoPartOne(lines: List<String>) = lines.filter { getPasswordInfo(it).isValid() }.count()
//
//fun getPasswordInfo(input: String): PasswordInfo {
//    val words = input.split(" ")
//    val rangeNumbers = words[0].split("-")
//    return PasswordInfo(rangeNumbers[0].toInt(), rangeNumbers[1].toInt(), words[1][0], words[2])
//}
//
//data class PasswordInfo(val rangeStart: Int, val rangeEnd: Int, val checkedChar: Char, val password: String) {
//    fun isValid() = password.count { it == checkedChar } in rangeStart..rangeEnd
//}