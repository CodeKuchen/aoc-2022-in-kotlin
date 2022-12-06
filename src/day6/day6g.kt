package day6

import inputTextOfDay
import utils.PixelGameEngine
import java.awt.Color
import kotlin.random.Random

class Day6g : PixelGameEngine() {

    val day = 6
    val input = inputTextOfDay(day)
    private val length = 4

    private val width = 61
    private val height = 3
    private val pixelWidth = 20
    private val pixelHeight = 20

    private var step = 0

    private val colors = "abcdefghijklmnopqrstuvwxyz".map { it to Color(Random.nextInt(0, 255), Random.nextInt(0, 255), Random.nextInt(0, 255)) }

    override fun onCreate() {
        construct(width, height, pixelWidth, pixelHeight, "AoC 2022 Day $day")
        limitFps = 100
    }

    override fun onUpdate(elapsedTime: Long, frame: Long) {
        appInfo = "step #${frame}"

        val scannerValue = input.drop(step+10).take(length).toSet().size

        input.drop(step).take(40).forEachIndexed { index, c ->
            val color = colors.first { (char, _) -> char == c }.second
            draw(index + index + 1, height - 2, color)
            drawString(c.toString(), Color.GREEN, index + index + 1, height - 1, pixelHeight/3, -pixelWidth/3)
        }

        // draw scanner
        draw(20, height - 1, Color.RED)
        draw(20, height - 2, Color.RED)
        draw(20, height - 3, Color.RED)
        draw(20+length*2, height - 1, Color.RED)
        draw(20+length*2, height - 2, Color.RED)
        draw(20+length*2, height - 3, Color.RED)

        if (scannerValue != length) {
            step++
        } else {
            drawString(scannerValue.toString(), Color.RED, 12*2, height - 2, pixelHeight/3, - pixelWidth/3)
            drawString((step+10+length).toString(), Color.RED, 12*2, height - 0, pixelHeight/3, - pixelWidth/3)
        }
    }
}

fun main() {

    val game = Day6g()

    game.start()

}
