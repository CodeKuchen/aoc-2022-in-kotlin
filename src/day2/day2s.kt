package day2

import inputTextOfDay
import testTextOfDay

const val rock = 1
const val paper = 2
const val scissors = 3

const val loose = 0
const val draw = 3
const val win = 6

fun part1s(text: String): Int = text.lines().sumOf {
    when (it) {
        // rock
        "A X" -> rock + draw
        "A Y" -> paper + win
        "A Z" -> scissors + loose
        // paper
        "B X" -> rock + loose
        "B Y" -> paper + draw
        "B Z" -> scissors + win
        // scissors
        "C X" -> rock + win
        "C Y" -> paper + loose
        else -> scissors + draw
    }
}

fun part2s(text: String): Int = text.lines().sumOf {
    when (it) {
        // rock
        "A X" -> loose + scissors
        "A Y" -> draw + rock
        "A Z" -> win + paper
        // paper
        "B X" -> loose + rock
        "B Y" -> draw + paper
        "B Z" -> win + scissors
        // scissors
        "C X" -> loose + paper
        "C Y" -> draw + scissors
        else -> win + rock
    }
}

fun main() {
    val day = 2

    val test = testTextOfDay(day)
    check(part1s(test) == 15)

    val input = inputTextOfDay(day)
    println(part1s(input))
    println(part2s(input))
}
