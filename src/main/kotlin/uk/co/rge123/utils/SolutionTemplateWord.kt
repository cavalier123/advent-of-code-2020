package main.kotlin.uk.co.rge123.utils

fun main() {
    val solution = solveIt("aeiou")
    print ("Solution is $solution")
}

fun solveIt(input: String): Int {
    return input.count{it.isVowel()}
}

fun Char.isVowel() = "aeiou".contains(this)