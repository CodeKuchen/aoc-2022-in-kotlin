package day2

import day2.Hand.*
import day2.Outcome.*
import inputTextOfDay
import testTextOfDay

enum class Hand(val value: Int) {
    Rock(1),
    Paper(2),
    Scissors(3)
}

enum class Outcome(val value: Int) {
    Loose(0),
    Draw(3),
    Win(6)
}

fun evaluate(elf: Hand, me: Hand): Int {
    return when (me) {
        Rock -> Rock + when (elf) {
            Rock -> Draw
            Paper -> Loose
            Scissors -> Win
        }

        Paper -> Paper + when (elf) {
            Rock -> Win
            Paper -> Draw
            Scissors -> Loose

        }

        Scissors -> Scissors + when (elf) {
            Rock -> Loose
            Paper -> Win
            Scissors -> Draw

        }
    }
}

fun evaluate(elf: Hand, outcome: Outcome): Int {
    return when (outcome) {
        Loose -> {
            Loose + when (elf) {
                Rock -> Scissors
                Paper -> Rock
                Scissors -> Paper
            }
        }

        Draw -> {
            Draw + when (elf) {
                Rock -> Rock
                Paper -> Paper
                Scissors -> Scissors
            }
        }

        Win -> {
            Win + when (elf) {
                Rock -> Paper
                Paper -> Scissors
                Scissors -> Rock
            }
        }
    }
}

private operator fun Hand.plus(outcome: Outcome): Int = this.value + outcome.value
private operator fun Outcome.plus(hand: Hand): Int = this.value + hand.value

fun part1(text: String): Int {
    return text.lines().sumOf { evaluate(toHand(it[0]), toHand(it[2])) }
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

fun part2(text: String): Int {
    return text.lines().sumOf { evaluate(toHand(it[0]), toOutcome(it[2])) }
}

fun main() {
    val day = 2

    val test = testTextOfDay(day)
    check(part1(test) == 15)

    val input = inputTextOfDay(day)
    println(part1(input))
    println(part2(input))
}
