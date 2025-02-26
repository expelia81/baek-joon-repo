package sample.장난감

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

fun main() {
    val br: BufferedReader = BufferedReader(InputStreamReader(System.`in`))
    val bw: BufferedWriter = BufferedWriter(OutputStreamWriter(System.out))

    var n = br.readLine().toInt()

    var list = ArrayList<String>()

    for (i in 0..n-1) {
        list.add(br.readLine())
    }
    var alphabet = Array(26) { i -> 'a'.plus(i) };

    var sb = StringBuilder()
    for(i in alphabet) {
        var count = 0;
        for (name in list) {
            if (name.startsWith(i)) count++;
        }
        if (count>=5) {
            sb.append(i)
        }
    }
    if (sb.isBlank()) {
        bw.write("PREDAJA")
    } else {
        bw.write(sb.toString())
    }
    bw.flush()
    br.close()
    bw.close()
}