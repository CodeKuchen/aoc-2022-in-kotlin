package day14

import inputTextOfDay
import testTextOfDay
import kotlin.math.max
import kotlin.math.min

const val day = 14
val test = testTextOfDay(day)
val input = inputTextOfDay(day)

typealias Pos = Pair<Int, Int>

val Pos.x get() = first
val Pos.y get() = second

typealias Cave = MutableMap<Pos, Char>

fun scanCave(input: String): Cave {
    val cave = mutableMapOf<Pos, Char>()
    val scans = input.lines()
    val paths = scans.map { scan ->
        scan.split(" -> ").map { node ->
            node.split(",").let { (x, y) -> x.toInt() to y.toInt() }
        }
    }
    paths.forEach { path ->
        path.windowed(2).forEach { (from: Pos, to: Pos) ->
            for (x in min(from.x, to.x)..max(from.x, to.x)) {
                for (y in min(from.y, to.y)..max(from.y, to.y))
                    cave[x to y] = '#'
            }
        }
    }
    return cave
}

fun printCave(cave: MutableMap<Pos, Char>, dim: Int) {
    for (y in 0..dim) {
        for (x in 400..600) {
            if (cave.containsKey(x to y))
                print(cave[x to y])
            else
                print('.')
        }
        println("")
    }
}

fun Cave.pourSand(x: Int, y: Int, abyss: Int, floor: Int): Pair<Int, Int> {
    var next = x to y
    do {
        val current = next
        // possible drop positions
        val down: Pos = current.x to current.y + 1
        val left: Pos = current.x-1 to current.y + 1
        val right: Pos = current.x+1 to current.y + 1

        // drop if possible
        if (down.y != floor) {
            if (!this.containsKey(down)) next = down
            else if (!this.containsKey(left)) next = left
            else if (!this.containsKey(right)) next = right
        }

    } while (next.y != current.y && next.y != abyss)
    this[next] = 'o'
    return next
}

fun main() {
    // part1
    val cave1 = scanCave(input)
    val abyss = cave1.keys.map { pos -> pos.y }.max() + 1

    var drops1 = -1
    do {
        drops1++
        val y = cave1.pourSand(500,0, abyss, abyss+1).y
    } while (y != abyss)
    println(drops1)
    check(drops1 == 644)


    // part2
    val cave2 = scanCave(input)
    val floor = cave2.keys.map { pos -> pos.y }.max() + 2

    var drops2 = 0
    do {
        drops2++
        val y = cave2.pourSand(500,0, floor+2, floor).y
    } while (y != 0)
    println(drops2)
    check(drops2 == 27324)

     printCave(cave2, floor)

    // check(part1(testInput) == 0)
    // println(part1(input))
    // println(part2(input))
}
