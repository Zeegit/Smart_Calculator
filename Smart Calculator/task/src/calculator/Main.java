package calculator;

import java.math.BigInteger;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        boolean exit = false;
        Calculator c = new Calculator();
        do {
            String in = scanner.nextLine();
            exit = c.calc2(in);

        } while (!exit);

                /*
        while (true) {
            String in = scanner.nextLine();

            if ("/exit".equals(in)) {
                break;
            } else if ("/help".equals(in)) {
                System.out.println("The program calculates the sum of numbers");
            } else if (in.startsWith("/")) {
                System.out.println("Unknown command");
            } else if (!in.isEmpty()) {

                if (c.isValid(in)) {
                    c.calc(in);

                }
                else {
                    System.out.println("Invalid expression");
                }
            }
        }
        System.out.println("Bye!");
    } */
    }
}
