package calculator;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        String in;
        while (true) {
            in = scanner.nextLine()
                    .replaceAll("\\s+", "")
                    .replaceAll("-{2}", "+")
                    //.replaceAll("--", "+")
                    .replaceAll("\\+{1,}", "+")
                    // .replaceAll("\\+-", "-")
                    .replaceAll("-", "+-");
            if ("/exit".equals(in)) {
                break;
            } else if ("/help".equals(in)) {
                System.out.println("The program calculates the sum of numbers");
            } else if (!in.isEmpty()) {
                int sum = 0;
                for(String s: in.split("\\+")) {
                    if (!s.isEmpty()) {
                        sum += Integer.parseInt(s);
                    }
                }
                System.out.println(sum);

            }
        }
        System.out.println("Bye!");
    }
}
