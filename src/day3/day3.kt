package day3

import inputTextOfDay
import testTextOfDay
fun part1(text: String): Int {
    val lines = text.lines()
    return 0
}
fun part2(text: String): Int {
    val lines = text.lines()
    return 0
}
fun main() {
    val day = 3

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 0)

    val input = inputTextOfDay(day)

    println(part1(input))
    println(part2(input))
}
