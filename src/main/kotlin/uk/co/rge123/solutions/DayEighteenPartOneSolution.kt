package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayEighteenData"
    val solution = solveDayEighteenPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

sealed class ExprEl
data class Number(val number: Long) : ExprEl() { override fun toString() = number.toString() }
class OpenBracket : ExprEl() { override fun toString() = "(" }
class CloseBracket() : ExprEl() { override fun toString() = ")" }
class Plus() : ExprEl() { override fun toString() = "+" }
class Multiply() : ExprEl() { override fun toString() = "*" }
class Unknown() : ExprEl() { override fun toString() = "x"}

fun toExprEl(char: Char): ExprEl {
    return when (char) {
        '(' -> OpenBracket()
        ')' -> CloseBracket()
        in '0'..'9' -> Number(char.code.toLong() - 48)
        '+' -> Plus()
        '*' -> Multiply()
        else -> Unknown()
    }
}

fun solveDayEighteenPartOne(lines: List<String>): Long {

    var count = 0L
    for (line in lines) {
        val expression = line.split(" ").flatMap{it.asIterable()}.map{toExprEl(it)}
        val reversePolish = getReversePolishPlusBeatsTimes(expression)
        count += evalReversedPolish(reversePolish)
    }
    return count
}

fun getReversePolish(exprEls: List<ExprEl>): List<ExprEl> {
    val revPolish = mutableListOf<ExprEl>()
    val opStack = ArrayDeque<ExprEl>()
    for (exprEl in exprEls.reversed()) {
        when (exprEl) {
            is OpenBracket -> {
                do {
                    val curExprEl = opStack.removeFirst()
                    if (curExprEl !is CloseBracket) {
                        revPolish.add(curExprEl)
                    }
                } while (curExprEl !is CloseBracket)
            }
            is Number -> revPolish.add(exprEl)
            is Plus, is Multiply, is CloseBracket -> opStack.addFirst(exprEl)
            else -> revPolish.add(exprEl) // rubbish
        }
    }
    while (opStack.isNotEmpty()) {
        revPolish.add(opStack.removeFirst())
    }
    return revPolish
}

fun getReversePolishPlusBeatsTimes(exprEls: List<ExprEl>): List<ExprEl> {
    val revPolish = mutableListOf<ExprEl>()
    val opStack = ArrayDeque<ExprEl>()
    for (exprEl in exprEls.reversed()) {
        when (exprEl) {
            is OpenBracket -> {
                do {
                    val curExprEl = opStack.removeFirst()
                    if (curExprEl !is CloseBracket) {
                        revPolish.add(curExprEl)
                    }
                } while (curExprEl !is CloseBracket)
            }
            is Number -> revPolish.add(exprEl)
            is Plus -> opStack.addFirst(exprEl)
            is Multiply ->  {
                while (opStack.isNotEmpty() && opStack.first() is Plus) {
                    revPolish.add(opStack.removeFirst())
                }
                opStack.addFirst(exprEl)
            }
            is CloseBracket -> opStack.addFirst(exprEl)
            else -> revPolish.add(exprEl) //rubbish
        }
    }
    while (opStack.isNotEmpty()) {
        revPolish.add(opStack.removeFirst())
    }

    return revPolish
}

fun evalReversedPolish(exprEls: List<ExprEl>): Long {
    val opStack = ArrayDeque<ExprEl>()
    for (exprEl in exprEls) {
        when (exprEl) {
            is Number -> opStack.addFirst(exprEl)
            is Plus, is Multiply -> {
                val arg1 = opStack.removeFirst()
                val arg2 = opStack.removeFirst()
                if (exprEl is Plus) {
                    opStack.addFirst(Number((arg1 as Number).number + (arg2 as Number).number))
                } else {
                    opStack.addFirst(Number((arg1 as Number).number * (arg2 as Number).number))
                }
            }
            else -> opStack.addFirst(exprEl) //rubbish
        }
    }
    return (opStack.first() as Number).number
}

