package util.test;

import util.input.InputScanner;

import java.util.regex.Pattern;

/* tester : InputScanner */

public class InputScannerTester {
    private static final InputScanner is = new InputScanner();

    public static void main(String[] args) {
        is.setIOErrMsg("IOError: error");
        booleanInputTest();
    }

    private static void stringInputTest() {
        System.out.println("---- STRING INPUT TEST ----");

        writeLine("readStr()test");
        String s = is.readStr();
        result(s);

        writeLine("readStr(String inMsg)test");
        String s1 = is.readStr("readStr > ");
        result(s1);

        writeLine("readCheckedStr(String inMsg, Pattern... p)");
        String s2 = is.readCheckedStr("readStr > ", Pattern.compile("[0-9]+"));
        result(s2);

        writeLine("readCheckedStr(String msg, String... pattern)test");
        String s3 = is.readCheckedStr("readStr > ", "[0-9]+|kazusa");
        result(s3);

        writeLine("readCheckedStr(String msg, String errMsg, Pattern... p)test");
        String s4 = is.readStrUntilMatch("readStr > ", "error try again.", Pattern.compile("[0-9]+"), Pattern.compile("kazusa"));
        result(s4);

        writeLine("readCheckedStr(String msg, String errMsg, String... pattern)test");
        String s5 = is.readStrUntilMatch("readStr > ", "error try again.", "[0-9]+", "kazusa");
        result(s5);
    }

    private static void intInputTest() {
        System.out.println("---- INTEGER INPUT TEST ----");

        writeLine("readInt()test");
        int i1 = is.readInt();
        result(i1);

        writeLine("readInt(String msg)test");
        int i2 = is.readInt("input int > ");
        result(i2);

        writeLine("readInt(String inMsg, String errMsg)test");
        int i3 = is.readInt("input int > ", "error try again.");
        result(i3);

        writeLine("readIntUntilMatch(String msg, Predicate<Integer> pred)test");
        int i4 = is.readCheckedInt("input int > ", x -> x <= 100, x -> x >= 0);
        result(i4);

        writeLine("readIntUntilMatch(String inMsg, String errMsg, Predicate<Integer> pred)test");
        int i5 = is.readIntUntilMatch("input int > ", "error try again.", x -> x <= 100 && x >= 0);
        result(i5);
    }

    private static void longInputTest() {
        System.out.println("---- LONG INPUT TEST ----");

        writeLine("readLong()test");
        long l1 = is.readLong();
        result(l1);

        writeLine("readLong(String)test");
        long l2 = is.readLong("input long > ");
        result(l2);

        writeLine("readLong(String, String)test");
        long l3 = is.readLong("input long > ", "error, try again.");
        result(l3);

        writeLine("readCheckedLong(String, Predicate<Long>)test");
        long l4 = is.readCheckedLong("input long > ", x -> x <= 100 && x >= 0);
        result(l4);

        writeLine("readLongUntilMatch(String, String, Predicate<Long>)test");
        long l5 = is.readLongUntilMatch("input long > ", "error, try again.", x -> x <= 100 && x >= 0);
        result(l5);
    }

    private static void charInputTest() {
        System.out.println("---- CHARACTER INPUT TEST ----");

        writeLine("readChar()test");
        char c1 = is.readChar();
        result(c1);

        writeLine("readChar(String)test");
        char c2 = is.readChar("input char > ");
        result(c2);

        writeLine("readChar(String, String)test");
        char c3 = is.readChar("input char > ", "error, try again.");
        result(c3);

        writeLine("readCheckedChar(String, Predicate<Character>)test");
        char c4 = is.readCheckedChar("input char > ", c -> c == 'a');
        result(c4);

        writeLine("readCharUntilMatch(String, String, Predicate<Character>)test");
        char c5 = is.readCharUntilMatch("input char > ", "error, try again.",
                                              c -> c == 'a' || c == 'A');
        result(c5);
    }

    private static void doubleInputTest() {
        System.out.println("---- DOUBLE INPUT TEST ----");

        writeLine("readDouble()test");
        double d1 = is.readDouble();
        result(d1);

        writeLine("readDouble(String)test");
        double d2 = is.readDouble("input double > ");
        result(d2);

        writeLine("readDouble(String, String)test");
        double d3 = is.readDouble("input double > ", "error, try again.");
        result(d3);

        writeLine("readCheckedDouble(String, Predicate<Double>)test");
        double d4 = is.readCheckedDouble("input double > ", x -> x >= 3.14);
        result(d4);

        writeLine("readDoubleUntilMatch(String, String, Predicate<Double>)test");
        double d5 = is.readDoubleUntilMatch("input double > ", "error, try again.",
                                                  x -> x >= 3.14);
        result(d5);
    }

    private static void booleanInputTest() {
        System.out.println("---- BOOLEAN INPUT TEST ----");

        writeLine("readBoolean()test");
        boolean b1 = is.readBoolean();
        result(b1);

        writeLine("readBoolean(String)test");
        boolean b2 = is.readBoolean("input boolean > ");
        result(b2);

        writeLine("readBoolean(String, String)test");
        boolean b3 = is.readBoolean("input boolean > ", "error, try again.");
        result(b3);

        writeLine("readCheckedBoolean(String, String)test");
        boolean b4 = is.readCheckedBoolean("yes", "no");
        result(b4);

        writeLine("readCheckedBoolean(Pattern, Pattern)test");
        boolean b5 = is.readCheckedBoolean(Pattern.compile("yes"), Pattern.compile("no"));
        result(b5);

        writeLine("readCheckedBoolean(String, String, boolean)test");
        boolean b6 = is.readCheckedBoolean("yes", "no", true);
        result(b6);

        writeLine("ask(String, String, String)test");
        boolean b7 = is.ask("Do you want to overwrite? > ", "yes", "no");
        result(b7);

        writeLine("ask(String, String, String, boolean)test");
        boolean b8 = is.ask("Do you want to overwrite? > ", "yes", "no", true);
        result(b8);

        writeLine("ask(String, Pattern, Pattern)test");
        boolean b9 = is.ask("Do you want to overwrite? > ", Pattern.compile("yes"), Pattern.compile("no"));
        result(b9);

        writeLine("ask(String, String, String, String)test");
        boolean b10 = is.ask("Do you want to overwrite? > ", "error, try again.", "yes", "no");
        result(b10);

        writeLine("ask(String, String, String, String, boolean)test");
        boolean b11 = is.ask("Do you want to overwrite? > ", "error, try again.", "yes", "no", true);
        result(b11);

        writeLine("ask(String, String, Pattern, Pattern)test");
        boolean b12 = is.ask("Do you want to overwrite? > ", "error, try again.",
                                    Pattern.compile("yes"), Pattern.compile("no"));
        result(b12);
    }

    private static void writeLine(String msg) {
        System.out.println("==========" + msg + "==========");
    }

    private static void result(Object msg) {
        System.out.println("結果は" + msg + "でした");
    }
}
