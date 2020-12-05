package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

private val requiredSet = HashSet<String>(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))

fun main() {
    val filename = "DayFourData"
    val solution = solveItDayFourPartTwo(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayFourPartTwo(lines: List<String>): Int {
    var curLine = StringBuffer()
    var valCount = 0

    for (line in lines) {
        if (line.length > 0) {
            if (curLine.isNotEmpty()) curLine.append(" ")
            curLine.append(line)
        } else {
            valCount += if (isValid2(curLine.toString())) 1 else 0
            curLine = StringBuffer()
        }
    }
    valCount += if (isValid2(curLine.toString())) 1 else 0

    return valCount
}

fun isValid2(line:String) : Boolean {
    return line.split(" ").map{ it.split(":")}.filter {isValidField(it[0], it[1])}.map{it[0]}.containsAll(requiredSet)
}

fun isValidField(field: String, value: String): Boolean {
    return when(field) {
        "byr" -> isNumInRange(value, (1920..2002))
        "iyr" -> isNumInRange(value, (2010..2020))
        "eyr" -> isNumInRange(value, (2020..2030))
        "hgt" -> isNumberFollowedByCmOrInches (value)
                && ( (value.endsWith("cm") && (isNumInRange(value.substringBefore("cm"), (150..193)))) ||
                (value.endsWith("in") && (isNumInRange(value.substringBefore("in"), (59..76)))))

        "hcl" ->  value.startsWith("#") && value.length == 7 && (isNumbersOrLetters(value.substring(1)))
        "ecl" ->  listOf ("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(value)
        "pid" ->  isNumber(value) && value.length == 9
        else -> true
    }
}

fun isNumber(num: String) = num.matches("\\d+".toRegex())
fun isLetters(num: String) = num.matches("[a-z]+".toRegex())
fun isNumbersOrLetters(num: String) = num.matches("[0-9,a-z]+".toRegex())
fun isNumberFollowedByCmOrInches(num: String) = num.matches("[0-9]+(cm|in)".toRegex())
fun isNumInRange(num: String, range: IntRange) = num.toInt() in range