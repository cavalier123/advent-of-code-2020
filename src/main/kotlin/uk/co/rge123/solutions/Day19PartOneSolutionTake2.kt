package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayNineteenRulesData"
    // val filename = "Day19RulesTest1Data"
    val solution = solveItDayNineteenPartOneTakeTwo(splitFileToLines(filename))
    print ("Solution is $solution")
}

sealed class NewRule(val validStrings: MutableSet<String>, val invalidStrings: MutableSet<String>)
data class NumberRule(val nextRules1: Pair<Int, Int?>, val nextRules2: Pair<Int, Int?>?) : NewRule(mutableSetOf(), mutableSetOf())
    {override fun toString() = "Number rule, nextRules1 = $nextRules1, nextRules2 = $nextRules2"}
data class LetterRule(val letter: String): NewRule(mutableSetOf(), mutableSetOf()) {override fun toString() = "Letter rule, letter = $letter"}

fun solveItDayNineteenPartOneTakeTwo(lines: List<String>): Int {

//    8: 42
//    85: 64 112 | 50 110
//    38: 64 104 | 50 4
//    120: 84 50 | 55 64
//    67: 64 7 | 50 53
//    64: "a"

    //val rules = HashMap<Int, Rule>()

//    8: 42 | 42 8
//    11: 42 31 | 42 11 31

    // 42: 64 54 | 50 40
    // 31: 64 69 | 50 67


    val rules = Array<NewRule>(129) {LetterRule("a")}

    for (line in lines) {
        val parts = line.split(":")
        val ruleNum = parts[0].toInt()
        if (parts[1].contains("\"")) {
            val letter = parts[1].removePrefix(" \"").removeSuffix("\"")
            rules[ruleNum] = LetterRule (letter)
        } else if (!parts[1].contains("|")) {
            val numList = parts[1].trim().split(" ").map{it.toInt()}
            rules[ruleNum] = NumberRule (Pair(numList[0], if(numList.size == 2) numList[1] else null), null)
        } else {
            val numLists = parts[1].split("|")
            val numList1 = numLists[0].trim().split(" ").map{it.toInt()}
            val numList2 = numLists[1].trim().split(" ").map{it.toInt()}
            rules[ruleNum] = NumberRule (Pair(numList1[0], if(numList1.size == 2) numList1[1] else null),
                    Pair(numList2[0], if(numList2.size == 2) numList2[1] else null))
        }
    }

    for (ruleIndex in rules.indices) {
        println ("$ruleIndex ${rules[ruleIndex]}")
    }

//    val strToCheck = "bbabbaaabbabbaaaabbbbbbb"
//
//    val isValid = checkIt(rules, 0, strToCheck)
//    println("$isValid")

    val lines = splitFileToLines("DayNineteenMessageData")
    var count = 0
    for (line in lines) {
        println(line)
        if (checkIt(rules, 0, line)) ++count
    }

    return count
}

fun checkIt(rules: Array<NewRule>, ruleNum: Int, strToCheck: String) : Boolean {

    // Check letter
    if (rules[ruleNum] is LetterRule) {
        return (rules[ruleNum] as LetterRule).letter == strToCheck
    }

    //if (ruleNum > 1000) {
        if (rules[ruleNum].validStrings.contains(strToCheck)) return true
        if (rules[ruleNum].invalidStrings.contains(strToCheck)) return false
    //}

    // check first pair
    var firstPairResult = false
    if ( (rules[ruleNum] as NumberRule).nextRules1.second == null) {
        firstPairResult = checkIt(rules, (rules[ruleNum] as NumberRule).nextRules1.first, strToCheck)
    } else {
        for (index in 0 .. strToCheck.length - 2) {
            val leftHalf = strToCheck.substring(0, index + 1)
            val rightHalf = strToCheck.substring(index + 1)
            firstPairResult = checkIt(rules, (rules[ruleNum] as NumberRule).nextRules1.first, leftHalf)
                    && checkIt(rules, (rules[ruleNum] as NumberRule).nextRules1.second!!, rightHalf)
            if (firstPairResult) break
        }
    }

    // if no go check second pair
    if (firstPairResult) {
        rules[ruleNum].validStrings.add(strToCheck)
        return true
    }
    if ((rules[ruleNum] as NumberRule).nextRules2 == null) {
        rules[ruleNum].invalidStrings.add(strToCheck)
        return false
    }

    var secondPairResult = false
    if ( (rules[ruleNum] as NumberRule).nextRules2?.second == null) {
        secondPairResult = checkIt(rules, (rules[ruleNum] as NumberRule).nextRules2?.first!!, strToCheck)
    } else if (ruleNum != 11) {
        for (index in 0 .. strToCheck.length - 2) {
            val leftHalf = strToCheck.substring(0, index + 1)
            val rightHalf = strToCheck.substring(index + 1)
            secondPairResult = checkIt(rules, (rules[ruleNum] as NumberRule).nextRules2?.first!!, leftHalf)
                    && checkIt(rules, (rules[ruleNum] as NumberRule).nextRules2?.second!!, rightHalf)
            if (secondPairResult) break
        }
    } else { // ruleNum == 11
        loop@ for (i in 0 .. strToCheck.length - 3) {
            for (j in i + 1..strToCheck.length - 2) {
                val firstPart = strToCheck.substring(0, i + 1)
                val secondPart = strToCheck.substring(i + 1, j + 1)
                val thirdPart = strToCheck.substring(j + 1)
                secondPairResult = checkIt(rules, (rules[ruleNum] as NumberRule).nextRules2?.first!!, firstPart)
                        && checkIt(rules, (rules[ruleNum] as NumberRule).nextRules2?.second!!, secondPart) &&
                        checkIt(rules, 31, thirdPart)
                if (secondPairResult) break@loop
            }
        }
    }

    if (secondPairResult) {
        rules[ruleNum].validStrings.add(strToCheck)
    } else {
        rules[ruleNum].invalidStrings.add(strToCheck)
    }

    return secondPairResult
}









