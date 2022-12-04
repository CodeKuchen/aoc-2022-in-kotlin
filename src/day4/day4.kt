package day4

import inputTextOfDay
import testTextOfDay

val input = inputTextOfDay(4)
fun getInput(input: String): List<Pair<Set<Int>, Set<Int>>> {
    return input.lines().map {
        it.split(',','-').map(String::toInt)
            .let{ (a,b,c,d)
                -> (a..b).toSet() to (c..d).toSet() }
    }
}

fun part1(text: String): Int {
    return getInput(text).count { (a, b) -> a intersect b == a || a intersect b == b }
}

fun part2(text: String): Int {
    return getInput(text).count { (a, b) -> (a intersect b).isNotEmpty() }
}

fun main() {
    val day = 4

    val testInput = testTextOfDay(day)
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    check(part1(input) == 602)
    check(part2(input) == 891)

    println(part1(input))
    println(part2(input))

}
