package main.kotlin.uk.co.rge123.solutions2021

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main (args: Array<String>) {

    val lines = splitFileToLines("dayTwo", 2021)

    var curAcross = 0
    var curDown = 0
    var curAim = 0

    for (line in lines) {
        val words = line.split(" ")
        val num = words[1].toInt()
        when (words[0]) {
            "forward" -> {
                curAcross += num
                curDown += curAim * num
            }
            "up" -> curAim -= num
            "down" -> curAim += num
        }
    }

    println ("Answer = ${curDown * curAcross}")
}