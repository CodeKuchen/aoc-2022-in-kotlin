package day0

import inputTextOfDay
import testTextOfDay

fun parseInput(input: String): List<String> {
    return input.lines()
}

fun part1(text: String): Int {
    val lines = parseInput(text)
    return 0
}

fun part2(text: String): Int {
    val lines = parseInput(text)
    return 0
}

fun main() {
    val day = 0

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 0)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
