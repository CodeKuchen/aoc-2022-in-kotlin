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
    val start = findInMaze(input, 'S')//.also { println("Start: ${it.first} to ${it.second}") }
    val goal = findInMaze(input, 'E')//.also { println("End: ${it.first} to ${it.second}") }
    val allNeighbours = createListsOfNeighbours(maze)
    val reachable = createListsOfReachableNeighbours(maze, allNeighbours)

    // part1
    val distance: Int = shortestDistance(setOf(start), goal, maze, reachable, setOf(), 0)
    println(distance)

    // part2
    val possibleStarts = findAllInMaze(input, 'a').toMutableSet().also{ it.add(start) }
    val minDistance: Int = shortestDistance(possibleStarts, goal, maze, reachable, setOf(), 0)
    println(minDistance)

}

fun shortestDistance(starts: Set<Cell>, goal: Cell, maze: Maze, neighbours: Map<Cell, List<Cell>>, visited: Set<Cell>, length: Int): Int {
    val nextStart = mutableSetOf<Cell>()
    val nextLength = length + 1
    val nextVisited = visited union starts

    if (starts.isEmpty()) error("(${goal.first}, ${goal.second}) not reachable")

    starts.forEach { cell ->
        if (goal in neighbours[cell]!!) return nextLength.also{ println(starts.size)}
        nextStart.addAll(neighbours[cell]!!)
    }
    return shortestDistance(nextStart, goal, maze, neighbours, nextVisited, nextLength)
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

fun createListsOfReachableNeighbours(maze: Maze, neighbours: Map<Cell, List<Cell>>): Map<Cell, List<Cell>> {
    val reachableNeighbours = mutableMapOf<Cell, MutableList<Cell>>()

    maze.forEachIndexed { r, row ->
        for (c in row.indices) {
            val currentCell: Cell = r to c
            reachableNeighbours[currentCell] = mutableListOf()
            neighbours[currentCell]!!.forEach { neighbour ->
                if (neighbourIsReachable(currentCell, neighbour, maze)) reachableNeighbours[currentCell]!!.add(neighbour)
            }
        }
    }

    return reachableNeighbours
}

private fun neighbourIsReachable(currentCell: Cell, neighbour: Cell, maze: Maze) =
    maze[currentCell.first][currentCell.second] >= maze[neighbour.first][neighbour.second] - 1

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