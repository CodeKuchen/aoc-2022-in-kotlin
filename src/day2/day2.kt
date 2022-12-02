package day2

import day2.Hand.*
import day2.Outcome.*
import inputTextOfDay
import testTextOfDay

enum class Hand(val value: Int) {
    Rock(1), Paper(2), Scissors(3)
}

enum class Outcome(val value: Int) {
    Loose(0), Draw(3), Win(6)
}

fun part1(text: String): Int {
    return text.lines().sumOf { evaluate(toHand(it[0]), toHand(it[2])) }
}

fun part2(text: String): Int {
    return text.lines().sumOf { evaluate(toHand(it[0]), toOutcome(it[2])) }
}

fun evaluate(elf: Hand, me: Hand): Int = when (elf) {
    Rock -> me + when (me) {
        Rock -> Draw
        Paper -> Win
        Scissors -> Loose
    }

    Paper -> me + when (me) {
        Rock -> Loose
        Paper -> Draw
        Scissors -> Win
    }

    Scissors -> me + when (me) {
        Rock -> Win
        Paper -> Loose
        Scissors -> Draw
    }
}

fun evaluate(elf: Hand, outcome: Outcome): Int = when (elf) {
    Rock -> outcome + when (outcome) {
        Loose -> Scissors
        Draw -> Rock
        Win -> Paper
    }

    Paper -> outcome + when (outcome) {
        Loose -> Rock
        Draw -> Paper
        Win -> Scissors
    }

    Scissors -> outcome + when (outcome) {
        Loose -> Paper
        Win -> Rock
        Draw -> Scissors
    }
}

fun toHand(hand: Char): Hand = when (hand) {
    'A', 'X' -> Rock
    'B', 'Y' -> Paper
    else -> Scissors
}

fun toOutcome(result: Char): Outcome = when (result) {
    'X' -> Loose
    'Y' -> Draw
    else -> Win
}

private operator fun Hand.plus(outcome: Outcome): Int = this.value + outcome.value
private operator fun Outcome.plus(hand: Hand): Int = this.value + hand.value

fun main() {
    val day = 2

    val test = testTextOfDay(day)
    check(part1(test) == 15)

    val input = inputTextOfDay(day)
    println(part1(input))
    println(part2(input))
}
