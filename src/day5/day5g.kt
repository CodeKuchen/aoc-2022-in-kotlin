package day5

import inputTextOfDay
import utils.PixelGameEngine
import java.awt.Color

class Day5g : PixelGameEngine() {

    private val width = 30
    private val height = 80

    val day = 5
    private val text = inputTextOfDay(day)

    private var stack = parseInput(text).first
    private val instructions = parseInput(text).second

    private var inst = 0

    override fun onCreate() {
        stack = parseInput(text).first
        construct(width, height, 20, 8, "AoC 2022 Day $day")
        limitFps = 40
    }

    override fun onUpdate(elapsedTime: Long, frame: Long) {
        appInfo = "step #${frame}"

        // draw new stack
        stack.forEachIndexed { row, columns ->
            columns.forEachIndexed { column, char ->
                draw(row + row + 1, height - (column + column + 1), Color.black)
            }
        }

        // calc new stack
        if(inst < instructions.size) {
            instructions[inst++]?.let { (amount, source, dest) ->
                repeat(amount) {
                    stack[dest - 1].add(stack[source - 1].removeLast())
                }
            }
        }

        // draw new stack
        stack.forEachIndexed { row, columns ->
            columns.forEachIndexed { column, char ->
                draw(row + row + 1, height - (column + column + 1), Color.ORANGE)
            }
        }
    }
}

fun main() {

    val game = Day5g()

    game.start()

}
