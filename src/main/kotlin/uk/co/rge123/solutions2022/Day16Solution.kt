package main.kotlin.uk.co.rge123.solutions2022
import main.kotlin.uk.co.rge123.utils.splitFileToLines
import java.lang.Math.max
import java.lang.Math.min

data class Valve(val name: String, val flowRate: Int,
                 var childNodes: MutableList<Valve> = mutableListOf()) {
    override fun toString(): String {
        val str = StringBuffer()
        for (child in childNodes) {
            str.append("${child.name},")
        }
        return ("name = $name, flow=$flowRate, children = ${str.toString()}")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Valve

        if (name != other.name) return false

        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

data class Visit (val valve: Valve, var turnedOn: Boolean)
data class Route (val visited: MutableList<Visit> = mutableListOf()) {
    fun copy(): Route {
        val newVisited = mutableListOf<Visit>()
        for (visit in visited) {
            newVisited.add(Visit(visit.valve, visit.turnedOn))
        }
        return Route(newVisited)
    }
    fun hasVisited (valve: Valve) = visited.map{it.valve.name}.contains(valve.name)
    fun numVisited (valve: Valve) = visited.count {it.valve.name == valve.name && !it.turnedOn}
    fun numTurnedOn() = visited.count{it.turnedOn}

    fun isGood(valve: Valve): Boolean {
        //if (valve.flowRate > 0 && !valveIsOpen(valve)) return true
        val numChildren = valve.childNodes.size - 1 // self
        val numVisited = numVisited(valve)
        return numVisited < numChildren
    }

    fun maxScorePossible(): Int {
        val routeVal = value()
        val routeLen = visited.size
        val routeLenStartIndex = 26 - routeLen

        val visitedValves = visited.filter{it.turnedOn}.map{it.valve}
        val valvesToBeVisited = openableValves.filter{!visitedValves.contains(it)}.sortedByDescending { it.flowRate }

        var maxToAdd = 0
        for ((index, valve) in valvesToBeVisited.withIndex()) {
            maxToAdd += valve.flowRate * (routeLenStartIndex - (index * 2))
        }

        return routeVal + maxToAdd
    }


    fun allChildrenVisited (valve: Valve): Boolean {
        for (child in valve.childNodes) {
            if (child.name != valve.name) {
                if (!hasVisited(child)) return false
            }
        }
        return true
    }
    fun valveIsOpen(valve: Valve): Boolean {
        return visited.find{it.valve.name == valve.name && it.turnedOn} != null
    }
    fun value(): Int {
        var score = 0
        for ((index, visit) in visited.withIndex()) {
            if (visit.turnedOn) {
                score += visit.valve.flowRate * (26 - index)
            }
        }
        return score
    }
    fun print() {
        for ((index, visit) in visited.withIndex()) {
            print("$index:${visit.valve.name}:${visit.turnedOn} ")
        }
        println()
    }
}

data class DualRoute (val routeA: Route, val routeB: Route) {
    fun copy(): DualRoute {
        return DualRoute(routeA.copy(), routeB.copy())
    }
    fun hasVisited (valve: Valve) = routeA.hasVisited(valve) || routeB.hasVisited(valve)
    fun numVisited (valve: Valve) = routeA.numVisited(valve) + routeB.numVisited(valve)
    fun numTurnedOn() = routeA.numTurnedOn() + routeB.numTurnedOn()

    fun isGood(valve: Valve): Boolean {
        val numChildren = valve.childNodes.size - 1 // self
        val numVisited = numVisited(valve)
        return numVisited < numChildren
    }

    fun maxScorePossible(): Int {
        val visitedOpenedValves = routeA.visited.toSet().union(routeB.visited.toSet()).filter{it.turnedOn}.map{it.valve}.toSet()
        val valvesToBeVisited = openableValves.filter{!visitedOpenedValves.contains(it)}.sortedByDescending { it.flowRate }

        val flows = valvesToBeVisited.zipWithNext().filterIndexed{index, _ -> index % 2 == 0}.map{it.first.flowRate + it.second.flowRate}.toMutableList()
        if (valvesToBeVisited.size % 2 == 1) flows.add(valvesToBeVisited.last().flowRate)

        val routeLenStartIndex = 26 - routeA.visited.size

        var maxToAdd = 0
        for ((index, flow) in flows.withIndex()) {
            val curIndex = routeLenStartIndex - (index * 3)
            if (curIndex < 0) {
                break
            }
            //println ("Cur index = $curIndex, flow = $flow")
            maxToAdd += (flow * (curIndex))
        }

        return routeA.value() + routeB.value() + maxToAdd
        //return routeA.maxScorePossible() + routeB.maxScorePossible()
    }

    fun valveIsOpen(valve: Valve): Boolean {
        return routeA.valveIsOpen(valve) || routeB.valveIsOpen(valve)
    }
    fun value() = routeA.value() + routeB.value()
    fun print() {
        println (">>>>>>")
        routeA.print()
        routeB.print()
        println ("<<<<<<")
    }
}



val valveMap = mutableMapOf<String, Valve>()
val openableValves = mutableListOf<Valve>()
var maxSoFar = 2000

fun main() {
    val filename = "day16"
    val solution = solveItDay16PartTwo2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

//Valve KF has flow rate=7; tunnels lead to valves YB, RZ, IK, PG, VI

fun solveItDay16PartOne2022(lines: List<String>): Int {

    for (line in lines) {
        val words = line.split(" ")
        val name = words[1]
        val flow = words[4].removePrefix("rate=").removeSuffix(";").toInt()
        val valve = Valve(name, flow)
        valveMap[name] = valve
        if (valve.flowRate > 0) {
            openableValves.add(valve)
        }
    }
    for (line in lines) {
        val words = line.split(" ")
        val name = words[1]
        val curValve = valveMap[name]
        val valvesIndex = line.indexOf("valve")
        val listIndex = valvesIndex + 6
        val valveList = line.substring(listIndex).trim()
        val valves = valveList.split(", ")
        for (valve in valves) {
            //println(cavern)
            val childValve = valveMap[valve]
            curValve!!.childNodes.add(childValve!!)
        }
        curValve!!.childNodes.add(curValve)
    }

    for (key in valveMap.keys) {
        println(valveMap[key])
    }

    val rootNode = valveMap["AA"]
    val route = Route()
    val numValves = valveMap.count{it.value.flowRate > 0}

    val score = 0 //(rootNode!!, 0, route, false, 0, numValves)

    return score
}

fun solveItDay16PartTwo2022(lines: List<String>): Int {

    for (line in lines) {
        val words = line.split(" ")
        val name = words[1]
        val flow = words[4].removePrefix("rate=").removeSuffix(";").toInt()
        val valve = Valve(name, flow)
        valveMap[name] = valve
        if (valve.flowRate > 0) {
            openableValves.add(valve)
        }
    }
    for (line in lines) {
        val words = line.split(" ")
        val name = words[1]
        val curValve = valveMap[name]
        val valvesIndex = line.indexOf("valve")
        val listIndex = valvesIndex + 6
        val valveList = line.substring(listIndex).trim()
        val valves = valveList.split(", ")
        for (valve in valves) {
            //println(cavern)
            val childValve = valveMap[valve]
            curValve!!.childNodes.add(childValve!!)
        }
        curValve!!.childNodes.add(curValve)
    }

    for (key in valveMap.keys) {
        println(valveMap[key])
    }

    val rootNode = valveMap["AA"]!!
    val dualRoute = DualRoute(Route(), Route())
    val numValves = valveMap.count{it.value.flowRate > 0}

    val score = traverseValvesDual(rootNode, rootNode, 0, dualRoute, false, false, 0, numValves)

    return score
}

fun traverseValvesDual(curValveA: Valve, curValveB: Valve,
                       depth: Int, dualRoute: DualRoute, turnedOnA: Boolean, turnedOnB: Boolean,
                       numValvesOpen: Int, numValves: Int): Int {

    dualRoute.routeA.visited.add(Visit(curValveA, turnedOnA))
    dualRoute.routeB.visited.add(Visit(curValveB, turnedOnB))

    //dualRoute.routeA.print()
    //dualRoute.routeB.print()

    //println ("${curValveA.name}, ${curValveB.name}: at start of traverse route")

    //route.print()

    var childrenVisited = 0
    var maxScore = 0

    val numTurnedOn = dualRoute.numTurnedOn()

    if (depth < 25 && numTurnedOn < numValves && dualRoute.maxScorePossible() >= maxSoFar) {

        val routeAPairs = mutableListOf<Pair<Valve, Boolean>>()
        val routeBPairs = mutableListOf<Pair<Valve, Boolean>>()

        var curValveAOpened = false
        if (curValveA.flowRate != 0 && !dualRoute.valveIsOpen(curValveA)) {
            routeAPairs.add(Pair(curValveA, true))
            curValveAOpened = true
        }
        if (!curValveAOpened || (curValveA != curValveB)) {
            if (curValveB.flowRate != 0 && !dualRoute.valveIsOpen(curValveB)) {
                routeBPairs.add(Pair(curValveB, true))
            }
        }

        for (child in curValveA.childNodes) {
            if (child.name != curValveA.name && dualRoute.isGood(child)) {
               routeAPairs.add(Pair(child, false))
            }
        }
        for (child in curValveB.childNodes) {
            if (child.name != curValveB.name && dualRoute.isGood(child)) {
                routeBPairs.add(Pair(child, false))
            }
        }

        //println("$routeAPairs")
        //println("$routeBPairs")

        var pairsVisited = mutableSetOf<Pair<Valve, Valve>>()

        for (pairA in routeAPairs) {
            for (pairB in routeBPairs) {
                val pairToAdd = Pair(pairA.first, pairB.first)
                if (!(curValveA == curValveB && (pairA.first == pairB.first || pairsVisited.contains(pairToAdd)))) {
                    pairsVisited.add(pairToAdd)
                    pairsVisited.add(Pair(pairB.first, pairA.first))
                    childrenVisited++
                    var newValvesOpen = 0
                    if (pairA.second) ++newValvesOpen
                    if (pairB.second) ++newValvesOpen
                    //println ("${curValve.name}:visit unvisited node")
                    val score = traverseValvesDual(
                        pairA.first, pairB.first, depth + 1, dualRoute.copy(),
                        pairA.second, pairB.second, numValvesOpen + newValvesOpen, numValves
                    )
                    maxScore = max(score, maxScore)
                }
            }
        }
    }
    if (childrenVisited == 0) {
        //println ("Final route")
        val routeVal = dualRoute.value()
        if (routeVal >= 1500) {
            println("route val = $routeVal")
            println("max so far $maxSoFar")
            println("max possible = ${dualRoute.maxScorePossible()}")
            dualRoute.print()
        }
        maxSoFar = maxSoFar.coerceAtLeast(routeVal)
        return routeVal
        //println("found")
    }
    return maxScore
}


fun traverseCaverns(cavern: Valve, curDepth: Int, maxDepth: Int, path:String, openValve: Boolean): Int {
    var newCurDepth = curDepth + 1
    val path = path +  " " + cavern.name
    //cavern.visited = true
    var cavernFlow = 0

    if (openValve) {
        cavernFlow = cavern.flowRate * (maxDepth - newCurDepth)
        newCurDepth++
        //cavern.isOpen = true
        println("Opening valve at ${cavern.name} with flow $cavernFlow at depth $newCurDepth")
    }

    var maxChildFlow = 0
    var childrenVisited = 0
    if (newCurDepth <= maxDepth) {
        for (child in cavern.childNodes) {
            //if (child.childNodes.count{!it.visited} >= 1) {
                println ("Recursing into node ${child.name} from node ${cavern.name}")
                var childFlow1 = 0
                //if (!child.isOpen && child.flowRate != 0) {
                    println("Open tap")
                    childFlow1 = traverseCaverns(child, newCurDepth, maxDepth, path, true)
                //}
                println ("Don't open tap")
                val childFlow2 = traverseCaverns(child, newCurDepth, maxDepth, path, false)
                val childFlow = max(childFlow1, childFlow2)
                if (childFlow > maxChildFlow) {
                    maxChildFlow = childFlow
                }
                childrenVisited++
            //}
        }
    }
    if (childrenVisited == 0) {
        println(" terminal path = $path")
    }
    // println("At node ${cavern.name} cavernFlow is $cavernFlow, maxChildFlow = $maxChildFlow")

    // cavern.isOpen = false
    return cavernFlow + maxChildFlow
}
