package day3

import inputTextOfDay
import testTextOfDay

private val Char.priority: Int
    get() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("$this is unknown, check your input!")
    }

fun part1s(text: String): Int =
    text.lines()
        .map { bag -> bag.chunked(bag.length / 2) { half -> half.toSet()} }
        .map { (a, b) -> (a intersect b).single() }
        .sumOf { it.priority }

fun part2s(text: String): Int =
    text.lines()
        .chunked(3) { elves -> elves.map { elf -> elf.toSet()} }
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
