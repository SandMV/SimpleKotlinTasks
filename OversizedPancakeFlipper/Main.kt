import java.io.*

/**
 * Created by sandulmv on 12.05.17.
 */
object Main {
    internal val PATH_RES = "/home/sandulmv/IdeaProjects/CodeJamJB/OversizedPancakeFlipper/res/"
    internal val PATH_TEST = "/home/sandulmv/IdeaProjects/CodeJamJB/OversizedPancakeFlipper/test/"
    internal val TEST_SMALL = "A-small-practice"
    internal val TEST_LARGE = "A-large-practice"

    // tested on large and small data sets: all passed
    @Throws(IOException::class)
    @JvmStatic fun main(args: Array<String>) {
        val test = TEST_LARGE
        val input = BufferedReader(
                FileReader(PATH_TEST + test + ".in")
        )
        val output = PrintWriter(
                FileWriter(PATH_RES + test + ".out")
        )
        val k = Integer.parseInt(input.readLine())
        var s : String
        var tokens: Array<String>
        var res: Int
        for (i in 1..k) {
            s = input.readLine()
            tokens = s.split(" ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            res = solveFlipper(tokens[0], Integer.parseInt(tokens[1]))
            output.printf("Case #%d: %s\n", i, if (res == -1) "IMPOSSIBLE" else res)
        }
        println("Done")
        output.close()
    }

    // simple greedy algorithm
    internal fun solveFlipper(state: String, fSize: Int): Int {
        val arrState = state.toCharArray()
        val length = arrState.size
        var pos = 0
        var flipCount = 0

        while (pos < length) {
            while (pos < length && arrState[pos] != '-') {
                pos++
            }
            val upper = pos + fSize
            var newPos = upper
            var firstEntry = true
            if (upper > length) {
                break
            }
            for (i in pos..upper - 1) {
                when (arrState[i]) {
                    '-' -> arrState[i] = '+'
                    '+' -> {
                        arrState[i] = '-'
                        if (firstEntry) {
                            newPos = i
                            firstEntry = false
                        }
                    }
                }
            }
            pos = newPos
            flipCount++
        }
        if (pos < length) {
            return -1
        }
        return flipCount
    }
}
