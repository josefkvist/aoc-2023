
import kotlin.time.DurationUnit
import kotlin.time.TimeSource

fun main() {
  println("Day07")
  fun part1(input: List<String>): Int {
    return input.size
  }

  fun part2(input: List<String>): Int {
    return input.size
  }
  val testInput = readInput("Day07_test1")
  check(part1(testInput) == 0)
  val testInput2 = readInput("Day07_test2")
  check(part2(testInput2) == 0)
  val input = readInput("Day07")
  val mark1 = timeSource.markNow()

  part1(input).println()
  val mark2 = timeSource.markNow()

  part2(input).println()
  val mark3 = timeSource.markNow()

  println((mark2 - mark1).toString(DurationUnit.MICROSECONDS))
  println((mark3 - mark2).toString(DurationUnit.MICROSECONDS))
}
