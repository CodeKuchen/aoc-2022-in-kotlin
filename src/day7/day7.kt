package day7

import inputTextOfDay
import testTextOfDay

fun part1(input: String): Int {
    val maxSize = 100_000
    return scanDirectorySizes(input)
        .toList()
        .filter { it <=  maxSize }
        .sumOf { it }
}

fun part2(input: String): Int {
    val dirSizes = scanDirectorySizes(input)

    val total = 70_000_000
    val required = 30_000_000
    val used = dirSizes.first()
    val freeSpace = total - used
    val additional = required - freeSpace

    return dirSizes
        .filter { it > additional }
        .minOf { it }
}

fun scanDirectorySizes(input: String): List<Int> {
    val path = mutableListOf<String>()
    val dirSizes = mutableMapOf("/" to 0)

    input.lines().forEach {
        val command = it.split(" ")
        when (command[0]) {
            "$" -> when (command[1]) {
                "cd" -> when (command[2]) {
                    ".." -> path.removeLast()
                    else -> path.add(command[2])
                }
            }
            "dir" -> {}
            else -> path.forEachIndexed { index, _ ->
                val currentPath = path.take(index + 1).joinToString("")
                dirSizes[currentPath] = dirSizes.getOrDefault(currentPath,0) + command[0].toInt()
            }
        }
    }
    return dirSizes.map { (_, size) -> size }
}

fun main() {
    val day = 7

    val testInput = testTextOfDay(day)
    println("Test1: " + part1(testInput))
    println("Test2: " + part2(testInput))

    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = inputTextOfDay(day)

    println("Part1: " + part1(input))
    println("Part2: " + part2(input))

    check(part1(input) == 1845346)
    check(part2(input) == 3636703)
}
