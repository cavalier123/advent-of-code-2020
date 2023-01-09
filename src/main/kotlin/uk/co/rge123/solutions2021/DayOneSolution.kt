package main.kotlin.uk.co.rge123.solutions2021

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main (args: Array<String>) {

    val numbers = splitFileToLines("dayOne", 2021).map {it.toInt()  }

    val slidingSum = List(numbers.size - numbers.size % 3) { i -> numbers[i] + numbers[i+1] + numbers[i+2]}

    val increases2 = slidingSum.zipWithNext().count {it.second > it.first}

    println ("Increases = $increases2")
}