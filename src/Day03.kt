fun main() {
  var numPattern = """(\d+)"""
  var symbolPattern = """[*&/@=+#$%-]"""
  var symbolPattern2 = """[*]"""

  fun String.findValues(): List<MatchResult> {
    var regex = Regex(numPattern)
    return regex.findAll(this).toList()
  }
  fun String.findSymbols(): List<Int> {
    var regex = Regex(symbolPattern)
    return regex.findAll(this).map { it.range.first }.toList()
  }
  fun String.findSymbolsPart2(): List<Int> {
    var regex = Regex(symbolPattern)
    return regex.findAll(this).map { it.range.first }.toList()
  }

  fun part1(input: List<String>): Int {
    var maxY = input.size - 1
    var maxX = input[0].length - 1
    var total: Int = 0
    var symbols = ArrayList<Pair<Int, Int>>()
    for ((lineIndex, l) in input.withIndex()) {
      var lineSymbols = l.findSymbols()
      for (sym in lineSymbols) {
        symbols.add(Pair(lineIndex, sym))
      }
    }

    for ((lineIndex, l) in input.withIndex()) {
      println(l)
      var fromY = listOf(lineIndex - 1, 0).maxOrNull() ?: 0
      var toY = listOf(lineIndex + 1, maxY).minOrNull() ?: 0
      var numbers = l.findValues()
      for (num in numbers) {
        var fromX = listOf(num.range.first - 1, 0).maxOrNull() ?: 0
        var toX = listOf(num.range.last + 1, maxX).minOrNull() ?: 0
        var any =
            symbols.any({
              it.first >= fromY && it.first <= toY && it.second >= fromX && it.second <= toX
            })
        if (any) {
          total += num.value.toInt()
        }
      }
    }
    return total
  }

  fun part2(input: List<String>): Int {

    var maxY = input.size - 1
    var maxX = input[0].length - 1
    var total: Int = 0
    var numbers = ArrayList<Num>() // y, x, val
    for ((lineIndex, l) in input.withIndex()) {
      var nums = l.findValues()
      for (sym in nums) {
        numbers.add(Num(sym.range, lineIndex, sym.value.toInt()))
      }
    }

    for ((lineIndex, l) in input.withIndex()) {
      println(l)
      var fromY = listOf(lineIndex - 1, 0).maxOrNull() ?: 0
      var toY = listOf(lineIndex + 1, maxY).minOrNull() ?: 0
      var symbols = l.findSymbolsPart2()
      for (sym in symbols) {
        var fromX = listOf(sym - 1, 0).maxOrNull() ?: 0
        var toX = listOf(sym + 1, maxX).minOrNull() ?: 0
        var any =
            numbers.filter({
              it.y >= fromY &&
                  it.y <= toY &&
                  (it.x.contains(fromX) || it.x.contains(sym) || it.x.contains(toX))
            })
        if (any.count() > 1) {
          var sum: Int = 1
          for (n in any) {
            sum = sum * n.value
          }
          total += sum
        }
      }
    }
    println(input.size)
    return total
  }
  val testInput = readInput("Day03_test1")
  check(part1(testInput) == 4361)
  val testInput2 = readInput("Day03_test2")
  check(part2(testInput2) == 467835)
  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}

data class Num(val x: IntRange, val y: Int, val value: Int)
