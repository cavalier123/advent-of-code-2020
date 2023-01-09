package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "Day21Data"
    val solution = solveItDay21Part1(splitFileToLines(filename))
    print ("Solution is $solution")
}

fun solveItDay21Part1(lines: List<String>): Int {
    // (contains dairy, shellfish, eggs)

    val hash = mutableMapOf<String, Set<String>>()
    val masterIngredientsList = mutableListOf<String>()

    for (line in lines) {
        val parts = line.split("(contains ")
        val ingredientsList = parts[0].trim().split(" ")
        masterIngredientsList.addAll(ingredientsList)
        val allergensList = parts[1].trim().removeSuffix(")").split(", ")
        for (allergen in allergensList) {
            var possibleIngredients = hash[allergen]
            if (possibleIngredients == null) {
                hash[allergen] = ingredientsList.toMutableSet()
            } else {
                hash[allergen] = possibleIngredients.intersect(ingredientsList)
            }
        }
    }

    println("master list len = ${masterIngredientsList.size}")

    for (key in hash.keys) {
        println ("Allergen $key is in ${hash[key]} of size ${hash[key]?.size}")
    }

    var changed = false
    do {
        changed = false
        for (entry in hash.entries) {
            if (entry.value.size == 1) {
                for (otherEntry in hash.entries) {
                    if (otherEntry.key != entry.key) {
                        val setToSearch: Set<String>? = hash[otherEntry.key]
                        //if (setToSearch?.contains(entry.value) == true) changed = true
                        hash[otherEntry.key] = otherEntry.value.minus(entry.value)
                    }
                }
            }
        }
    } while (hash.map{it.value.size}.maxOrNull() ?: 0 > 1)

    println("-------")
    for (key in hash.keys) {
        println ("Allergen $key is in ${hash[key]} of size ${hash[key]?.size}")
    }
    val dangerousIngredients = hash.values.flatten()

    val sortedBad = hash.toSortedMap().flatMap{it.value}.joinToString(",")
    println("$sortedBad")


    return masterIngredientsList.count {it !in dangerousIngredients}

}