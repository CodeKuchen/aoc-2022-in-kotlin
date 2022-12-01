package day01

import inputTextOfDay
import testTextOfDay

fun parseInput(input: String): List<List<Int>> {
    return input.split("\r\n\r\n").map { elf -> elf.lines().map { it.toInt() } }
}

fun part1(input: String): Int {
    val data = parseInput(input)
    return data.maxOf { it.sum() }
}

fun part2(input: String): Int {
    val data = parseInput(input)
    return data.map { it.sum() }
        .sortedDescending()
        .take(3)
        .sum()
}

fun main() {
    val day = 1

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 24000)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
