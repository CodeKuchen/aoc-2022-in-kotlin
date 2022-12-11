package day11

import inputTextOfDay
import testTextOfDay
import kotlin.math.floor

fun part1(input: String): ULong {
    return monkeyBusiness(input, 20, ::reduceWorryLevels)
}
fun part2(input: String): ULong {
    return monkeyBusiness(input, 10000, ::manageWorryLevels)
}

fun monkeyBusiness(input: String, rounds: Int, managedWorries: (ULong, UInt) -> ULong): ULong {
    val monkeys = parseMonkeys(input)
    val monkeyItems = monkeys.map { monkey -> monkey.first().split(", ").map { it.toULong() }.toMutableList() }
    val monkeyInspections = MutableList(8) { 0UL }

    val monkeyDivisor = monkeys.map{ monkey -> monkey[2].toUInt() }.reduce { a, b -> a * b }

    repeat(rounds) {
        for (monkey in monkeys.indices) {
            monkeyItems[monkey].forEach {  worryLevel ->
                monkeyInspections[monkey] = monkeyInspections[monkey] + 1UL
                val (_, operation, tests, testTrue, testFalse) = monkeys[monkey]
                val (operator, operand) = operation.split(" ")

                val worryLevelDuringInspection = when (operator) {
                    "*" -> worryLevel * when (operand) {
                        "old" -> worryLevel
                        else -> operand.toULong()
                    }
                    else -> worryLevel + when (operand) {
                        "old" -> worryLevel
                        else -> operand.toULong()
                    }
                }

                val worryLevelAfterInspection = managedWorries(worryLevelDuringInspection, monkeyDivisor)

                when (worryLevelAfterInspection % tests.toUInt() == 0UL) {
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

private fun reduceWorryLevels(worryLevel: ULong, monkeyDivisor: UInt) =
    floor(worryLevel.toDouble()  / 3.0).toULong()

private fun manageWorryLevels(worryLevel: ULong, monkeyDivisor: UInt) =
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
    check(part1(test) == 10605UL)
    check(part1(input) == 57838UL)

    println(part2(test))
    println(part2(input))

    check(part2(test) == 2713310158UL)
    check(part2(input) == 15050382231UL)
}
