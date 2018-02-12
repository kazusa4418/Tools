package util.test;

import util.input.InputScanner;

import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("all")
public class TestClass {

    public static void main(String[] args) {
        SubPredicate sp = new SubPredicate();

        InputScanner is = new InputScanner();
        char c = is.readCheckedChar("input > ", sp);
        System.out.println(c);
    }
}

class SubPredicate implements Predicate<Character> {
    public boolean test(Character c) {
        if (c == 'a') {
            return true;
        }
        else {
            return false;
        }
    }
}
