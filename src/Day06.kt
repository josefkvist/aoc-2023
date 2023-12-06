import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.time.DurationUnit
import kotlin.time.TimeSource

fun main() {

  fun pq(p: Double, q: Double): Solution {
    var x1 = -p / 2 - sqrt((p / 2).pow(2) - q) + 0.001
    var x2 = -p / 2 + sqrt((p / 2).pow(2) - q) - 0.001
    x1.println()
    x2.println()
    return Solution(x1, x2)
  }

  fun part1(input: List<String>): Int {
    var pattern = """(\d+)"""
    var regex = Regex(pattern)
    var times = regex.findAll(input[0]).map { it.value.toDouble() }.toList()
    var distances = regex.findAll(input[1]).map { it.value.toDouble() }.toList()

    var total: Int = 1
    for (i in times.indices) {
      println(i)
      var time = times[i]
      var distance = distances[i]
      var p = -time
      var q = distance
      var (x1, x2) = pq(p, q)

      var first = ceil(x1).toInt()
      var second = floor(x2).toInt()
      first.println()
      second.println()
      var tot = (second - first + 1)
      println(tot)
      total *= tot
    }

    return total
  }

  fun part2(input: List<String>): Int {
    var pattern = """(\d+)"""
    var regex = Regex(pattern)
    var time = regex.findAll(input[0]).map { it.value }.joinToString("") { it }.toDouble()
    var distance = regex.findAll(input[1]).map { it.value }.joinToString("") { it }.toDouble()

    var p = -time
    var q = distance
    var (x1, x2) = pq(p, q)

    var first = ceil(x1).toInt()
    var second = floor(x2).toInt()
    first.println()
    second.println()
    var tot = (second - first + 1)
    println(tot)

    return tot
  }
  val testInput = readInput("Day06_test1")
  check(part1(testInput) == 288)
  val testInput2 = readInput("Day06_test1")
  check(part2(testInput2) == 71503)
  val input = readInput("Day06")
  val timeSource = TimeSource.Monotonic
  val mark1 = timeSource.markNow()

  part1(input).println()
  val mark2 = timeSource.markNow()

  part2(input).println()
  val mark3 = timeSource.markNow()

  println((mark2 - mark1).toString(DurationUnit.MICROSECONDS))
  println((mark3 - mark2).toString(DurationUnit.MICROSECONDS))
}

data class Solution(var x1: Double, var x2: Double)
