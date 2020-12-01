package main.kotlin.uk.co.rge123.utils

fun main() {
    val filename = "DayOneData"
    val solution = solveItWithWords(splitFileToWordsWhitespaceSeparated(filename))
    print ("Solution is $solution")
}

fun solveItWithWords(words: List<String>): Int {
    val wordCount = mutableMapOf<String, Int>()
    words.forEach { word ->
        wordCount[word]?.let {wordCount[word] = it.plus(1)} ?: run { wordCount[word] = 1 }
    }
    return wordCount.values.maxOrNull() ?: 0
}