package sample

import java.lang.Error
import kotlin.math.pow
import kotlin.math.sqrt

class Calculator {

    fun isStringAnEquation(p_equation: String): Boolean {
        if (p_equation.isEmpty())
            return false

        var temp = true
        for (character in p_equation) {
            when (character) {
                '0','1','2','3','4','5','6','7','8','9',
                '(',')','-','+','*','/','%','^', 'v', '.' -> temp = true
                else -> return false
            }
        }
        return temp
    }

    fun run(input: String): String {

        val operators: Stack<Char> = Stack()
        var output = ""

        var lastCharacter = ' '
        var dotCount = 0
        for (character in input) {
            if(character == '.') {
                dotCount++
                if(lastCharacter !in '0'..'9' || dotCount > 1) {
//                    window.alert("Cannot do equation on provided such string")
                    throw Exception("Cannot introduce such string")
                }
            }
            if (character == '(') {
                if(lastCharacter in '0'..'9') {
                    output += " "
                }
                operators.push(character)
            }
            else if (character == ')') {
                if(lastCharacter in '0'..'9') {
                    output += " "
                }
                while (operators.peek() != '(') {
                    output += "${operators.peek()} "
                    operators.pop()
                }
                operators.pop()
            }
            else if (isOperator(character)) {
                if(lastCharacter in '0'..'9') {
                    output += " "
                }
                if((lastCharacter == '+'
                            || lastCharacter =='*' || lastCharacter == '/' || lastCharacter == '%'
                            || lastCharacter == '^' ) && character == '-' ) {

                    output += "-"
                }
                else {
                    if (operators.isEmpty() || priority(character) > priority(operators.peek())) {
                        operators.push(character)
                    } else {
                        while (priority(operators.peek()) >= priority(character)) {
                            output += "${operators.peek()} "
                            operators.pop()
                        }
                        operators.push(character)
                    }
                }
            }
            else {
                if((character in '0'..'9' || character == '.') &&
                    (lastCharacter in '0'..'9' || lastCharacter == ' ')
                    || lastCharacter == '-' || lastCharacter == '+'
                    || lastCharacter == '*' || lastCharacter == '/' || lastCharacter == '%'
                    || lastCharacter == '^' || lastCharacter == 'v'|| lastCharacter == '(') {
                    output += "$character"
                }
                else {
                    output += "$character "
                }
            }

            lastCharacter = character
        }
        while (!operators.isEmpty()) {
            output += " ${operators.peek()}"
            operators.pop()
        }

        return output
    }

    fun calculate(input: String): Double?
    {
        var stackDouble: Stack<Double> = Stack()
        var result = 0.0
        var temp = ""
        var it = 1
        for (character in input) {
            if (isOperator(character)) {
                if(temp.lastOrNull() in '0'..'9') {
                    stackDouble.push(temp.toDouble())
                    temp = ""
                }
                if(character == '-' && input[it] != ' ') {
                    temp += character
                    if(it == input.length) {
                        result = doEquation(stackDouble, character)
                    }
                    continue
                }
                result = doEquation(stackDouble, character)
            }
            else if (character == ' ') {
                if(temp.lastOrNull() in '0'..'9') {
                    stackDouble.push(temp.toDouble())
                    temp = ""
                }
            }
            else {
                temp += character
            }
            it += 1
        }
        return result
    }

    private fun doEquation(stack: Stack<Double>, c: Char): Double {
        var temp = 0.0
        var d: Double

        if(stack.isEmpty()) {
//            window.alert("Impossible operation")
            throw Exception("There were no numbers provided, please type another equation")
        }
        when (c) {
            '-' -> {
                temp = stack.peek()!!
                if(stack.size() >= 2) {
                    stack.pop()
                    d = stack.peek()!!
                    temp = d - temp
                    stack.replaceTop(temp)
                }
                else {
                    throw Error("")
                }
            }
            '+' -> {
                temp = stack.peek()!!
                if(stack.size() >= 2) {
                    stack.pop()
                    d = stack.peek()!!
                    temp = d + temp
                    stack.replaceTop(temp)
                }
                else {
                    throw Error("")
                }
            }
            '*' -> {
                temp = stack.peek()!!
                if(stack.size() >= 2) {
                    stack.pop()
                    d = stack.peek()!!
                    temp = d * temp
                    stack.replaceTop(temp)
                }
                else {
                    throw Error("")
                }

            }
            '/' -> {
                temp = stack.peek()!!
                if(stack.size() >= 2) {
                    stack.pop()
                    d = stack.peek()!!
                    temp = d / temp
                    stack.replaceTop(temp)
                }
                else {
                    throw Error("")
                }
            }
            '%' -> {
                temp = stack.peek()!!
                if(stack.size() >= 2) {
                    stack.pop()
                    d = stack.peek()!!
                    temp = d%temp
                    stack.replaceTop(temp)
                }
                else {
                    throw Error("")
                }
            }
            '^' -> {
                temp = stack.peek()!!
                if(stack.size() >= 2) {
                    stack.pop()
                    d = stack.peek()!!
                    temp = d.pow(temp)
                    stack.replaceTop(temp)
                }
                else {
                    throw Error("")
                }

            }
            'v' -> {
                temp = stack.peek()!!
                temp = sqrt(temp)
                stack.replaceTop(temp)
            }
        }
        return temp
    }

    private fun priority(peek: Char?): Int {
        return when (peek) {
            '^', 'v' -> 3
            '*', '/' -> 2
            '+', '-', ')' -> 1
            else -> 0
        }
    }

    private fun isOperator(character: Char): Boolean {
        return when (character) {
            '-', '+', '*', '/', '%', '^', 'v' -> true
            else -> false
        }
    }
}