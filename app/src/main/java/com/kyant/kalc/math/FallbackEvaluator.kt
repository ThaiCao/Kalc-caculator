package com.kyant.kalc.math

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.math.pow

object FallbackEvaluator {
    private var mathContext = MathContext.DECIMAL64

    private fun String.toBigDecimalOrZero() = this.toBigDecimalOrNull() ?: BigDecimal.ZERO

    private fun multiply(s: String): String {
        val b = s.split('*').map { it.toBigDecimalOrZero() }
        return (b[0].pow(b[1], mathContext).toString())
    }

    private fun divide(s: String): String {
        val b = s.split('/').map { it.toBigDecimalOrZero() }
        return (b[0].divide(b[1], mathContext).toString())
    }

    private fun add(s: String): String {
        val t = s.replace("""^\+""".toRegex(), "").replace("""\++""".toRegex(), "+")
        val b = t.split('+').map { it.toBigDecimalOrZero() }
        return (b[0].add(b[1], mathContext).toString())
    }

    private fun subtract(s: String): String {
        val t = s.replace("""(\+-|-\+)""".toRegex(), "-")
        if ("--" in t) return add(t.replace("--", "+"))
        val b = t.split('-').map { it.toBigDecimalOrZero() }
        return (
            if (b.size == 3) -b[1].subtract(b[2], mathContext)
            else b[0].subtract(b[1], mathContext)
            ).toString()
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

    fun eval(expr: String): BigDecimal? {
        var t = expr
            .replace("−", "-")
            .replace("×", "*")
            .replace("÷", "/")
            .replace("""\s""".toRegex(), "").replace("""^\+""".toRegex(), "")
        val rePara = """\([^()]*\)""".toRegex()
        while (true) {
            val match = rePara.find(t) ?: break
            val exp = match.value
            t = t.replace(exp, evalExp(exp))
        }
        return evalExp(t).toBigDecimalOrNull()
    }

    private fun BigDecimal.pow(n: BigDecimal, mathContext: MathContext): BigDecimal {
        var right = n
        val signOfRight = right.signum()
        right = right.multiply(signOfRight.toBigDecimal())
        val remainderOfRight = right.remainder(BigDecimal.ONE)
        val n2IntPart = right.subtract(remainderOfRight)
        val intPow = pow(n2IntPart.intValueExact(), mathContext)
        val doublePow = toDouble().pow(remainderOfRight.toDouble()).toBigDecimal()

        var result = intPow.multiply(doublePow, mathContext)
        if (signOfRight == -1) result = BigDecimal
            .ONE.divide(result, mathContext.precision, RoundingMode.HALF_UP)

        return result
    }
}
