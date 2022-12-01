package day02

import inputTextOfDay
import testTextOfDay

fun parseInput(input: String): List<String> {
    return input.lines()
}

fun part1(input: String): Int {
    val data = parseInput(input)
    return 0
}

fun part2(input: String): Int {
    val data = parseInput(input)
    return 0
}

fun main() {
    val day = 2

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 0)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
