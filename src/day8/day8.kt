package day8

import inputTextOfDay
import testTextOfDay

val heights = mutableListOf<MutableList<Int>>()
val visibility = mutableListOf<MutableList<Boolean>>()
val scenicScore = mutableListOf<MutableList<Int>>()

fun parseInput(text: String) {
    heights.clear()
    visibility.clear()
    scenicScore.clear()

    text.lines()
        .forEach { line ->
            heights.add(line.map { it.digitToInt() }.toMutableList())
            visibility.add(line.map { false }.toMutableList())
            scenicScore.add(line.map { -1 }.toMutableList())
        }
}

fun part1(text: String): Int {
    parseInput(text)

    visibility.forEachIndexed { r, row ->
        row.indices.forEach{ c ->
            visibility[r][c] = isVisible(r, c, heights)
        }
    }

    return visibility.flatten().count { it }
}

fun isVisible(row: Int, col: Int, heights: List<List<Int>>): Boolean {
    val max = heights.size - 1

    // edge is always visible
    if (row == 0 || col == 0 || row == max || col == max) return true

    // trees to the
    val left = heights[row].take(col)
    val right = heights[row].drop(col + 1)
    val top = heights.map { line -> line[col] }.take(row)
    val bottom = heights.map { line -> line[col] }.drop(row + 1)

    // visible if all trees in at least one direction are smaller
    return listOf(right, left, top, bottom).any { it.max() < heights[row][col] }
}

fun part2(text: String): Int {
    parseInput(text)

    scenicScore.forEachIndexed { r, row ->
        row.forEachIndexed { c, _ ->
            scenicScore[r][c] = getScore(r, c, heights)
        }
    }

    return scenicScore.flatten().max()
}

fun getScore(row: Int, col: Int, heights: List<List<Int>>): Int {
    val max = heights.size - 1
    if (row == 0 || col == 0 || row == max || col == max) return 0

    // trees to the
    val left = heights[row].take(col).reversed()
    val right = heights[row].drop(col + 1)
    val top = heights.map { line -> line[col] }.take(row)
    val bottom = heights.map { line -> line[col] }.drop(row + 1)

    var l = left.indexOfFirst { it >= heights[row][col] } + 1
    var r = right.indexOfFirst { it >= heights[row][col] } + 1
    var u = top.take(row).reversed().indexOfFirst { it >= heights[row][col] } + 1
    var d = bottom.drop(row + 1).indexOfFirst { it >= heights[row][col] } + 1

    if (l == 0) l = left.size
    if (r == 0) r = right.size
    if (u == 0) u = top.size
    if (d == 0) d = bottom.size

    return l * r * u * d
}

fun main() {
    val day = 8

    val testInput = testTextOfDay(day)
    println("Test1: " + part1(testInput))
    println("Test2: " + part2(testInput))

    check(part1(testInput) == 21)
    check(part2(testInput) == 8)

    val input = inputTextOfDay(day)

    println("Part1: " + part1(input))
    println("Part2: " + part2(input))

    check(part1(input) == 1543)
    check(part2(input) == 595080)
}
