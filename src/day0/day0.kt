package day0

import inputTextOfDay
import testTextOfDay

fun part1(input: String): Int {
    return input.length
}

fun part2(input: String): Int {
    return input.length
}

fun main() {
    val day = 0

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 0)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
