package day12

import inputTextOfDay
import testTextOfDay

const val day = 12
val testInput = testTextOfDay(day)
val input = inputTextOfDay(day)

typealias Maze = List<MutableList<Int>>
typealias Cell = Pair<Int, Int>

fun main() {
    val maze = createMazeOfInts(input)//.also { printMaze(it) }
    val start = findInMaze(input, 'S').also { println("Start: ${it.first} to ${it.second}") }
    val goal = findInMaze(input, 'E').also { println("End: ${it.first} to ${it.second}") }
    val neighbours: Map<Cell, List<Cell>> = createListsOfNeighbours(maze)
    val reachable: Map<Cell, List<Cell>> = filterNeighbours(neighbours, maze)

    val distance: Int = shortestDistance(setOf(start), goal, maze, reachable, setOf(), 0)
    println(distance)

    val possibleStarts = findAllInMaze(input, 'a').toMutableSet().also{ it.add(start) }
    val minDistance: Int = shortestDistance(possibleStarts, goal, maze, reachable, setOf(), 0)
    println(minDistance)

}

fun shortestDistance(start: Set<Cell>, goal: Cell, maze: Maze, neighbours: Map<Cell, List<Cell>>, visited: Set<Cell>, length: Int): Int {
    val next = mutableSetOf<Cell>()
    val nextLength = length + 1
    val nextVisited = visited union start

    start.forEach { s ->
        if (goal in neighbours[s]!!) return nextLength
        next.addAll(neighbours[s]!!)
    }
    return shortestDistance(next, goal, maze, neighbours, nextVisited, nextLength)
}

fun filterNeighbours(all: Map<Cell, List<Cell>>, maze: Maze): Map<Cell, List<Cell>> {
    val filtered = mutableMapOf<Cell, MutableList<Cell>>()
    maze.forEachIndexed { r, row ->
        for (c in row.indices) {
            val cell: Cell = r to c
            filtered[cell] = mutableListOf()
            all[cell]!!.forEach { n -> if (maze[r][c] + 1 >= maze[n.first][n.second]) filtered[cell]!!.add(n) }
        }
    }
    return filtered
}

fun createListsOfNeighbours(maze: Maze): Map<Cell, List<Cell>> {
    val neighbours = mutableMapOf<Cell, MutableList<Cell>>()
    maze.forEachIndexed { r, row ->
        for (c in row.indices) {
            val cell: Cell = r to c
            neighbours[cell] = mutableListOf()
            if (c + 1 < row.size) neighbours[cell]!!.add(r to c + 1)
            if (c - 1 >= 0) neighbours[cell]!!.add(r to c - 1)
            if (r + 1 < maze.size) neighbours[r to c]!!.add(r + 1 to c)
            if (r - 1 >= 0) neighbours[cell]!!.add(r - 1 to c)
        }
    }
    return neighbours
}

fun findInMaze(input: String, char: Char): Cell {
    val rows = input.lines()
    rows.forEachIndexed { r, row ->
        for (c in row.indices) {
            if (rows[r][c] == char) return r to c
        }
    }
    return -1 to -1
}

fun findAllInMaze(input: String, char: Char): List<Cell> {
    val result = mutableListOf<Cell>()
    val rows = input.lines()
    rows.forEachIndexed { r, row ->
        for (c in row.indices) {
            if (rows[r][c] == char) result.add(r to c)
        }
    }
    return result
}

fun createMazeOfInts(input: String): Maze {
    val rows = input.lines()
    val dy = rows.size
    val dx = rows.first().length
    val maze = createEmptyMaze(dx, dy, 0)
    maze.forEachIndexed { r, row ->
        for (c in row.indices) {
            val inputValue = rows[r][c]
            maze[r][c] = when (inputValue) {
                'S' -> 'a' - 'a'
                'E' -> 'z' - 'a'
                in 'a'..'z' -> inputValue - 'a'
                else -> error("$inputValue")
            }
        }
    }
    return maze
}

@Suppress("unused")
fun printMaze(maze: Maze) {
    maze.forEach { row ->
        row.forEach { value ->
            print("$value".padStart(2, ' '))
        }
        println("")
    }
}

fun createEmptyMaze(dx: Int, dy: Int, defaultValue: Int = 0): Maze {
    return List(dy) { MutableList(dx) { defaultValue } }
}