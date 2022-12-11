package day11

import inputTextOfDay
import testTextOfDay
import kotlin.math.floor


fun part1(input: String): Int {
    val monkeys = parseMonkeys(input)
    val monkeyItems = monkeys.map { monkey -> monkey.first().split(", ").map { it.toInt() }.toMutableList() }
    val monkeyInspections = MutableList(monkeys.size) { 0 }

    repeat(20) {
        for (monkey in monkeys.indices) {
            monkeyItems[monkey].forEach { worryLevel ->
                monkeyInspections[monkey] = monkeyInspections[monkey] + 1
                val (startingItems, operation, tests, testTrue, testFalse) = monkeys[monkey]
                val (operator, operand) = operation.split(" ")

                // during inspection
                val worryLevelDuringInspection = when (operator) {
                    "*" -> worryLevel * when (operand){
                        "old" -> worryLevel
                        else -> operand.toInt()
                    }
                    else -> worryLevel + when (operand){
                        "old" -> worryLevel
                        else -> operand.toInt()
                    }
                }

                // after inspection but before test my worry level
                val worryLevelAfterInspection = floor(worryLevelDuringInspection / 3.0).toInt()

                // test worry level and throw item accordingly
                when (worryLevelAfterInspection % tests.toInt() == 0) {
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

    //println(monkeys)
    //println(monkeyItems)
    return monkeyInspections.sortedDescending().take(2).let { (a,b) -> a*b }
}

fun part2(input: String): ULong {

    val monkeys = parseMonkeys(input)

    val monkeyItems = List(8) { mutableListOf<ULong>() }
    val monkeyInspections = MutableList(8) { 0UL }
    var monkeyDivisor = 1U

    monkeys.forEachIndexed { index, (startingItems, operation, test, testTrue, testFalse) ->
        val worryLevels = startingItems.split(", ").map { it.toULong() }
        worryLevels.forEach { monkeyItems[index].add(it) }
        monkeyDivisor *= test.toUInt()
    }

    repeat(10000) {

        for (monkey in 0 .. 7) {
            monkeyItems[monkey].forEach {  level ->
                monkeyInspections[monkey] = monkeyInspections[monkey] + 1UL
                val (startingItems, operation, tests, testTrue, testFalse) = monkeys[monkey]
                val (operator, operand) = operation.split(" ")

                // during inspection
                val levelIncreased = when (operator) {
                    "*" -> level * when (operand) {
                        "old" -> level
                        else -> operand.toULong()
                    }
                    else -> level + when (operand) {
                        "old" -> level
                        else -> operand.toULong()
                    }
                }

                // after inspection but before test my worry level
                val levelManaged = levelIncreased % monkeyDivisor

                // test worry level
                when (levelManaged % tests.toUInt() == 0UL) {
                    true -> {
                        monkeyItems[testTrue.toInt()].add(levelManaged)
                    }
                    false -> {
                        monkeyItems[testFalse.toInt()].add(levelManaged)
                    }
                }
            }
            monkeyItems[monkey].clear()
        }
    }

    return monkeyInspections.sortedDescending().take(2).let { (a,b) -> a * b }
}

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
    check(part1(test) == 10605)
    check(part1(input) == 57838)

    println(part2(test))
    println(part2(input))

    check(part2(test) == 2713310158UL)
    check(part2(input) == 15050382231UL)
}
