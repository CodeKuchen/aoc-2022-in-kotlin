import java.io.File

fun inputTextOfDay(n: Int): String {
    return File("src/day$n/input$n.txt").readText()
}

fun testTextOfDay(n: Int): String {
    return File("src/day$n/test$n.txt").readText()
}
