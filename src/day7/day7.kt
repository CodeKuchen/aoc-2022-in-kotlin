package day7

import inputTextOfDay
import testTextOfDay

typealias File = Pair<Long, String>

fun part1(input: String): Long {
    var path = mutableListOf<String>()
    var dirSizes = mutableMapOf("/" to 0L)

    input.lines().forEach {
        val command = it.split(" ")
        when (command[0]){
            "$" -> when(command[1]) {
                "cd" -> when (command[2]) {
                    ".." -> path.removeLast()
                    else -> path.add(command[2])
                }
                "ls" -> if (dirSizes.containsKey(path.last()) ) dirSizes[path.last()] = 0L
            }
            "dir" -> dirSizes[command[1]] = 0L
            else -> path.forEach{ segment ->
                dirSizes[segment] = dirSizes[segment]!!.toLong() + command[0].toLong()
            }
        }
    }

    return dirSizes.toList().filter{(dir, size) -> size <= 100_000 }.sumOf { (dir, size) -> size }
}

fun part2(input: String): Int {
    return 0
}

fun main() {
    val day = 7

    val testInput = testTextOfDay(day)
    println("Test1: " + part1(testInput))
    println("Test2: " + part2(testInput))

    check(part1(testInput) == 95437L)
    check(part2(testInput) == 0)

    val input = inputTextOfDay(day)

    println("Part1: " + part1(input))
    println("Part2: " + part2(input))

    check(part1(input) != 1540861L)
    //check(part2(input) == 0)
}
