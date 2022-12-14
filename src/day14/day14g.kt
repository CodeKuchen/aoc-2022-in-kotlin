package day14

import utils.PixelGameEngine
import java.awt.Color


class Day14: PixelGameEngine(){

    val width = 500
    val cave = scanCave(input)
    val abyss = cave.keys.map { pos -> pos.y }.max() + 1
    val floor = cave.keys.map { pos -> pos.y }.max() + 2
    var sand = 0

    override fun onCreate() {
        construct(width, floor, 2, 2, "AoC 2022 Day $day")
        limitFps = 5000
        drawCave(cave, width, floor)
    }
    override fun onUpdate(elapsedTime: Long, frame: Long) {
        appInfo = "step #${frame}"
        drawCave(cave, width, floor)

        cave.pourSand(500,0, floor+2, floor).y

        if (sand in 100 .. 27324-100) repeat(5) {
            cave.pourSand(500,0, floor+2, floor).y
        }
        if (sand in 200 .. 27324-200) repeat(10) {
            cave.pourSand(500,0, floor+2, floor).y
        }
        if (sand in 1000 .. 27324-1000) repeat(10) {
            cave.pourSand(500,0, floor+2, floor).y
        }
        sand++

    }

    fun drawCave(cave: MutableMap<Pos, Char>, dx: Int, dy: Int) {
        for (y in 0..dy) {
            for (x in 0..dx*2) {
                if (cave.containsKey(x to y)) {
                    val color = when (cave[x to y]) {
                        '#' -> Color.DARK_GRAY
                        'o' -> Color.ORANGE
                        else -> Color.BLACK
                    }
                    draw(x-250,y, color)
                }
                else
                    draw(x-250,y, Color.BLACK)
            }
            println("")
        }
    }

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

    val g = Day14()
    g.start()
    // check(part1(testInput) == 0)
    // println(part1(input))
    // println(part2(input))
}
