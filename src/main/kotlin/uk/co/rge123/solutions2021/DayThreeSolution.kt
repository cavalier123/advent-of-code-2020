package main.kotlin.uk.co.rge123.solutions2021

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import kotlin.math.pow

fun main (args: Array<String>) {

    val lines = splitFileToLines("dayThree", 2021)
    val numSize = 12

    val oneCount = Array(numSize) { 0 }

    for (line in lines) {
        for (index in 0 until numSize) {
            if (line[index] == '1') oneCount[index]++
        }
    }

    val numLines = lines.size

    var gamma = 0.0
    var epsilon = 0.0

    for (index in 0 until numSize) {
        val placeVal = 2.toDouble().pow(index)
        if (oneCount[numSize - index - 1] > numLines / 2) {
            gamma += placeVal
        } else {
            epsilon += placeVal
        }
    }

    println ("Answer = ${gamma * epsilon}")
}