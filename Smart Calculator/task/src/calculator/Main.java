package calculator;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        boolean exit;
        Calculator c = new Calculator();
        do {
            String in = scanner.nextLine();
            exit = c.calc2(in);

        } while (!exit);

    }
}
