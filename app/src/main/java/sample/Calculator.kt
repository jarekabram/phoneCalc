package sample

import java.lang.StringBuilder

class Calculator {

    var mRpnExpression = Stack<String>()

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

    fun run(input: String): Boolean {

        val operators: Stack<Char> = Stack()
        var isNegative = false
        var expectOperator = false
        var number = StringBuilder()
        var count = 0
        var operator: Char
        for(character in input) {
            if (Character.isDigit(character)) {
                while (count < input.length && (Character.isDigit(input[count]) || character === '.')) {
                    number.append(character)
                    count++
                }
                count--
                if (isNegative) {
                    var temp = "-$number"
                    number.clear()
                    number.append(temp)
                    isNegative = false
                }
                mRpnExpression.push(number.toString())
                number.setLength(0)
                expectOperator = true
            } else {
                operator = input.substring(count, count + 1).first()
                if (isOperator(operator)) {
                    if (!expectOperator && operator == '-') {
                        isNegative = true
                        count++
                        continue
                    }
                    while (operators.size() !== 0 && operators.peek()!! != '('
                        && (priority(operators.peek()) > priority(operator) || priority(operators.peek()) === priority(
                            operator
                        ) && operators.peek()!! != operator)
                    ) {
                        mRpnExpression.push(operators.pop().toString())
                    }
                    operators.push(operator)
                    expectOperator = false
                } else {
                    if (operator == '(')
                        operators.push(operator)
                    if (operator == ')') {
                        while (operators.peek()!! != '(') {
                            mRpnExpression.push(operators.pop().toString())
                        }
                        operators.pop()
                    }
                }
            }
            count++
        }
        while (operators.size() > 0) {
            mRpnExpression.push(operators.pop().toString())
        }

        return mRpnExpression.size() != null
    }


    fun calculate(): String {
        var numbers = Stack<String>()
        var operator: String
        var firstNumber: String
        var secondNumber: String
        
        for(index in 0 until mRpnExpression.size()) {
            try {
                operator = mRpnExpression[index]
                if (isOperator(operator.first()) && operator.length == 1) {
                    firstNumber = numbers.pop()!!
                    secondNumber = numbers.pop()!!
                    numbers.push(performOperation(operator, firstNumber, secondNumber))
                } else {
                    numbers.push(operator)
                }
            }
            catch(pExc: Exception) {
                throw pExc
            }
        }
        if(numbers.size()==1)
        {
            return numbers.first().toString()
        }
        else
        {
            throw Exception("Bad syntax")
        }
    }

    private fun performOperation(operator: String, firstNumber: String, secondNumber: String): String {
        var result = 0.0
        when(operator)
        {
            "+" -> {
                result = sum(java.lang.Double.parseDouble(secondNumber), java.lang.Double.parseDouble(firstNumber))
                return result.toString()
            }
            "-" -> {
                result = substract(java.lang.Double.parseDouble(secondNumber), java.lang.Double.parseDouble(firstNumber))
                return result.toString()
            }
            "*" -> {
                result = multiply(java.lang.Double.parseDouble(secondNumber), java.lang.Double.parseDouble(firstNumber))
                return result.toString()
            }
            "/" -> {
                result = divide(java.lang.Double.parseDouble(secondNumber), java.lang.Double.parseDouble(firstNumber))
                return result.toString()
            }
            "^" -> {
                result = power(java.lang.Double.parseDouble(secondNumber), java.lang.Double.parseDouble(firstNumber))
                return result.toString()
            }
            else -> throw Exception("Invalid operator")
        }
        return result.toString()
    }

    private fun <T : Number> sum(num1: T, num2: T): Double {
        return num1.toDouble() + num2.toDouble()
    }

    private fun <T : Number> substract(num1: T, num2: T): Double {
        return num1.toDouble() - num2.toDouble()
    }

    private fun <T : Number> multiply(num1: T, num2: T): Double {
        return num1.toDouble() * num2.toDouble()
    }

    private fun <T : Number> divide(num1: T, num2: T): Double {
        return num1.toDouble() / num2.toDouble()
    }

    private fun <T : Number> power(num: T, power: T): Double {
        return Math.pow(num.toDouble(), power.toDouble())
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