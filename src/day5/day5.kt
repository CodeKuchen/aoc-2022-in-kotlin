package day5

import inputTextOfDay

fun parseInput(input: String): Pair<MutableList<MutableList<Char>>, List<List<Int>>> {
    return input.split("\r\n\r\n").let { (_, second) -> getStacks() to parseInstructions(second) }
}

fun getStacks(): MutableList<MutableList<Char>> {
    val s1 = listOf('N', 'R', 'J', 'T', 'Z', 'B', 'D', 'F')
    val s2 = listOf('H', 'J', 'N', 'S', 'R')
    val s3 = listOf('Q', 'F', 'Z', 'G', 'J', 'N', 'R', 'C')
    val s4 = listOf('Q', 'T', 'R', 'G', 'N', 'V', 'F')
    val s5 = listOf('F', 'Q', 'T', 'L')
    val s6 = listOf('N', 'G', 'R', 'B', 'Z', 'W', 'C', 'Q')
    val s7 = listOf('M', 'H', 'N', 'S', 'L', 'C', 'F')
    val s8 = listOf('J', 'T', 'M', 'Q', 'N', 'D')
    val s9 = listOf('S', 'G', 'P')
    return listOf(s1, s2, s3, s4, s5, s6, s7, s8, s9).map { it.reversed().toMutableList() }.toMutableList()
}

fun parseInstructions(second: String): List<List<Int>> {
    return second.lines().map { line -> line.split("move ", " from ", " to ").mapNotNull { it.toIntOrNull() } }
}

fun part1(text: String): String {
    val (stack, instructions) = parseInput(text)

    instructions.forEach { (amount, source, dest) ->
        repeat(amount) {
            stack[dest - 1].add(stack[source - 1].removeLast())
        }
    }

    return stack.map { it.last() }.joinToString("")
}

fun part2(text: String): String {
    val (stack, instructions) = parseInput(text)

    instructions.forEach { (amount, source, dest) ->
        val lifted = mutableListOf<Char>()
        repeat(amount) {
            lifted.add(stack[source - 1].removeLast())
        }
        repeat(amount) {
            stack[dest - 1].add(lifted.removeLast())
        }
    }

    return stack.map { it.last() }.joinToString("")
}

fun main() {
    val day = 5

    // val testInput = testTextOfDay(day)
    // check(part1(testInput) == 5)

    val input = inputTextOfDay(day)

    println(part1(input))
    check(part1(input) == "QNNTGTPFN")

    println(part2(input))
    check(part2(input) == "GGNPJBTTR")
}
