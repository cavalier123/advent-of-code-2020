package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayNineteenRulesData"
    // val filename = "Day19RulesTest1Data"
    val solution = solveItDayNineteenPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

sealed class MessEl
data class NumberEl (val value: Int): MessEl() { override fun toString() = value.toString()}
data class StringEl (val value: String): MessEl() { override fun toString() = value}

data class Rule (var used: Boolean, var snips: Set<MutableList<MessEl>>) {
    override fun toString(): String {
        val buf = StringBuffer()
        buf.append("used=$used, snippets=")
        for (snip in snips) {
            for (el in snip) {
                buf.append(el)
                buf.append(":")
            }
            buf.append(",")
        }
        return buf.toString()
    }

    fun getRawStrings() = snips.map {toRawString(it)}

    fun containsNumber(num: Int): Boolean {
        return snips.flatMap {it}.find{ it is NumberEl && it.value == num } != null
    }
    fun isAllLetters(): Boolean {
        return snips.isNotEmpty() && snips.flatMap{it}.find { it is NumberEl } == null
    }
    fun replace(num: Int, strings: List<String>) {
        if (containsNumber(num)) {
            val newSnips = mutableSetOf<MutableList<MessEl>>()
            for (snip in snips) {
                if (containsNum(snip, num)) {
                    newSnips.addAll(getNumWithString(snip, num, strings))
                } else {
                    newSnips.add(snip)
                }
            }
            snips = newSnips
        }
    }
    fun matches(message: String) = getRawStrings().contains(message)

}

fun toRawString(elements: MutableList<MessEl>): String {
    val strings = elements.map{it.toString()}
    val buf = StringBuffer()
    for (string in strings) {
        buf.append(string)
    }
    return buf.toString()
}

fun getMessList(numList: List<Int>): MutableList<MessEl> {
    val ret = mutableListOf<MessEl>()
    for (num in numList) {
        ret.add(NumberEl(num))
    }
    return ret
}

fun containsNum(elements: List<MessEl>, num: Int): Boolean {
    for (element in elements) {
        if (element is  NumberEl && element.value == num) return true
    }
    return false
}

fun getNumWithString(elements: List<MessEl>, num: Int, reps: List<String>): MutableSet<MutableList<MessEl>> {

    val ret = mutableSetOf<MutableList<MessEl>>()
    for (rep in reps) {
        val newLine = elements.map{ if((it is NumberEl) && it.value == num) StringEl(rep) else it }.toMutableList()
        ret.add(newLine)
    }
    return ret
}

fun solveItDayNineteenPartOne(lines: List<String>): Int {

//    8: 42
//    85: 64 112 | 50 110
//    38: 64 104 | 50 4
//    120: 84 50 | 55 64
//    67: 64 7 | 50 53
//    64: "a"

    //val rules = HashMap<Int, Rule>()

    val rules = Array(129) {Rule(false, mutableSetOf())}

    for (line in lines) {
        val parts = line.split(":")
        val ruleNum = parts[0].toInt()
        if (parts[1].contains("\"")) {
            val letter = parts[1].removePrefix(" \"").removeSuffix("\"")
            val messElList: MutableList<MessEl> = mutableListOf(StringEl(letter))
            rules[ruleNum] = Rule (false, setOf(messElList))
        } else if (!parts[1].contains("|")) {
            val numList = parts[1].trim().split(" ").map{it.toInt()}
            rules[ruleNum] = Rule(false, setOf(getMessList(numList)))
        } else {
            val numLists = parts[1].split("|")
            val numList1 = numLists[0].trim().split(" ").map{it.toInt()}
            val numList2 = numLists[1].trim().split(" ").map{it.toInt()}
            rules[ruleNum] = Rule(false, setOf(getMessList(numList1), getMessList(numList2)))
        }
    }

    for (ruleIndex in rules.indices) {
        println ("$ruleIndex ${rules[ruleIndex]}")
    }

    var curGen = 1
    do {
        println ("CurGen = $curGen")
        curGen++
        val unusedStringyRuleIndex = findFirstAllLettersUnusedRule(rules)
        unusedStringyRuleIndex?.run {
            // println(">>>>Useful rule $this = ${rules[this]}")
            applyRule(this, rules)
//            for (ruleIndex in rules.indices) {
//                println ("$ruleIndex ${rules[ruleIndex]}")
//            }
        }
    } while (unusedStringyRuleIndex != null)
    // println ("${rules[0]}")
    for (ruleIndex in rules.indices) {
        println ("$ruleIndex ${rules[ruleIndex].used} ${rules[ruleIndex].isAllLetters()}")
    }

    //println ("Num snips for rule 0 = ${rules[0].snips.size}")
    val rawStrings = rules[0].getRawStrings()
    println ("raw strings size = ${rawStrings.size}")
    val minLen = rawStrings.map{it.length}.min()
    val maxLen = rawStrings.map{it.length}.max()
    println ("range = $minLen to $maxLen")

//    for (rawStringIndex in rawStrings.indices)
//    {
//        println("$rawStringIndex||${rawStrings[rawStringIndex]}||")
//    }

    val lines = splitFileToLines("DayNineteenMessageData")
    var count = 0
    for (line in lines) {
        println (line)
        if (rawStrings.contains(line)) ++ count
        println(count)
    }
    return count
    //return 0
}

fun findFirstAllLettersUnusedRule(rules: Array<Rule>) : Int? {
    for (ruleIndex in rules.size - 1 downTo 1) {
        if (rules[ruleIndex].isAllLetters() && !rules[ruleIndex].used) return ruleIndex
    }
    return null
}

fun applyRule(ruleToApplyIndex: Int, rules: Array<Rule>) {
    val ruleToApply = rules[ruleToApplyIndex]
    val curRuleStrings = ruleToApply.getRawStrings()
    for (rule in rules) {
        rule.replace(ruleToApplyIndex, curRuleStrings)
    }
    ruleToApply.used = true
}











