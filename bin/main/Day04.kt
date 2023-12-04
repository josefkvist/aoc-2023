import kotlin.math.pow

fun main() {
  fun part1(input: List<String>): Int {
    var total: Int = 0
    var two: Double = 2.0
    for ((index, value) in input.withIndex()) {
      var numbersStr = value.split(":")[1]
      var (winning, my) = numbersStr.split("|")
      var regexer = Regex("""(\d{1,2})""")
      var winningNumbers = regexer.findAll(winning).map { it.value.toInt() }.toList()
      var myNumbers = regexer.findAll(my).map { it.value.toInt() }.toList()

      var matchingNumbers = myNumbers.filter { winningNumbers.contains(it) }.count()
      var points: Double = two.pow(matchingNumbers - 1)
      total += points.toInt()
    }

    return total
  }

  fun part2(input: List<String>): Int {
    var total: IntArray = IntArray(input.size) { 1 }
    var max = input.size - 1

    for ((index, value) in input.withIndex()) {
      var numbersStr = value.split(":")[1]
      var (winning, my) = numbersStr.split("|")
      var regexer = Regex("""(\d{1,2})""")
      var winningNumbers = regexer.findAll(winning).map { it.value.toInt() }.toList()
      var myNumbers = regexer.findAll(my).map { it.value.toInt() }.toList()

      var matchingNumbers = myNumbers.filter { winningNumbers.contains(it) }.count()
      if (matchingNumbers > 0) {

        var end = listOf(max, index + matchingNumbers).minOrNull() ?: max
        var r: IntRange = IntRange(index + 1, end)
        for (i in r) {
          total[i] = total[i] + total[index]
        }
      }
    }

    return total.sum()
  }
  val testInput = readInput("Day04_test1")
  check(part1(testInput) == 13)
  val testInput2 = readInput("Day04_test2")
  check(part2(testInput2) == 30)
  val input = readInput("Day04")
  part1(input).println()
  part2(input).println()
}
