package day9

import inputTextOfDay
import testTextOfDay
import kotlin.math.abs

fun parseInput(input: String): List<Motion> {
    return input.lines().map { it[0].toDirection() to it.substringAfter(" ").toInt() }
}

fun part1(input: String): Int {
    return visit(2, input).size
}

fun part2(input: String): Int {
    return visit(10, input).size
}

fun visit(ropeSize: Int, input: String): MutableSet<Point> {

    val motions = parseInput(input)
    val rope = (1..ropeSize).map { ORIGIN }.toMutableList()

    val visited = mutableSetOf<Point>()

    motions.forEach { (direction, steps) ->
        repeat(steps) {
            rope.head += direction
            (1 until rope.size).forEach { tail ->
                if (tooFar(rope[tail], rope[tail - 1]))
                    rope[tail] = newTail(rope[tail - 1], rope[tail])
            }
            visited.add(rope.last().x to rope.last().y)
        }
    }

    return visited
}

private fun Char.toDirection(): Point {
    return when (this) {
        'U', 'N' -> UP
        'R', 'E' -> RIGHT
        'D', 'S' -> DOWN
        'L', 'W' -> LEFT
        else -> error("could not transform $this to Point")
    }
}

fun newTail(head: Point, tail: Point): Point {
    return tail.x + when {
        head.x > tail.x -> 1
        head.x < tail.x -> -1
        else -> 0
    } to tail.y + when {
        head.y > tail.y -> 1
        head.y < tail.y -> -1
        else -> 0
    }
}

fun tooFar(tail: Point, head: Point): Boolean {
    return abs(head.x - tail.x) > 1 || abs(head.y - tail.y) > 1
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


private typealias Steps = Int
private typealias Point = Pair<Int, Int>
private typealias Direction = Point
private typealias Motion = Pair<Direction, Steps>

private var <E> MutableList<E>.head: E
    get() = first()
    set(e) = run { set(0, e) }

private val Point.x: Int get() = first
private val Point.y: Int get() = second

private operator fun Point.plus(other: Point): Point = first + other.first to second + other.second
private operator fun Point.minus(other: Point): Point = first - other.first to second - other.second

val UP: Direction = +1 to 0
val RIGHT: Direction = 0 to +1
val DOWN: Direction = -1 to 0
val LEFT: Direction = 0 to -1
val ORIGIN: Point get() = 0 to 0
