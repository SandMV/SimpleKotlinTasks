import java.io.*
import java.util.Arrays

/**
 * Created by sandulmv on 12.05.17.
 */
object Main {
    internal val PATH_RES = "/home/sandulmv/IdeaProjects/CodeJamJB/TidyNumbers/res/"
    internal val PATH_TEST = "/home/sandulmv/IdeaProjects/CodeJamJB/TidyNumbers/test/"
    internal val TEST_SMALL = "B-small-practice"
    internal val TEST_LARGE = "B-large-practice"

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
        var s: String
        for (i in 1..k) {
            s = input.readLine()
            output.printf("Case #%d: %s\n", i, largestTidyNumber(s.toCharArray()))
        }
        println("Done")
        output.close()
    }

    // simple solution based on operations on digits
    internal fun largestTidyNumber(num: CharArray): String {
        var num = num
        val length = num.size
        for (i in 1..length - 1) {
            if (num[i] < num[i - 1]) {
                bringOrder(num, i)
                for (j in i + 1..length - 1) {
                    num[j] = '9'
                }
                break
            }
        }
        if (num[0] == '0') {
            num = Arrays.copyOfRange(num, 1, length)
        }
        return String(num)
    }

    internal fun bringOrder(num: CharArray, from: Int) {
        var i = from - 1
        while (i >= 0) {
            if (num[i + 1] < num[i]) {
                num[i + 1] = '9'
                while (num[i] == '0') {
                    num[i--] = '9'
                }
                num[i]--
            } else {
                return
            }
            i--
        }
    }

}
