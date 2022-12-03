package day3

import inputTextOfDay
import testTextOfDay

private val Char.priority: Int
    get() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        else -> this - 'A' + 27
    }

fun part1s(text: String): Int =
    text.lines()
        .map { it.chunked(it.length / 2) { bag -> bag.toSet()} }
        .map { (a, b) -> (a intersect b).single() }
        .sumOf { it.priority }

fun part2s(text: String): Int =
    text.lines()
        .chunked(3) { it.map { elf -> elf.toSet()} }
        .map { (a, b, c) -> (a intersect b intersect c).single() }
        .sumOf { it.priority }

fun main() {
    val day = 3

    val testInput = testTextOfDay(day)
    check(part1s(testInput) == 157)

    val input = inputTextOfDay(day)

    println(part1s(input))
    println(part2s(input))
}
