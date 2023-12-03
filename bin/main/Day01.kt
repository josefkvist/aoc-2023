fun main() {

    fun part1(input: List<String>): Int {
        var returnVal: Int = 0
        for (i in input) {
            var first: Char = '0'
            for (j in i) {
                if (j.isDigit()) {
                    first = j
                    break
                }
            }
            var last: Char = '0'

            for (k in i.reversed()) {
                if (k.isDigit()) {
                    last = k
                    break
                }
            }
            var full: String = "${first}${last}"
            var value = full.toInt()
            returnVal += value
        }

        return returnVal
    }

    fun part2(input: List<String>): Int {
        var array: List<String> =
                listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val myArrayList = ArrayList<String>()
        for (q in array) {
            myArrayList.add(q.reversed())
        }
        var returnVal: Int = 0
        for (i in input) {
            var line: String = i
            var first: Char = '0'
            firstFor@ for ((index, value) in line.withIndex()) {
                if (value.isDigit()) {
                    first = value
                    break@firstFor
                } else {
                    var subStr = line.substring(index)
                    for ((i2, v2) in array.withIndex()) {
                        if (subStr.startsWith(v2)) {

                            first = (i2 + 1).toString()[0]
                            break@firstFor
                        }
                    }
                }
            }
            var last: Char = '0'
            var reversedLine = line.reversed()
            lastFor@ for ((ki, kv) in reversedLine.withIndex()) {
                if (kv.isDigit()) {
                    last = kv
                    break@lastFor
                } else {
                    var subStr = reversedLine.substring(ki)
                    for ((i2, v2) in myArrayList.withIndex()) {
                        if (subStr.startsWith(v2)) {

                            last = (i2 + 1).toString()[0]
                            break@lastFor
                        }
                    }
                }
            }
            var full: String = "${first}${last}"
            var value = full.toInt()
            returnVal += value
        }

        return returnVal
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part2(testInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
