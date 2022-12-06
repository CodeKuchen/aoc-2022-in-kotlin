package day7

import inputTextOfDay
import testTextOfDay

fun part1(input: String): Int {
    return 0
}

fun part2(input: String): Int {
    return 0
}

fun main() {
    val day = 7

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 0)
    check(part2(testInput) == 0)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
