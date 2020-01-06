package calculator;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class NotationConverter {
    ArrayList<String> convert(String[] strs) {
        /*

1. Добавьте операнды (числа и переменные) к результату (постфиксная запись) по мере их поступления.

2. Если стек пуст или содержит левую скобку сверху, поместите входящий оператор в стек.

3. Если входящий оператор имеет более высокий приоритет, чем вершина стека, поместите его в стек.

4. Если входящий оператор имеет более низкий или равный приоритет, чем или к вершине стека,
вытолкните стек и добавляйте операторы к результату, пока не увидите оператор с меньшим приоритетом
или левую скобку на вершине стека; затем добавьте входящий оператор в стек.

5. Если входящий элемент является левой скобкой, поместите его в стек

6.Если входящий элемент является правой круглой скобкой,
 стек и добавьте операторы к результату, пока не увидите левую круглую скобку.
 Откажитесь от пары скобок.

7.В конце выражения вытолкните стек и добавьте все операторы к результату.
*/
        Deque<String> stack = new ArrayDeque<>();
        ArrayList<String> result = new ArrayList<>();

        for (String s0 : strs) {
            String s = s0.replaceAll("\\s+", "").trim();
            // number or identificator
            if (s.matches("[A-Za-z0-9]+")) {
                // 1
                result.add(s);
            } else if (s.equals("(")) {
                // 2
                stack.push(s);
            } else if (s.equals(")")) {
                // 6
                while (!stack.peek().equals("(")) {
                    result.add(stack.pop());
                    if (stack.isEmpty()) {

                        return null;
                    }
                }
                stack.pop();
            } else if (isOperator(s)) {
                // 3, 4
                while (!stack.isEmpty() && priority(stack.peek()) >= priority(s)) {
                    result.add(stack.pop());
                }
                stack.push(s);
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    boolean isOperator(String operator) {
        return operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/") || operator.equals("^");
    }

    int priority(String operator) {
        switch (operator) {
            case "^":
                return 3;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
        }
        return 0;
    }
}
