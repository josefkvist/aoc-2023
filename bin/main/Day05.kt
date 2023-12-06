import kotlin.time.DurationUnit
import kotlin.time.TimeSource

fun main() {
  println("Day05")
  fun part1(input: List<String>): Long {
    var seeds: LongArray =
        input[0].split(" ").filter { !it.contains("seeds") }.map { it.toLong() }.toLongArray()

    var inputSize = input.size
    var rowIndex = 2
    var containers = ArrayList<MapContainer>()
    var row: String

    while (rowIndex < inputSize) {

      row = input[rowIndex]
      var name = row
      if (row.contains("map")) {

        var maps = ArrayList<Map>()
        rowIndex++
        if (rowIndex >= inputSize) {
          break
        }
        while (true) {
          if (rowIndex >= inputSize) {
            break
          }
          row = input[rowIndex]
          if (row.length == 0) {
            break
          }

          var splits = row.split(" ")
          maps.add(Map(splits[0].toLong(), splits[1].toLong(), splits[2].toLong()))

          rowIndex++
        }

        containers.add(MapContainer(name, maps))
        rowIndex++
      }
    }

    for (i in containers.indices) {
      var cont = containers[i]
      for (k in seeds.indices) {

        var map =
            cont.maps
                .filter { seeds[k] >= it.sourceStart && seeds[k] < it.sourceStart + it.rangeLength }
                .toList()
        if (map.any()) {
          var change = map.first().destinationStart - map.first().sourceStart
          seeds[k] += change
        }
      }
    }

    var min = seeds.min()
    return min
  }

  fun part2(input: List<String>): Long {

    // create long ranges

    var seedsArray: LongArray =
        input[0].split(" ").filter { !it.contains("seeds") }.map { it.toLong() }.toLongArray()
    var seeds = ArrayList<LongRange>()
    for (i in 0..seedsArray.size - 1 step 2) {
      var start = seedsArray[i].toLong()
      var range = seedsArray[i + 1].toLong()
      seeds.add(LongRange(start, start + range - 1))
    }
    // save all maps
    var inputSize = input.size
    var rowIndex = 2
    var containers = ArrayList<MapContainer>()
    var row: String

    while (rowIndex < inputSize) {

      row = input[rowIndex]
      var name = row
      if (row.contains("map")) {

        var maps = ArrayList<Map>()
        rowIndex++
        if (rowIndex >= inputSize) {
          break
        }
        while (true) {

          if (rowIndex >= inputSize) {
            break
          }
          row = input[rowIndex]
          if (row.length == 0) {
            break
          }

          var splits = row.split(" ")
          maps.add(Map(splits[0].toLong(), splits[1].toLong(), splits[2].toLong()))

          rowIndex++
        }

        containers.add(MapContainer(name, maps))
        rowIndex++
      }
    }
    // loop through each container
    for (i in containers.indices) {
      var cont = containers[i]
      // create two arrays of longRange, mapped, not-mapped
      var mapped = ArrayList<LongRange>()
      var notMapped = ArrayList<LongRange>(seeds)
      // loop trhough all maps
      for (map in cont.maps) {
        var range = LongRange(map.sourceStart, map.sourceStart + map.rangeLength - 1)
        var diff = map.destinationStart - map.sourceStart
        var matched =
            notMapped
                .filter { it ->
                  (range.start <= it.start && range.endInclusive >= it.start) ||
                      (range.start <= it.endInclusive && range.endInclusive >= it.endInclusive) ||
                      (it.start <= range.start && it.endInclusive >= range.endInclusive)
                }
                .toList()
        // remove from notmapped
        notMapped.removeAll(matched)
        // loop through matched
        for (match in matched) {
          // move mapped to mapped
          var overlapStart = range.start <= match.start
          var overlapEnd = range.endInclusive >= match.endInclusive

          if (overlapStart && overlapEnd) {
            // map is bigger -> move all to mapped
            var p1 = LongRange(match.start + diff, match.endInclusive + diff)

            mapped.add(p1)
          } else if (overlapStart && !overlapEnd) {
            // map overlaps at start -> divide into two, start is mapped
            var p1 = LongRange(match.start + diff, range.endInclusive + diff)
            var p2 = LongRange(range.endInclusive + 1, match.endInclusive)
            mapped.add(p1)
            notMapped.add(p2)
          } else if (!overlapStart && overlapEnd) {
            // map overlaps at end -> divide into two, end is mapped
            var p1 = LongRange(match.start, range.start - 1)
            var p2 = LongRange(range.start + diff, match.endInclusive + diff)
            notMapped.add(p1)
            mapped.add(p2)
          } else {
            // map is smaller -> divide into three
            var p1 = LongRange(match.start, range.start - 1)
            var p2 = LongRange(range.start + diff, range.endInclusive + diff)
            var p3 = LongRange(range.endInclusive + 1, match.endInclusive)
            notMapped.add(p1)
            mapped.add(p2)
            notMapped.add(p3)
          }
        }
      }
      // add all not-mapped to mapped
      mapped.addAll(notMapped)
      seeds.clear()
      seeds.addAll(mapped)

      // next container
    }
    // get min from minstart
    var min = seeds.map { it.start }.toLongArray().min()
    return min
  }

  val testInput = readInput("Day05_test1")
  var result = 35
  check(part1(testInput) == result.toLong())
  val testInput2 = readInput("Day05_test2")
  var result2 = 46
  check(part2(testInput2) == result2.toLong())
  val input = readInput("Day05")
  val timeSource = TimeSource.Monotonic
  val mark1 = timeSource.markNow()

  part1(input).println()
  val mark2 = timeSource.markNow()

  part2(input).println()
  val mark3 = timeSource.markNow()

  println((mark2 - mark1).toString(DurationUnit.MICROSECONDS))
  println((mark3 - mark2).toString(DurationUnit.MICROSECONDS))
}

data class MapContainer(val name: String, var maps: ArrayList<Map>)

data class Map(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long)
