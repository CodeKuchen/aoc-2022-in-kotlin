package day3

import inputTextOfDay
import testTextOfDay

private val Char.priority: Int
    get() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        in 'A'..'Z' -> this - 'A' + 27
        else -> error("$this is unknown, check your input!")
    }

fun part1(text: String): Int =
    text.lines()
        .map { it.chunked(it.length / 2) { it.toSet()} }
        .map { (a, b) -> a.single { item -> b.count { it == item } >= 1 } }
        .sumOf { it.priority }

fun part2(text: String): Int =
    text.lines()
        .chunked(3) { elves -> elves.map { elf -> elf.toSet()} }
        .map { (a, b, c) -> a.single { item -> b.count { it == item } >= 1 && c.count { it == item } >= 1 } }
        .sumOf { it.priority }

fun main() {
    val day = 3

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 157)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
