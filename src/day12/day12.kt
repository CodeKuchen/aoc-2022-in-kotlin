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
    val start = findSingleInMaze(input, 'S')//.also { println("Start: ${it.first} to ${it.second}") }
    val goal = findSingleInMaze(input, 'E')//.also { println("End: ${it.first} to ${it.second}") }
    val allNeighbours = createListsOfNeighbours(maze)
    val reachable = createListsOfReachableNeighbours(maze, allNeighbours)

    // part1
    val distance: Int = distanceOfShortestPath(setOf(start), goal, reachable, setOf(), 0)
    println(distance)

    // part2
    val possibleStarts = findAllInMaze(input, 'a') union setOf(start)
    val minDistance: Int = distanceOfShortestPath(possibleStarts, goal, reachable, setOf(), 0)
    println(minDistance)

}

fun distanceOfShortestPath(starts: Set<Cell>, goal: Cell, neighbours: Map<Cell, List<Cell>>, visited: Set<Cell>, length: Int): Int {

    fun distanceOfShortestPath(starts: Set<Cell>, visited: Set<Cell>, length: Int): Int {
        val nextStart = mutableSetOf<Cell>()
        val nextLength = length + 1
        val nextVisited = visited union starts

        if (starts.isEmpty()) error("(${goal.first}, ${goal.second}) not reachable")

        starts.forEach { cell ->
            val cellNeighbours = neighbours.getOrDefault(cell, emptyList())
            if (goal in cellNeighbours) return nextLength.also{ println(starts.size)}
            nextStart.addAll(cellNeighbours)
        }
        return distanceOfShortestPath(nextStart, nextVisited, nextLength)
    }

    return distanceOfShortestPath(starts, visited, length)
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


fun findSingleInMaze(input: String, char: Char): Cell {
    return findAllInMaze(input, char).single()
}

fun findAllInMaze(input: String, char: Char): Set<Cell> {
    val result = mutableSetOf<Cell>()
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