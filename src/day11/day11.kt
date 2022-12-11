package day11

import inputTextOfDay
import testTextOfDay
import kotlin.math.floor

fun part1(input: String): Long {
    return monkeyBusiness(input, 20, ::reduceWorryLevels)
}
fun part2(input: String): Long {
    return monkeyBusiness(input, 10000, ::manageWorryLevels)
}

fun monkeyBusiness(input: String, rounds: Int, managedWorries: (Long, Long) -> Long): Long {
    val monkeys = parseMonkeys(input)
    val monkeyItems = monkeys.map { monkey -> monkey.first().split(", ").map { it.toLong() }.toMutableList() }
    val monkeyInspections = MutableList(8) { 0L }

    val monkeyDivisor = monkeys.map{ monkey -> monkey[2].toLong() }.reduce { a, b -> a * b }

    repeat(rounds) {
        for (monkey in monkeys.indices) {
            monkeyItems[monkey].forEach {  worryLevel ->
                monkeyInspections[monkey] = monkeyInspections[monkey] + 1L
                val (_, operation, tests, testTrue, testFalse) = monkeys[monkey]
                val (operator, operand) = operation.split(" ")

                val worryLevelDuringInspection = when (operator) {
                    "*" -> worryLevel * when (operand) {
                        "old" -> worryLevel
                        else -> operand.toLong()
                    }
                    else -> worryLevel + when (operand) {
                        "old" -> worryLevel
                        else -> operand.toLong()
                    }
                }

                val worryLevelAfterInspection = managedWorries(worryLevelDuringInspection, monkeyDivisor)

                when (worryLevelAfterInspection % tests.toInt() == 0L) {
                    true -> {
                        monkeyItems[testTrue.toInt()].add(worryLevelAfterInspection)
                    }
                    false -> {
                        monkeyItems[testFalse.toInt()].add(worryLevelAfterInspection)
                    }
                }
            }
            monkeyItems[monkey].clear()
        }
    }
    return monkeyInspections.sortedDescending().take(2).let { (a,b) -> a * b }
}

private fun reduceWorryLevels(worryLevel: Long, monkeyDivisor: Long) =
    floor(worryLevel.toDouble()  / 3.0).toLong()

private fun manageWorryLevels(worryLevel: Long, monkeyDivisor: Long) =
    worryLevel % monkeyDivisor

private fun parseMonkeys(input: String) = input.lines().chunked(7) { monkey ->
    monkey.drop(1).mapNotNull {
        when {
            it.startsWith("  Starting items: ") -> it.substringAfter("  Starting items: ")
            it.startsWith("  Operation: new = old ") -> it.substringAfter("  Operation: new = old ")
            it.startsWith("  Test: divisible by ") -> it.substringAfter("  Test: divisible by ")
            it.startsWith("    If true: throw to monkey ") -> it.substringAfter("    If true: throw to monkey ")
            it.startsWith("    If false: throw to monkey ") -> it.substringAfter("    If false: throw to monkey ")
            else -> null
        }
    }
}


fun main() {
    val day = 11

    val test = testTextOfDay(day)
    val input = inputTextOfDay(day)

    println(part1(test))
    println(part1(input))
    check(part1(test) == 10605L)
    check(part1(input) == 57838L)

    println(part2(test))
    println(part2(input))

    check(part2(test) == 2713310158L)
    check(part2(input) == 15050382231L)
}
