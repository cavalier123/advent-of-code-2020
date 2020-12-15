package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Integer.min

fun main() {
    val filename = "DayFourteenData"
    val solution = solveDayFourteenPartOne(splitFileToLines(filename))
    print("Solution is $solution")
}

//mask = 111X01X0100X111X1X010XX01X1111101100
//mem[2201] = 2380
//mem[47171] = 21857
//mem[6021] = 7287595
//mem[49580] = 3837
//mem[65412] = 28041507
//mem[32992] = 99064028

// 8471403462063

fun solveDayFourteenPartOne(lines: List<String>): Long {

    var curMask = ""
    val memVals = HashMap<Int, Long>()

    for (line in lines) {
        val parts = line.split("=")
        if (parts[0].trim() == "mask") {
            curMask = parts[1].trim()
        } else {
            val indexParts = parts[0].trim().split("[")
            val index = indexParts[1].removeSuffix("]").toInt()
            val value = parts[1].trim().toInt()
            setVal(memVals, index, value, curMask)
        }
    }
    return memVals.values.sum()
}

fun setVal(memVal: HashMap<Int, Long>, index: Int, value: Int, mask: String) {
    val binStr = Integer.toBinaryString(value).padStart(36, '0')
    val newValue = applyMask(binStr, mask)
    val newValueAsLong = newValue.toLong(2)
    memVal[index] = newValueAsLong
}


fun applyMask(binStr: String, mask: String): String {
    return binStr.mapIndexed { i, c -> if (mask[i] != 'X') mask[i] else c }.joinToString("")
}