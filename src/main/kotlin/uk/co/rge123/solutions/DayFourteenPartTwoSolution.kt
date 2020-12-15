package main.kotlin.uk.co.rge123.solutions

import kotlin.math.pow

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Integer.min

fun main() {
    val filename = "DayFourteenData"
    val solution = solveDayFourteenPartTwo(splitFileToLines(filename))
    print("Solution is $solution")
}

//mask = 111X01X0100X111X1X010XX01X1111101100
//mem[2201] = 2380
//mem[47171] = 21857
//mem[6021] = 7287595
//mem[49580] = 3837
//mem[65412] = 28041507
//mem[32992] = 99064028

fun solveDayFourteenPartTwo(lines: List<String>): Long {

    var curMask = ""
    val memVals = HashMap<Long, Int>()

    for (line in lines) {
        val parts = line.split("=")
        if (parts[0].trim() == "mask") {
            curMask = parts[1].trim()
        } else {
            val indexParts = parts[0].trim().split("[")
            val index = indexParts[1].removeSuffix("]").toInt()
            val value = parts[1].trim().toInt()
            setMemoryValues(memVals, index, value, curMask)
        }
    }
    return memVals.values.map{it.toLong()}.sum()
}

fun setMemoryValues(memVal: HashMap<Long, Int>, index: Int, value: Int, mask: String) {

    val binStr = Integer.toBinaryString(index).padStart(36, '0')
    val newValuePair = applyMaskWithXs(binStr, mask)

    for (index in 0 .. 2.toDouble().pow(newValuePair.second).toInt() - 1) {
        val curBinaryPerm = Integer.toBinaryString(index).padStart(newValuePair.second, '0')
        val memAddressBinary = applyBinaryPermToXString(curBinaryPerm, newValuePair.first)
        val memAddressLong = memAddressBinary.toLong(2)
        memVal[memAddressLong] = value
    }

}

fun applyBinaryPermToXString(curBinaryPerm: String, binaryStringWithXs: String): String {
    var curBinaryDigitIndex = 0
    return binaryStringWithXs.map { if(it == 'X') curBinaryPerm[curBinaryDigitIndex++] else it}.joinToString("")
}

//If the bitmask bit is 0, the corresponding memory address bit is unchanged.
//If the bitmask bit is 1, the corresponding memory address bit is overwritten with 1.
//If the bitmask bit is X, the corresponding memory address bit is floating.

fun applyMaskWithXs(binStr: String, mask: String): Pair<String, Int> {

    val buf = StringBuffer("")
    var xCount = 0

    for (i in binStr.indices) {
        if (mask[i] == 'X') {
            buf.append(mask[i])
            ++xCount
        } else if (mask[i] == '0'){
            buf.append (binStr[i])
        } else {
            buf.append('1')
        }
    }
    return Pair(buf.toString(), xCount)

}