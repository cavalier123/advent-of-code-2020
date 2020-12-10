package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DaySevenData"
    val solution = solveItForDaySevenPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItForDaySevenPartOne(lines: List<String>): Int {


    // pale turquoise bags contain 3 muted cyan bags, 5 striped teal bags.
    // light maroon bags contain no other bags.

    val bagContainsList = HashMap<String, MutableList<String>>()

    for (line in lines) {
        if (line.contains("no other bags")) continue

        val parts = line.split("contain")
        val firstBag = parts[0].trim().removeSuffix("s")
        println(firstBag)

        val bagList = parts[1].removeSuffix(".").trim()

        // pale turquoise bags contain 3 muted cyan bags, 5 striped teal bags.

        val containsList = parts[1].split(",").map { it.substring(2).removeSuffix(".").removeSuffix("s").trim() }
        for (thing in containsList) {
            println(">>>> $thing")
        }

        for (bag in containsList) {
            val curContainsList = bagContainsList[bag]
            if (curContainsList == null) {
                bagContainsList[bag] = mutableListOf(firstBag)
            } else {
                curContainsList?.add(firstBag)
            }
        }

    }

    for (entry in bagContainsList.keys) {
        print ("$entry >>== ");
        val value =  bagContainsList[entry]
        if (value != null) {
            for (thing in value) {
                print("$thing,")
            }
        }
        println()
    }

    val bagSet: MutableSet<String> = HashSet<String>()
    bagSet.add("shiny gold bag")
    var curSetSize = 1

    do {
        curSetSize = bagSet.size

        val bagsToAdd: MutableSet<String> = HashSet<String>()

        for (bag in bagSet) {
            val containedInList = bagContainsList[bag]
            if (containedInList != null) {
                bagsToAdd.addAll(containedInList)
            }
        }

        bagSet.addAll(bagsToAdd)

    } while (bagSet.size > curSetSize)

    return bagSet.size

}
