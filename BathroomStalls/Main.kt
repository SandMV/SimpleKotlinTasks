import java.io.*
import java.util.*

/**
 * Created by sandulmv on 12.05.17.
 */

object Main {
    internal val PATH_TEST = "/home/sandulmv/IdeaProjects/CodeJamJB/BathroomStalls/test/"
    internal val PATH_RES = "/home/sandulmv/IdeaProjects/CodeJamJB/BathroomStalls/res/"
    internal val TEST_SMALL1 = "C-small-practice-1"
    internal val TEST_SMALL2 = "C-small-practice-2"
    internal val TEST_LARGE = "C-large-practice"

    // Написал несколько решений
    // Для двух "маленьких" наборов данных все решения дали верный ответ
    // Почему-то для большого набора, "умные" решения дали неверные ответы
    // Я так и не смог найти ошибку :(
    // Я протестировал "умные" решения против очевидного на случайных данных
    // для n, k <= 10^5
    // Ответы совпали

    @Throws(IOException::class)
    @JvmStatic fun main(args: Array<String>) {
        val file = TEST_LARGE
        val input = BufferedReader(
                FileReader(PATH_TEST + file + ".in")
        )
        val output = PrintWriter(
                FileWriter(PATH_RES + file + ".out")
        )
        val t = Integer.parseInt(input.readLine())
        var s: String
        var tokens: Array<String>
        var solution: LongArray
        var k: Long
        var n: Long
        for (i in 1..t) {
            s = input.readLine()
            tokens = s.split(" ".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            k = java.lang.Long.parseLong(tokens[1])
            n = java.lang.Long.parseLong(tokens[0])
            solution = smartSolution(n, k)
            output.printf("Case #%d: %d %d\n", i, solution[1], solution[0])
        }
        println("Done")
        output.close()
    }

    // Самый "умный" вариант
    // Работет за O(1)
    internal fun smartSolution(n: Long, k: Long): LongArray {
        val p = (Math.log(k.toDouble()) / Math.log(2.0)).toInt()
        val nSpaces = 1L shl p
        val freeStalls = n - nSpaces + 1
        var spaceSize = freeStalls / nSpaces
        val rest = freeStalls - spaceSize * nSpaces
        val nextWave = k - nSpaces + 1
        if (nextWave > rest) {
            spaceSize--
        }
        val half = spaceSize / 2
        return longArrayOf(half, spaceSize - half)
    }

    // Более "умный" вариант
    // Работает за O(log(k))
    internal fun notSoSmartSolution(n: Long, k: Long): LongArray {
        val maxDepth = (Math.log(k.toDouble()) / Math.log(2.0)).toInt()
        val partition = HashMap<Long, Long>()
        partition.put(n, 1L)
        var firstHalf: Long
        var secondHalf: Long
        val rest = k + 1 - (1L shl maxDepth);
        for (d in 1..maxDepth) {
            val keys = HashSet(partition.keys)
            for (key in keys) {
                firstHalf = (key - 1) / 2
                secondHalf = key - 1 - firstHalf
                partition.put(firstHalf, partition.getOrDefault(firstHalf, 0L) + partition.getOrDefault(key, 0L))
                partition.put(secondHalf, partition.getOrDefault(secondHalf, 0L) + partition.getOrDefault(key, 0L))
                partition.remove(key)
            }

        }
        var maxKey: Long = -1
        var minKey: Long = -1
        for (key in partition.keys) {
            if (key > maxKey) {
                minKey = maxKey
                maxKey = key
            } else {
                minKey = key
            }
        }
        val size = if (partition.getOrDefault(maxKey, 0L) >= rest) maxKey else minKey
        firstHalf = size - 1 shr 1
        secondHalf = size - 1 - firstHalf
        return longArrayOf(firstHalf, secondHalf)
    }

    // Простое очевидное пошаговое решение
    // Работает за O(k * log(k))
    internal fun stupidSolution(n: Long, k: Long): LongArray {
        val queue = PriorityQueue(Collections.reverseOrder<Long>())
        queue.add(n)
        var max: Long = -1
        var min: Long = 1
        for (j in 1..k) {
            val current = queue.poll()
            val n1 = current / 2
            val n2 = (current - 1) / 2
            queue.add(n1)
            queue.add(n2)
            if (j == k) {
                max = n1
                min = n2
            }
        }
        return longArrayOf(min, max)
    }
}

