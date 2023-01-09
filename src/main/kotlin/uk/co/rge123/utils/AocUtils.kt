package main.kotlin.uk.co.rge123.utils

import java.io.File

fun splitFileToLines(fileName: String, year: Int = 2022) : List<String> = File("src/main/resources/data${year.toString()}/$fileName").readLines()
fun splitFileToWords(fileName: String, separator: String, year: Int = 2022) = splitFileToLines(fileName, year).flatMap {it.split(separator)}
fun splitFileToWordsCommaSeparated(fileName: String, year: Int = 2022) = splitFileToLines(fileName, year).flatMap {it.split(",")}
fun splitFileToWordsRexExSeparated(fileName: String, re: Regex, year: Int = 2022) = splitFileToLines(fileName, year).flatMap {it.split(re)}
fun splitFileToWordsWhitespaceSeparated (fileName: String, year: Int = 2022) = splitFileToWordsRexExSeparated(fileName, Regex("[\\s]+"), year)
fun getIntPair(range: String, delimiter: String = "-") : Pair<Int, Int> {
    val rangeNums = range.split(delimiter)
    return Pair(rangeNums[0].toInt(), rangeNums[1].toInt())
}
fun splitFileToIntPairs(fileName: String, delimiter: String = "-", year: Int = 2022) = splitFileToLines(fileName, year).map {getIntPair(it, delimiter)}
fun splitFileToSetOfIntPairs(fileName: String, delimiter: String = "-", year: Int = 2022) =
    splitFileToLines(fileName, year)
        .map { it1 ->
            it1.split(",").map {getIntPair(it, delimiter)}
        }