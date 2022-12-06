package day6

import inputTextOfDay
import testTextOfDay

fun parseInput(input: String): List<String> {
    return input.lines()
}

fun part1(text: String): Int {
    return findMarker(text, 4)
}

fun part2(text: String): Int {
    return findMarker(text, 14)
}

private fun findMarker(text: String, length: Int): Int {
    val similarityCount = text.windowed(length)
        .mapIndexed { index, string -> index to (string.toSet().size == length) }
        .filter { (i, check) -> check }
    return similarityCount.first().first+length
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
