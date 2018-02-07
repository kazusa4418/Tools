package util.test;

import util.input.InputScanner;

import java.util.regex.Pattern;

public class InputScannerTester {
    public static void main(String[] args) {
        InputScanner is = new InputScanner();
        is.setIOErrMsg("IOError: error");

        System.out.println("---- STRING INPUT TEST ----");
        writeLine("readStr()test");
        String s = is.readStr();
        result(s);

        writeLine("readStr(String inMsg)test");
        String s1 = is.readStr("readStr > ");
        result(s1);

        writeLine("readCheckedStr(String inMsg, Pattern... p");
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

        writeLine("readCheckedInt(String msg, Predicate<Integer> pred)test");
        int i4 = is.readCheckedInt("input int > ", x -> x <= 100, x -> x >= 0);
        result(i4);

        writeLine("readCheckedInt(String inMsg, String errMsg, Predicate<Integer> pred)test");
        int i5 = is.readCheckedInt("input int > ", "error try again.", x -> x <= 100, x -> x >= 0);
        result(i5);




    }

    private static void writeLine(String msg) {
        System.out.println("==========" + msg + "==========");
    }

    private static void result(Object msg) {
        System.out.println("結果は" + msg + "でした");
    }
}
