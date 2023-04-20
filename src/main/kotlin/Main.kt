import kotlin.math.pow

fun main(args: Array<String>) {
    fun intToBin(num: Int): String {
        var count = num
        var s = ""
        while (count >= 1) {
            s += "${count % 2}"
            count /= 2
        }
        return s.reversed().padStart(7, '0')
    }

    fun binToInt(bin: String): Int {
        var num = 0
        for (i in bin.indices) {
            if (bin[i] == '1') {
                num += 2.0.pow((bin.length - i - 1).toDouble()).toInt()
            }
        }
        return num
    }

    fun encode() {
        print("Input string:\n")
        val line = readln()
        var s = ""
        for (i in line) {
            s += intToBin(i.code)
        }
        var counterOne = 0
        var counterZero = 0
        var encoded = ""
        for (i in 0..s.length - 2) {
            val j = s[i]
            when {
                j == '1' && s[1 + i] == '1' -> {
                    counterOne += 1
                    counterZero = 0
                    if (i + 1 == s.indices.last) {
                        encoded += "0 ${"0".repeat(counterOne + 1)}"
                    }
                }

                j == '1' && s[1 + i] == '0' -> {
                    counterOne += 1
                    encoded += "0 ${"0".repeat(counterOne)} "
                    counterZero = 0
                    if (i + 1 == s.indices.last) {
                        encoded += "00 0"
                    }
                }

                j == '0' && s[1 + i] == '0' -> {
                    counterOne = 0
                    counterZero += 1
                    if (i + 1 == s.indices.last) {
                        encoded += "00 ${"0".repeat(counterZero + 1)}"
                    }
                }

                j == '0' && s[1 + i] == '1' -> {
                    counterZero += 1
                    encoded += "00 ${"0".repeat(counterZero)} "
                    counterOne = 0
                    if (i + 1 == s.indices.last) {
                        encoded += "0 0"
                    }
                }
            }
        }
        println("Encoded string:")
        println(encoded)
    }

    fun decode() {
        print("Input encoded string:\n")
        val line = readln()
        val sub = line.split(" ")
        println(sub.filterIndexed { index, _ -> index % 2 == 1 }
            .sumOf { it.length })
        when {
            !line.all { it == '0' || it == ' ' } -> println("not valid")
            !sub.filterIndexed { index, _ -> index % 2 == 0 }
                .all { it == "0" || it == "00" } -> println("not valid")

            sub.size % 2 != 0 -> println("not valid")
            sub.filterIndexed { index, _ -> index % 2 == 1 }
                .sumOf { it.length } % 7 != 0 -> println("not valid")

            else -> {
                var bin = ""
                for (i in sub.indices step 2) {
                    when (sub[i]) {
                        "0" -> {
                            bin += "1".repeat(sub[i + 1].length)
                        }

                        "00" -> {
                            bin += "0".repeat(sub[i + 1].length)
                        }
                    }
                }
                var decoded = ""
                val binChunksOfSeven = bin.chunked(7)
                for (chunk in binChunksOfSeven) {
                    decoded += binToInt(chunk).toChar()
                }

                println("Decoded string:")
                println(decoded)
            }
        }
    }
    while (true) {
        println("Please input operation (encode/decode/exit):")
        when (val input = readln()) {
            "encode" -> encode()
            "decode" -> decode()
            "exit" -> {
                println("Bye!")
                break
            }

            else -> println("There is no '$input' operation")
        }
    }
}
