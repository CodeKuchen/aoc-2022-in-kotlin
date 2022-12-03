package day3

import inputTextOfDay
import testTextOfDay

private val Char.priority: Int
    get() = when (this) {
        in 'a'..'z' -> this - 'a' + 1
        else -> this - 'A' + 27
    }

fun part1(text: String): Int =
    text.lines()
        .map { it.chunked(it.length / 2) }
        .map { (a, b) -> a.first { char -> b.count { it == char } >= 1 }
        }
        .sumOf { it.priority }

fun part2(text: String): Int =
    text.lines()
        .chunked(3)
        .map { (a, b, c) ->
            a.first { item ->
                b.count { it == item } >= 1 && c.count() { it == item } >= 1
            }
        }
        .sumOf { it.priority }

fun main() {
    val day = 3

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 157)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
