package util.test;

import util.input.InputScanner;

public class InputScannerTester {
    public static void main(String[] args) {
        InputScanner is = new InputScanner();
        writeLine("input()test");
        String s = is.input(); is.inputLine();
        result(s);

        writeLine("inputLine()test");
        String s1 = is.inputLine();
        result(s1);

        writeLine("inputLine(String msg)test");
        String s2 = is.inputLine(false);
        result(s2);

        writeLine("inputLine(String msg, String pattern)test");
        String s3 = is.inputLine("STRING > ", "[0-9]+");
        result(s3);

        writeLine("inputLineWithCheck(String msg, String pattern)test");
        String s4 = is.inputLineWithCheck("STRING > ", "[0-9]+", "[a]+");
        result(s4);

    }

    private static void writeLine(String msg) {
        System.out.println("==========" + msg + "==========");
    }

    private static void result(String msg) {
        System.out.println("結果は" + msg + "でした");
    }
}
