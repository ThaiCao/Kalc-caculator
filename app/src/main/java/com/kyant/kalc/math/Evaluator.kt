package com.kyant.kalc.math

import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import java.util.Stack
import kotlin.math.pow

object Evaluator {
    private var mathContext = MathContext.DECIMAL64

    private const val OPS = "-+/*^"

    fun eval(expr: String): BigDecimal? {
        return try {
            rpnCalculate(infixToPostfix(expr))
        } catch (e: Exception) {
            null
        }
    }

    private fun infixToPostfix(infix: String): String {
        val sb = StringBuilder()
        val s = Stack<Int>()
        val rx = """\s""".toRegex()
        for (
        token in infix
            .replace("−", "-")
            .replace("×", "*")
            .replace("÷", "/")
            .replace("""(\d+)?(\.\d+)?""".toRegex(), " $0 ")
            .replace("""[+\-/*^()]""".toRegex(), " $0 ")
            .split(rx)
        ) {
            if (token.isEmpty()) continue
            val c = token[0]
            val idx = OPS.indexOf(c)

            // check for operator
            if (idx != -1) {
                if (s.isEmpty()) {
                    s.push(idx)
                } else {
                    while (!s.isEmpty()) {
                        val prec2 = s.peek() / 2
                        val prec1 = idx / 2
                        if (prec2 > prec1 || (prec2 == prec1 && c != '^')) {
                            sb.append(OPS[s.pop()]).append(' ')
                        } else break
                    }
                    s.push(idx)
                }
            } else if (c == '(') {
                s.push(-2) // -2 stands for '('
            } else if (c == ')') {
                // until '(' on stack, pop operators.
                while (s.peek() != -2) sb.append(OPS[s.pop()]).append(' ')
                s.pop()
            } else {
                sb.append(token).append(' ')
            }
        }
        while (!s.isEmpty()) sb.append(OPS[s.pop()]).append(' ')
        return sb.toString()
    }

    private fun rpnCalculate(expr: String): BigDecimal {
        if (expr.isEmpty()) throw IllegalArgumentException("Expression cannot be empty")
        val tokens = expr.split(' ').filter { it != "" }
        val stack = mutableListOf<BigDecimal>()
        for (token in tokens) {
            val d = token.toBigDecimalOrNull()
            if (d != null) {
                stack.add(d)
            } else if ((token.length > 1) || (token !in "+-*/^")) {
                throw IllegalArgumentException("$token is not a valid token")
            } else if (stack.size < 2) {
                throw IllegalArgumentException("Stack contains too few operands")
            } else {
                val d1 = stack.removeAt(stack.lastIndex)
                val d2 = stack.removeAt(stack.lastIndex)
                stack.add(
                    when (token) {
                        "+" -> d2.add(d1, mathContext)
                        "-" -> d2.subtract(d1, mathContext)
                        "*" -> d2.multiply(d1, mathContext)
                        "/" -> d2.divide(d1, mathContext)
                        else -> d2.pow(d1, mathContext)
                    }
                )
            }
        }
        return stack.first()
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
