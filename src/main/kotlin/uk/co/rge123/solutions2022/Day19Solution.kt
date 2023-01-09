package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Math.max
import java.lang.Math.min

const val NUM_INTERVALS = 32
fun main() {
    val filename = "day19"
    val solution = solveItDay19PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

// Blueprint 1: Each ore robot costs 3 ore.
// Each clay robot costs 4 ore.
// Each obsidian robot costs 4 ore and 13 clay.
// Each geode robot costs 3 ore and 7 obsidian.

data class Purse (var numOre: Int, var numClay: Int, var numObsidian: Int, var numGeode: Int) {
    fun numCanAfford(cost: Cost): Int {
        val oreNum = if (cost.numOre == 0) Int.MAX_VALUE else numOre / cost.numOre
        val clayNum = if (cost.numClay == 0) Int.MAX_VALUE else numClay / cost.numClay
        val obsidianNum = if (cost.numObsidian == 0) Int.MAX_VALUE else numObsidian / cost.numObsidian
        return min(oreNum, (min(clayNum, obsidianNum)))
    }
    fun canAfford(cost: Cost): Boolean = numCanAfford(cost) >= 1
    fun spend(cost: Cost) {
        //if (canAfford(cost)) {
            numOre -= cost.numOre
            numClay -= cost.numClay
            numObsidian -= cost.numObsidian
        //}
    }
    fun copyPurse(): Purse {
        return Purse(numOre, numClay, numObsidian, numGeode)
    }
}
data class Cost (val numOre: Int, val numClay: Int, val numObsidian: Int) {
    operator fun plus(increment: Cost): Cost {
        return Cost(numOre + increment.numOre, numClay + increment.numClay, numObsidian + increment.numObsidian)
    }
}

data class Plan(val oreRobotCost: Cost, val clayRobotCost: Cost, val obsidianRobotCost: Cost, val geodeRobotCost: Cost)

data class State(var time: Int, var purse: Purse = Purse(0,0,0, 0),
                    var numOreRobots: Int = 0, var numClayRobots: Int  = 0, var numObsidianRobots: Int = 0, var numGeodeRobots: Int = 0,
                    var noBuys: Int = 0) {
    fun updatePurse() {
        purse.numOre += numOreRobots
        purse.numClay += numClayRobots
        purse.numObsidian += numObsidianRobots
        purse.numGeode += numGeodeRobots
    }
    fun copyState(): State {
        return State(time, purse.copyPurse(), numOreRobots, numClayRobots, numObsidianRobots, numGeodeRobots, noBuys)
    }
    private fun sumArithmetic(start: Int, num: Int): Int {
        return (num * (2 * start + num - 1)) / 2
    }
    fun maxGeodes(): Int {
        val numNums = NUM_INTERVALS - time + 1
        val startNum = numGeodeRobots
        return sumArithmetic (startNum, numNums) + purse.numGeode
    }
}

fun solveItDay19PartOne2022(lines: List<String>): Int {

    val plans = mutableListOf<Plan>()

    for (line in lines) {
        val words = line.split(".")

        var senWords = words[0].trim().split(" ")
        val oreRobotCost = Cost(senWords[6].toInt(), 0, 0)

        senWords = words[1].trim().split(" ")
        val clayRobotCost = Cost(senWords[4].toInt(), 0, 0)

        senWords = words[2].trim().split(" ")
        val obsidianRobotCost = Cost(senWords[4].toInt(), senWords[7].toInt(), 0)

        senWords = words[3].trim().split(" ")
        val geodeRobotCost = Cost (senWords[4].toInt(), 0,senWords[7].toInt())

        val plan = Plan (oreRobotCost, clayRobotCost, obsidianRobotCost, geodeRobotCost)

        plans.add(plan)
    }

    var sumQualityLevels = 1
    for ((index, plan) in plans.withIndex()) {
        maxGeodesSoFar = 0
        val state = State(time = 0, numOreRobots = 1)
        val maxGeodes = traverseWithPlan(state, plan)
        println ("Max geodes = $maxGeodes")
        sumQualityLevels *= maxGeodes
        println(plan)
    }

    //val purse = Purse(8,2,0, 0)
    //val cost = Cost(2, 0, 0)
    // val state = State(time = 0, numOreRobots = 1)

    // println ("Thingy = ${purse.numCanAfford(cost)}")

    // val maxGeodes = traverseWithPlan(state, plans[0])

    return sumQualityLevels
}

var maxGeodesSoFar = 0

fun traverseWithPlan(state: State, plan: Plan): Int {

//    println("$state")
//    println("${state.purse}")

    state.time++
    //println("${state.time}")

    if (state.time == NUM_INTERVALS) {
        state.updatePurse()
        val numGeodes = state.purse.numGeode
        maxGeodesSoFar = max(maxGeodesSoFar, numGeodes)
        if (numGeodes == maxGeodesSoFar) {
//            println("$state")
//            println("${state.purse}")
//            println ("numGeodes = $numGeodes")
        }
        return numGeodes
    }
    else if (state.maxGeodes() < maxGeodesSoFar) {
        //println("ABORT")
        return 0
    }
    else {
        var maxGeodes = 0
        var canAffordAnything = false
        if (state.purse.canAfford(plan.geodeRobotCost)) {
            //println("Buy geode robot")
            canAffordAnything = true
            val newState = state.copyState()
            newState.purse.spend(plan.geodeRobotCost)
            newState.updatePurse()
            newState.numGeodeRobots++
            maxGeodes = max(maxGeodes, traverseWithPlan(newState, plan))
        }
        if (state.purse.canAfford(plan.obsidianRobotCost)) {
            //println("Buy obsidian robot")
            canAffordAnything = true
            val newState = state.copyState()
            newState.purse.spend(plan.obsidianRobotCost)
            newState.updatePurse()
            newState.numObsidianRobots++
            maxGeodes = max(maxGeodes, traverseWithPlan(newState, plan))
        }
        if (state.purse.canAfford(plan.clayRobotCost)) {
            //println("Buy clay robot")
            canAffordAnything = true
                val newState = state.copyState()
                newState.purse.spend(plan.clayRobotCost)
            newState.updatePurse()
                newState.numClayRobots++
                maxGeodes = max(maxGeodes, traverseWithPlan(newState, plan))
        }
        if (state.purse.canAfford(plan.oreRobotCost)) {
            //println("Buy ore robot")
            canAffordAnything = true
                val newState = state.copyState()
                newState.purse.spend(plan.oreRobotCost)
            newState.updatePurse()
                newState.numOreRobots++
                maxGeodes = max(maxGeodes, traverseWithPlan(newState, plan))
        }
        // Consider options of not buying all robots except geode
        if (state.noBuys < 10) {
            val newState = state.copyState()
            newState.updatePurse()
            if (canAffordAnything) newState.noBuys++
            maxGeodes = max(maxGeodes, traverseWithPlan(newState, plan))
        }
        return maxGeodes
    }
}