package main.kotlin.uk.co.rge123.solutions

import main.kotlin.uk.co.rge123.utils.splitFileToLines

fun main() {
    val filename = "DayEightData"
    val solution = solveDayEightPartOne(splitFileToLines(filename))
    print ("Solution is $solution")
}

enum class Operation {
    ACC, JMP, NOP
}
data class Instruction (var op: Operation, val arg: Int)

fun solveDayEightPartOne(lines: List<String>): Int {

    val program = mutableListOf<Instruction>()

    for (line in lines) {
        program.add(getInstruction(line))
    }

    for (step in program) {
        if (step.op == Operation.JMP || step.op == Operation.NOP) {
            val originalStep = step.op
            step.op = when(step.op) {
                Operation.JMP -> Operation.NOP
                Operation.NOP -> Operation.JMP
                else -> Operation.JMP
            }
            val result = runIt(program)
            if (result) break
            step.op = originalStep
        }
    }

    return 0
}

fun getInstruction(line: String): Instruction {
    val parts = line.split(" ")

    return Instruction(when (parts[0]) {
        "acc" -> Operation.ACC
        "jmp" -> Operation.JMP
        "nop" -> Operation.NOP
        else -> Operation.NOP
    }, parts[1].toInt())
}

fun runIt(program: List<Instruction>): Boolean {

    var accumulator = 0
    var progStep = 0
    var lastProgStep = -1
    var executed = HashSet<Int>()

    while (!executed.contains(lastProgStep) && progStep < program.size) {

        executed.add(lastProgStep)
        lastProgStep = progStep

        when (program[progStep].op) {
            Operation.ACC -> { accumulator += program[progStep].arg; progStep ++ }
            Operation.JMP -> progStep += program[progStep].arg
            Operation.NOP -> progStep ++
        }
    }

    return progStep == program.size
}



