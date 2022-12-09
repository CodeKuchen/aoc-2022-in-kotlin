package year2021.day0

import java.io.File

const val year = 2021
const val day = 0

val test = File("src/year$year/day$day/test.txt").readText()
val input = File("src/year$year/day$day/input.txt").readText()

fun part1(input: String): Int {
    return 0
}
fun part2(input: String): Int {
    return 0
}

fun main() {
    println("TEST --->")
    println(part1(test))
    println(part2(test))

    println("INPUT --->")
    println(part1(input))
    println(part2(input))
}