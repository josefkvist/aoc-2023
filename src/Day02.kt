fun main() {
  var pattern = """\d{1,2}\s{1}(green|blue|red)"""

  fun String.findValues(): List<String> {
    var regex = Regex(pattern)
    return regex.findAll(this).map { it.value }.toList()
  }

  fun part1(input: List<String>): Int {
    var games = ArrayList<Int>()

    line@ for ((index, value) in input.withIndex()) {

      var values = value.findValues()
      for (i in values.indices) {
        var (num, col) = values[i].split(" ")
        var numVal = num.toInt()
        var maxLimit: Int =
            when (col) {
              "green" -> 13
              "blue" -> 14
              "red" -> 12
              else -> {
                throw Exception("no color match")
              }
            }
        if (numVal > maxLimit) {
          continue@line
        }
      }

      var gameNum = index + 1
      games.add(gameNum)
    }
    return games.sum()
  }

  fun part2(input: List<String>): Int {
    var games = ArrayList<Int>()

    for (value in input) {
      var values = value.findValues()
      var green: Int = 0
      var blue: Int = 0
      var red: Int = 0
      for (i in values.indices) {
        var (num, col) = values[i].split(" ")
        var numVal = num.toInt()
        when (col) {
          "green" -> {
            green = listOf(green, numVal).maxOrNull() ?: 0
          }
          "blue" -> {
            blue = listOf(blue, numVal).maxOrNull() ?: 0
          }
          "red" -> {
            red = listOf(red, numVal).maxOrNull() ?: 0
          }
          else -> {
            throw Exception("no color match")
          }
        }
      }
      var factor: Int = green * red * blue

      games.add(factor)
    }
    return games.sum()
  }
  val testInput = readInput("Day02_test1")
  check(part1(testInput) == 8)
  val testInput2 = readInput("Day02_test2")
  check(part2(testInput2) == 2286)
  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}
