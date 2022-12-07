package day7

import inputTextOfDay
import testTextOfDay

var path = mutableListOf<String>()
var dirSizes = mutableMapOf("/" to 0L)

fun scanDirectory(input: String) {
    path = mutableListOf()
    dirSizes = mutableMapOf("/" to 0L)

    input.lines().forEach {
        val command = it.split(" ")
        when (command[0]) {
            "$" -> when(command[1]) {
                "cd" -> when (command[2]) {
                    ".." -> path.removeLast()
                    else -> path.add(command[2])
                }
            }
            "dir" -> dirSizes[path.joinToString("")+command[1]] = 0L
            else -> path.forEachIndexed { index, segment ->
                val currentPath = path.take(index+1).joinToString("")
                dirSizes[currentPath] = dirSizes[currentPath]!!.toLong() + command[0].toLong()
            }
        }
    }
}
fun part1(input: String): Long {
    scanDirectory(input)

    return dirSizes.toList().filter{(_, size) -> size <= 100_000 }.sumOf { (_, size) -> size }
}

fun part2(input: String): Long {
    scanDirectory(input)

    val total = 70_000_000L
    val required = 30_000_000L
    val used = dirSizes["/"]!!
    val freeSpace = total - used
    val additional = required - freeSpace

    return dirSizes.filter { (_, size) -> size > additional }.minOf { (_, size) -> size }
}

fun main() {
    val day = 7

    val testInput = testTextOfDay(day)
    println("Test1: " + part1(testInput))
    println("Test2: " + part2(testInput))

    check(part1(testInput) == 95437L)
    check(part2(testInput) == 24933642L)

    val input = inputTextOfDay(day)

    println("Part1: " + part1(input))
    println("Part2: " + part2(input))

    check(part1(input) == 1845346L)
    check(part2(input) == 3636703L)
}
