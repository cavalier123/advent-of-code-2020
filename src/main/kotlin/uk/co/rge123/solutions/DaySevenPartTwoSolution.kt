package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DaySevenDataTestOne"
    val solution = solveItForDaySevenPartTwo(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItForDaySevenPartTwo(lines: List<String>): Int {


    // pale turquoise bags contain 3 muted cyan bags, 5 striped teal bags.
    // light maroon bags contain no other bags.

    val bagContainsList = HashMap<String, MutableList<Pair<String, Int>>>()

    for (line in lines) {
        val parts = line.split("contain")
        val firstBag = parts[0].trim().removeSuffix("s")

        val bagList = parts[1].removeSuffix(".").trim()

        if (!bagList.contains("no other bags")) {
            val containsList = parts[1].split(",").map { Pair(it.substring(2).removeSuffix(".").removeSuffix("s").trim(), it.substring(0, 2).trim().toInt()) }
            bagContainsList[firstBag] = containsList.toMutableList()
        }
    }

    val startNode = Pair("shiny gold bag", 1)

    return getBagsIn(startNode, bagContainsList) - 1
}

fun getBagsIn(curNode: Pair<String, Int>, graph: HashMap<String, MutableList<Pair<String, Int>>>): Int {

    val children = graph[curNode.first] ?: return curNode.second

    var totalChildBags: Int = 0
    for (child in children) {
        totalChildBags += getBagsIn(child, graph)
    }

    return curNode.second + curNode.second * totalChildBags
}






