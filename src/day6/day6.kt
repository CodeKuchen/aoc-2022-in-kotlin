package day6

import inputTextOfDay
import testTextOfDay

fun part1(input: String): Int {
    return findStartOfMarker(input, 4)
}

fun part2(input: String): Int {
    return findStartOfMarker(input, 14)
}

private fun findStartOfMarker(input: String, length: Int): Int {
    return input
        .windowed(length)
        .map { it.toSet().size == length }
        .indexOfFirst { it } + length
}

fun main() {
    val day = 6

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
