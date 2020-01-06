package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class Calculator {
    HashMap<String, String> map;

    public Calculator() {

        map = new HashMap<>();
    }

    public String remove(String text) {
        return text
                // replaceAll("\\s+", "")
                .replaceAll("\\+{1,}", "+")
                .replaceAll("-{2}", "+")
                .replaceAll("(\\+-)", "-")
                .replaceAll("(-\\+)", "-");
    }

    public int calc(String calcText) {
        calcText = remove(calcText);
        int sum = 0;
        String operation = "+";
        String[] elements = calcText.split("\\s+");
        String var = "";
        boolean isShow = false;

        for (String s : elements) {
            if (s.matches("[a-zA-Z]+")) {
                var = s;
                String val = map.get(var);
                if (val != null) {
                    sum += Integer.parseInt(val);
                    isShow = true;
                } else {
                    System.out.println("Invalid identifier");
                }
            } else if ("-".equals(s) || "+".equals(s) || "=".equals(s)) {
                operation = s;
            } else {
                // Ели переменная
                if (s.matches("[a-zA-Z]+")) {
                    s = map.get(s);
                }

                if ("+".equals(operation)) {
                    sum += Integer.parseInt(s);
                    isShow = true;
                } else if ("-".equals(operation)) {
                    sum -= Integer.parseInt(s);
                    isShow = true;
                } else if ("=".equals(operation)) {
                    map.put(var, s);
                }
            }
        }
        if (isShow) {
            System.out.println(sum);
        }
        return sum;
    }

    boolean calc2(String str) {
        int sum = 0;
        String[] strings;

        boolean exit = false;

        if (str.replaceAll("\\s+", "").matches("[A-Za-z]+[A-Za-z0-9]?+\\=[A-Za-z0-9]+")) {

            strings = str.replaceAll("\\s+", "").split("=");

            if (strings[0].matches(".*[0-9]")) {
                System.out.println("Invalid identifier");
            } else if (strings[1].matches("\\D") && map.get(strings[1]) == null) {
                System.out.println("Unknown variable");
            } else if (strings[1].matches("[A-Za-z]+") && map.get(strings[1]) != null) {
                map.put(strings[0], map.get(strings[1]));
            } else if (strings[1].matches("\\d+")) {
                map.put(strings[0], strings[1]);
            } else {
                System.out.println("Invalid assignment");
            }
        } else if (str.replaceAll("\\s+", "").matches("[A-Za-z0-9]+")) {
            if (map.get(str) != null) {
                System.out.println(map.get(str));
            } else {
                System.out.println("Unknown variable");
            }
        } else if (str.replaceAll("\\s+", "").matches("[A-Za-z0-9()]+[-+*\\/^]+[A-Za-z0-9()]+.*")) {
            // "[A-Za-z0-9]+[-+][A-Za-z0-9]+.*"
            strings = str.
                    replaceAll("\\s+", "").
                    replaceAll("\\-{2}", "+").
                    replaceAll("\\+{1,}", "+").

                    replaceAll("-", " - ").
                    replaceAll("\\+", " + ").
                    replaceAll("\\*", " * ").
                    replaceAll("\\/", " / ").
                    replaceAll("\\^", " ^ ").

                    replaceAll("\\(", " ( ").
                    replaceAll("\\)", " ) ").
                    split("\\s+");
            /*strings = str.replaceAll("\\s+", "").
                    replaceAll("\\-{2}", "+").
                    replaceAll("\\-", "+-").
                    replaceAll("\\+{1,}", "+").
                    replaceAll("\\*", "+*").
                    replaceAll("\\/", "+/").
                    split("\\+");*/

            NotationConverter nc = new NotationConverter();
            ArrayList<String> list = nc.convert(strings);

            if (list == null || list.contains("(") || list.contains(")")) {
                System.out.println("Invalid expression");
                return exit;
            }
            //System.out.println(list.toString());
            // new String[]{"2", "3", "4", "+", "*", "1", "+"};
            Deque<String> stack = new ArrayDeque<>();
            /*
            Если входящий элемент является числом, поместите его в стек (целое число, а не одну цифру!).

            Если входящий элемент является именем переменной, поместите его значение в стек.

            Если входящий элемент является оператором, то дважды нажмите, чтобы получить два числа
            и выполнить операцию; поместите результат в стек.

            Когда выражение заканчивается, число на вершине стека является окончательным результатом.
             */
            BigInteger op1;
            BigInteger op2;
            for (String s : list) {
                if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/") || s.equals("^")) {
                    if (!stack.isEmpty()) {
                        op1 = new BigInteger(stack.pop());
                    } else {
                        System.out.println("Invalid expression");
                        return exit;
                    }
                    if (!stack.isEmpty()) {
                        op2 = new BigInteger(stack.pop());

                    } else {
                        System.out.println("Invalid expression");
                        return exit;
                    }
                    stack.push(doOpertion(s, op1, op2).toString());
                } else if (map.get(s.replaceAll("-", "")) != null) {
                    if (s.matches("\\-.*")) {
                        stack.push("-" + map.get(s.replaceAll("-", "")));
                    } else {
                        stack.push(map.get(s.replaceAll("-", "")));
                    }
                } else {
                    stack.push(s);
                }
            }

            System.out.println(stack.pop());
            sum = 0;
        } else if (str.equals("/exit")) {
            System.out.println("Bye!");
            exit = true;
        } else if (str.equals("/help")) {
            System.out.println("The program must calculate expressions like these: 4 + 6 - 8, 2 - 3 - 4, and so on. It must support both unary and binary minus operators. If the user has entered several operators following each other, the program still should work (like Java or Python REPL). Interpret two adjacent minus signs as a plus.");
        } else if (str.matches("\\/.*") && !str.equals("/help") && !str.equals("/exit")) {
            System.out.println("Unknown command");
        } else if (str.replaceAll("\\s+", "").matches(".*\\=.*\\=.")) {
            System.out.println("Invalid assignment");
        }
        return exit;
    }

    private BigInteger doOpertion(String s, BigInteger op1, BigInteger op2) {
        switch (s) {
            case "+":
                return op1.add(op2);
            case "-":
                return op2.subtract(op1);
            case "*":
                return op1.multiply(op2);
            case "/":
                return op2.divide(op1);
            case "^":
                return op2.pow(op1.intValue());
            default:
                return BigInteger.ZERO;
        }
    }
}


