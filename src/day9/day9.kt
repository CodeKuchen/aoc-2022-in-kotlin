package day9

import inputTextOfDay
import testTextOfDay

fun parseInput(input: String): List<Pair<String, Int>> {
    return input.lines().map { it.split(" ").let { (dir, dist) -> dir to dist.toInt() } }
}

fun part1(input: String): Int {
    return visit(2, input).size
}

fun part2(input: String): Int {
    return visit(10, input).size
}

fun visit(length: Int, input: String): MutableSet<Pair<Int, Int>> {
    val motions = parseInput(input)
    val rope = mutableListOf<Pair<Int,Int>>()
    repeat(length) { rope.add (0 to 0)}

    val visited = mutableSetOf<Pair<Int,Int>>()

    motions.forEach { (dir, dist) ->
        repeat(dist) {
            when (dir) {
                "R" -> rope[0] = rope[0].first + 1 to rope[0].second
                "L" -> rope[0] = rope[0].first - 1 to rope[0].second
                "U" -> rope[0] = rope[0].first to rope[0].second + 1
                "D" -> rope[0] = rope[0].first to rope[0].second - 1
            }

            (1 until rope.size).forEach {
                if (tooFar(rope[it], rope[it-1])) {
                    rope[it] = newTailPos(rope[it-1], rope[it])
                }
            }

            visited.add(rope.last().first to rope.last().second)
        }
    }

    return visited
}

fun newTailPos(headPos: Pair<Int, Int>, tailPos: Pair<Int, Int>): Pair<Int, Int> {
    var first = tailPos.first
    var second = tailPos.second
    if (headPos.first > tailPos.first) first += 1 else if (headPos.first < tailPos.first) first -= 1
    if (headPos.second > tailPos.second) second += 1 else if (headPos.second < tailPos.second) second -= 1
    return first to second
}

fun tooFar(tailPos: Pair<Int, Int>, headPos: Pair<Int, Int>): Boolean {
    return kotlin.math.abs(headPos.first - tailPos.first) > 1 || kotlin.math.abs(headPos.second - tailPos.second) > 1
}


fun main() {
    val day = 9

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 13)

    val input = inputTextOfDay(day)
    check(part1(input) == 5710)
    check(part2(input) == 2259)

    println(part1(input))
    println(part2(input))
}
