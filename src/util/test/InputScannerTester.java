package util.test;

import util.input.InputScanner;

public class InputScannerTester {
    public static void main(String[] args) {
        InputScanner is = new InputScanner();
        writeLine("input()test");
        String s = is.input(); is.input();
        result(s);

        writeLine("inputLine()test");
        String s1 = is.input();
        result(s1);

        writeLine("inputLine(String msg)test");
        String s2 = is.input("input > ");
        result(s2);

        writeLine("inputLine(String msg, String pattern)test");
        String s3 = is.checkedInput("input > ", "[0-9]+|kazusa");
        result(s3);

        writeLine("inputLineWithCheck(String msg, String pattern)test");
        String s4 = is.checkedInputWithLoop("input > ", "error try again.", "[0-9]+");
        result(s4);

    }

    private static void writeLine(String msg) {
        System.out.println("==========" + msg + "==========");
    }

    private static void result(String msg) {
        System.out.println("結果は" + msg + "でした");
    }
}
