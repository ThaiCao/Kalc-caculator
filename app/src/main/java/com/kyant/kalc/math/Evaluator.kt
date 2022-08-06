package com.kyant.kalc.math

import java.math.BigDecimal

object Evaluator {
    private fun String.toBigDecimalOrZero() = this.toBigDecimalOrNull() ?: BigDecimal.ZERO

    private fun multiply(s: String): String {
        val b = s.split('*').map { it.toBigDecimalOrZero() }
        return (b[0] * b[1]).toString()
    }

    private fun divide(s: String): String {
        val b = s.split('/').map { it.toBigDecimalOrZero() }
        return (b[0] / b[1]).toString()
    }

    private fun add(s: String): String {
        val t = s.replace("""^\+""".toRegex(), "").replace("""\++""".toRegex(), "+")
        val b = t.split('+').map { it.toBigDecimalOrZero() }
        return (b[0] + b[1]).toString()
    }

    private fun subtract(s: String): String {
        val t = s.replace("""(\+-|-\+)""".toRegex(), "-")
        if ("--" in t) return add(t.replace("--", "+"))
        val b = t.split('-').map { it.toBigDecimalOrZero() }
        return (if (b.size == 3) -b[1] - b[2] else b[0] - b[1]).toString()
    }

    private fun evalExp(s: String): String {
        var t = s.replace("""[()]""".toRegex(), "")
        val reMD = """\d+\.?\d*\s*[*/]\s*[+-]?\d+\.?\d*""".toRegex()
        val reM = """\*""".toRegex()
        val reAS = """-?\d+\.?\d*\s*[+-]\s*[+-]?\d+\.?\d*""".toRegex()
        val reA = """\d\+""".toRegex()

        while (true) {
            val match = reMD.find(t) ?: break
            val exp = match.value
            val match2 = reM.find(exp)
            t = if (match2 != null) {
                t.replace(exp, multiply(exp))
            } else {
                t.replace(exp, divide(exp))
            }
        }

        while (true) {
            val match = reAS.find(t) ?: break
            val exp = match.value
            val match2 = reA.find(exp)
            t = if (match2 != null) {
                t.replace(exp, add(exp))
            } else {
                t.replace(exp, subtract(exp))
            }
        }

        return t
    }

    fun evalArithmeticExp(s: String): BigDecimal? {
        var t = "1.00000000000000000*$s".replace("""\s""".toRegex(), "").replace("""^\+""".toRegex(), "")
        val rePara = """\([^()]*\)""".toRegex()
        while (true) {
            val match = rePara.find(t) ?: break
            val exp = match.value
            t = t.replace(exp, evalExp(exp))
        }
        return evalExp(t).toBigDecimalOrNull()?.stripTrailingZeros()
    }
}
