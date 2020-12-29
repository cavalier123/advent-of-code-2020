package main.kotlin.uk.co.rge123.solutions

fun main() {
    val str = "abcde"
    for (i in 0 .. str.length - 3) {
        for (j in i+1 .. str.length - 2) {
            println(str.substring(0, i+1))
            println(str.substring(i+1, j+1))
            println(str.substring(j+1))
            println("-----")

        }
        println("-----")
    }
}

