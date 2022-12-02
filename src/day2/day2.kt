package day2

import inputTextOfDay
import testTextOfDay

fun evaluateHands(first: Char, second: Char): Int {
    if (second == 'X') { // Rock
        val points = 1
        if (first == 'A') return points + 3 // Rock
        if (first == 'B') return points // Paper
        if (first == 'C') return points + 6 // Scissor
    }
    if (second == 'Y') { // Paper
        val points = 2
        if (first == 'A') return points + 6
        if (first == 'B') return points + 3
        if (first == 'C') return points
    }
    if (second == 'Z') { // Scissors
        val points = 3
        if (first == 'A') return points
        if (first == 'B') return points + 6
        if (first == 'C') return points + 3
    }
    return 0
}

fun pickHand(first: Char, second: Char): Int {
    if (second == 'X') { // loose
        val hand = 0
        if (first == 'A') return hand + 3
        if (first == 'B') return hand + 1
        if (first == 'C') return hand + 2
    }
    if (second == 'Y') { // draw
        val hand = 3
        if (first == 'A') return hand + 1
        if (first == 'B') return hand + 2
        if (first == 'C') return hand + 3
    }
    if (second == 'Z') { // win
        val hand = 6
        if (first == 'A') return hand + 2
        if (first == 'B') return hand + 3
        if (first == 'C') return hand + 1
    }
    return 0
}

fun part1(text: String): Int {
    return text
        .lines()
        .sumOf { round -> evaluateHands(round[0], round[2]) }
}

fun part2(text: String): Int {

    return text
        .lines()
        .sumOf { round -> pickHand(round[0], round[2]) }
}

fun main() {
    val day = 2

    val test = testTextOfDay(day)
    check(part1(test) == 15)

    val input = inputTextOfDay(day)
    println(part1(input))
    println(part2(input))
}
