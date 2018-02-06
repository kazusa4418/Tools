package util.test;

import util.input.InputScanner;

@SuppressWarnings("all")
public class TestClass {

    public static void main(String[] args) {
        InputScanner is = new InputScanner();

        int i = is.checkedInputInt("input > ",
                                  "error: try again.",
                                          x -> x > 100);
        System.out.println(i);

    }


}
