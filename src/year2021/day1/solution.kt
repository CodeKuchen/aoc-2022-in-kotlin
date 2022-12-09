package year2021.day1

import java.io.File

const val year = 2021
const val day = 1
val test = File("src/year$year/day$day/test.txt").readText()
val input = File("src/year$year/day$day/input.txt").readText()

fun part1(input: String): Int {
    val measurements = input.lines().map { it.toInt() }
    return countIncrements(measurements)
}
fun part2(input: String): Int {
    val measurements = input.lines().map { it.toInt() }.windowed(3).map { it.sum() }
    return countIncrements(measurements)
}

fun countIncrements(measurements: List<Int>): Int {
    return List(measurements.size) { index ->
        when (index) {
            0 -> false
            else -> measurements[index] > measurements[index - 1]
        }
    }.count { it }
}

fun main() {
    println("TEST --->")
    println(part1(test))
    println(part2(test))

    println("INPUT --->")
    println(part1(input))
    println(part2(input))
}