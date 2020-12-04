package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayFourData"
    val solution = solveItDayFourPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDayFourPartOne(lines: List<String>): Int {
    var curLine = StringBuffer()
    var valCount = 0

    for (line in lines) {
        //println("$line has length ${curLine.length}")
        if (line.length > 0) {
            curLine.append(" ").append(line)
        } else {
            //println (curLine)
            valCount += if (isValid(curLine.toString())) 1 else 0
            curLine = StringBuffer()
        }
    }
    valCount += if (isValid(curLine.toString())) 1 else 0

    return valCount

}

//byr (Birth Year) - four digits; at least 1920 and at most 2002.
//iyr (Issue Year) - four digits; at least 2010 and at most 2020.
//eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
//hgt (Height) - a number followed by either cm or in:
//If cm, the number must be at least 150 and at most 193.
//If in, the number must be at least 59 and at most 76.
//hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
//ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
//pid (Passport ID) - a nine-digit number, including leading zeroes.
//cid (Country ID) - ignored, missing or not.


fun isValid(line:String) : Boolean {

    val wordSet = HashSet<String>()
    val requiredSet = HashSet<String>(listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"))
    var isValid = true

    val words = line.split(" ")
    for (word in words) {
        val parts = word.split(":")
        wordSet.add(parts[0])

        if (parts[0] == "byr") isValid = isValid && parts[1].length == 4 && parts[1].toInt() in (1920 .. 2002)
        if (parts[0] == "iyr") isValid = isValid && parts[1].length == 4 && parts[1].toInt() in (2010 .. 2020)
        if (parts[0] == "eyr") isValid = isValid && parts[1].length == 4 && parts[1].toInt() in (2020 .. 2030)

        if (parts[0] == "hgt") isValid = isValid
                && (parts[1].endsWith("cm") || parts[1].endsWith("in"))
                && (isNumber(parts[1].substring(0 .. parts[1].length - 3)))
                && ((parts[1].endsWith("cm") && parts[1].substringBefore("cm").toInt() in (150 ..193)) ||
                   (parts[1].endsWith("in") && parts[1].substringBefore("in").toInt() in (59 ..76)))

        if (parts[0] == "hcl") isValid = isValid && parts[1].startsWith("#") && parts[1].length == 7 && (isNumbersOrLetters(parts[1].substring(1)))
        if (parts[0] == "ecl") isValid = isValid && listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(parts[1])
        if (parts[0] == "pid") isValid = isValid && isNumber(parts[1]) && parts[1].length == 9
    }
    return wordSet.containsAll(requiredSet) && isValid
}


fun isNumber(num: String) = num.matches("\\d+".toRegex())
fun isLetters(num: String)= num.matches("[a-z]+".toRegex())
fun isNumbersOrLetters(num: String) = num.matches("[0-9,a-z]+".toRegex())