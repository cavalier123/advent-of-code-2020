package main.kotlin.uk.co.rge123.solutions2022

import main.kotlin.uk.co.rge123.utils.splitFileToLines


enum class OP {
    PLUS, MINUS, MULTIPLY, DIVIDE;
    companion object {
        fun toOp(op: String): OP {
            return when (op) {
                "*" -> MULTIPLY
                "/" -> DIVIDE
                "-" -> MINUS
                "+" -> PLUS
                else -> PLUS
            }
        }

        fun getVal(op: OP, val1: Long, val2: Long): Long {
            return when (op) {
                PLUS -> val1 + val2
                MINUS -> val1 - val2
                MULTIPLY -> val1 * val2
                DIVIDE -> val1 / val2
            }
        }
    }
}

open class Node (open val name: String)
data class ValNode (override val name: String, var value: Long): Node (name)
data class ExNode (override val name: String, val name1: String, val name2: String, val op: OP,
                   var child1: Node? = null, var child2: Node? = null): Node(name)

val nodeMap = mutableMapOf<String, Node>()

fun main() {
    val filename = "day21"
    val solution = solveItDay21PartOne2022(splitFileToLines(filename))
    print ("Solution is $solution")
}

//zvqw: 4
//sshm: qhnd * lrcj

fun solveItDay21PartOne2022(lines: List<String>): Long {
    for (line in lines) {
        val words = line.split(" ")
        val name = words[0].removeSuffix(":")
        if (words.size == 2) {
            val valNode = ValNode(name, words[1].toLong())
            nodeMap[name] = valNode
        } else {
            val name1 = words[1]
            val name2 = words[3]
            val op = OP.toOp(words[2])
            val exNode = ExNode(name, name1, name2, op)
            nodeMap[name] = exNode
        }
    }

    val rootNode = nodeMap["root"]

    buildTree(rootNode!!)

    // val solution = evalTree(rootNode)

    // println (evalRoot(rootNode as ExNode))

    var answer = 0L
//    for (humanVal in -50L .. 50L) {
//        println("Human val = $humanVal")
//        val human = nodeMap["humn"]
//        (human as ValNode).value = humanVal
//        val ret = evalRoot(rootNode as ExNode, humanVal)
//        if (ret) {
//            answer = humanVal
//        }
//    }

    println ("80526799293735")

//    val human = nodeMap["humn"]
//    (human as ValNode).value = 10000000000000
//    val ret = evalRoot(rootNode as ExNode, 0)
//    if (ret < 80526799293735) println ("bingo")

    // solution is imn range [0 - 10000000000000]
    val human = nodeMap["humn"]

    var startRange = 0L
    var endRange = 10000000000000L

    do {
        val midRange = startRange + (endRange - startRange)/2
        println ("Midrange = $midRange")
        (human as ValNode).value = midRange
        val ret = evalRoot(rootNode as ExNode, 0)
        if (ret == 80526799293735) {
            println ("Bingo answer = $midRange")
            break
        } else if (ret < 80526799293735) {
            endRange = midRange
        } else {
            startRange = midRange
        }
    } while (true)

    return 999
}

fun buildTree(node: Node) {
    if (node is ExNode) {
        val child1 = nodeMap[node.name1]
        val child2 = nodeMap[node.name2]
        node.child1 = child1
        node.child2 = child2
        buildTree(child1!!)
        buildTree(child2!!)
    }
    return
}

fun evalRoot(rootNode: ExNode, humanVal: Long): Long {
    val leftVal = evalTree(rootNode.child1!!)
    val rightVal = evalTree(rootNode.child2!!)
    println("Left = $leftVal, right = $rightVal")
    return leftVal
}

fun evalTree(node: Node): Long {
    return when (node) {
        is ExNode -> OP.getVal(node.op, evalTree(node.child1!!) , evalTree(node.child2!!))
        is ValNode -> node.value
        else -> 0L
    }
}



