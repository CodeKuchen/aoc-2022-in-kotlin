package day9

import inputTextOfDay
import testTextOfDay
import kotlin.math.abs

fun parseInput(input: String): List<Pair<Direction, Int>> =
    input.lines().map { line ->
        line.split(" ").let { (d, n) -> Direction.valueOf(d) to n.toInt() }
    }

fun part1(input: String) = visit(2, input).size
fun part2(input: String) = visit(10, input).size

fun visit(ropeSize: Int, input: String): MutableSet<Pos> {

    val motions = parseInput(input)

    val knots = MutableList(ropeSize) { Pos(0,0) }
    val visited = mutableSetOf(knots.last())

    for ((direction, n) in motions) {
        repeat(n) {
            knots.head += direction.move
            for (tail in 1 until knots.size) {
                knots[tail] = knots[tail] + tailMove(knots[tail - 1], knots[tail])
            }
            visited += knots.last()
        }
    }

    return visited
}

val Move.chebyshevDistance: Int get () = maxOf(abs(dx), abs(dy))

fun tailMove(head: Pos, tail: Pos): Move {
    val tailToHead = head - tail
    return if (tailToHead.chebyshevDistance > 1)
        Move(tailToHead.dx.coerceIn(-1,1), tailToHead.dy.coerceIn(-1,1))
    else
        Move(0,0)
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

private var <E> MutableList<E>.head: E
    get() = first()
    set(e) = run { set(0, e) }

data class Pos(val x: Int, val y: Int){
    override fun toString() = "(x:$x, y:$y)"
}
data class Move(val dx: Int, val dy: Int)

private operator fun Pos.plus(move: Move): Pos = copy(x = x + move.dx, y = y + move.dy)
private operator fun Pos.minus(other: Pos): Move = Move(this.x - other.x, this.y - other.y)

enum class Direction(val move: Move) {
    U(Move(0, 1)),
    R(Move(1, 0)),
    D(Move(0, -1)),
    L(Move(-1, 0))
}
