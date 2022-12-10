package day10

import inputTextOfDay
import testTextOfDay


fun part1(input: String): Int {

    val instructions = input.lines()
    var cycle = 1
    var x = 1

    var strength = 0

    instructions.forEach {
        when {
            it.startsWith("addx") -> {
                strength += measureStrength(cycle, x) // during
                cycle++

                strength += measureStrength(cycle, x) // during
                cycle++
                x += it.substringAfter("addx ").toInt() // after
            }

            else -> {
                strength += measureStrength(cycle, x) // during
                cycle++
            }
        }
    }
    return strength
}

private fun measureStrength(cycle: Int, x: Int): Int = when {
    cycle == 20 -> cycle * x
    (cycle - 20) % 40 == 0 -> cycle * x
    else -> 0
}

fun part2(input: String) {

    val instructions = input.lines()
    var cycle = 1
    var x = 1

    val screen = MutableList(40 * 6) { "." }
    var crt = 0

    instructions.forEach {
        when {
            it.startsWith("addx") -> {
                draw(crt, x, screen, cycle) // during
                cycle++
                crt = move(crt)

                draw(crt, x, screen, cycle) // during
                cycle++
                x += it.substringAfter("addx ").toInt() // after
                crt = move(crt) // after
            }

            else -> {
                draw(crt, x, screen, cycle) // during
                cycle++
                crt = move(crt) // after
            }
        }
    }

    printScreen(screen)

}

private fun move(crt: Int) = (crt + 1) % 40

private fun draw(crt: Int, x: Int, screen: MutableList<String>, cycle: Int ) {
    if (crt in x - 1..x + 1) screen[cycle-1] = "#"
}

fun printScreen(screen: List<String>, size: Int = 40) {
    for (line in screen.chunked(size)) {
        println(line.joinToString(""))
    }
}

fun main() {
    val test = testTextOfDay(day)
    val input = inputTextOfDay(day)

    check(part1(test) == 13140)
    println("Test ${part1(test)}")
    part2(test)

    check(part1(input) == 15120)
    println("Input ${part1(input)}")
    part2(input)
}

const val day = 10
