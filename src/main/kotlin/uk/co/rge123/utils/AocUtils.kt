package main.kotlin.uk.co.rge123.utils

import java.io.File

fun splitFileToLines(fileName: String) : List<String> = File("src/main/resources/$fileName").readLines()
fun splitFileToWords(fileName: String, separator: String) = splitFileToLines(fileName).flatMap {it.split(separator)}
fun splitFileToWordsCommaSeparated(fileName: String) = splitFileToLines(fileName).flatMap {it.split(",")}
fun splitFileToWordsRexExSeparated(fileName: String, re: Regex) = splitFileToLines(fileName).flatMap {it.split(re)}
fun splitFileToWordsWhitespaceSeparated(fileName: String) = splitFileToWordsRexExSeparated(fileName, Regex("[\\s]+"))
