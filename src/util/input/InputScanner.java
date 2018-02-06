package util.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputScanner {
    private BufferedReader reader = null;
    private String errMsg = "please try again.";

    private static final byte[] intMaxValue = { 2, 1, 4, 7, 4, 8, 3, 6, 4, 7 };
    private static final byte[] intMinValue = { 2, 1, 4, 7, 4, 8, 3, 6, 4, 8 };

    /* -------------------- コンストラクター -------------------- */
    public InputScanner() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public InputScanner(Reader reader) {
        reader = new BufferedReader(reader);
    }

    /* --------------------------------------------------------- */

    /* -------------------- STRING INPUT -------------------- */
    public String input() {
        return input("");
    }

    public String input(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return reader.readLine();
            }
            catch (IOException e) {
                System.err.println("IO error : " + errMsg);
            }
        }
    }

    public String checkedInput(Pattern pattern) {
        return checkedInput("", pattern);
    }

    public String checkedInput(String inMsg, String pattern) {
        Pattern p = Pattern.compile(pattern);
        return checkedInput(inMsg, p);

    }

    public String checkedInput(String inMsg, Pattern p) {
        String s = input(inMsg);
        Matcher m = p.matcher(s);

        if (m.matches()) {
            return s;
        }
        else {
            return "";
        }
    }

    public String checkedInputWithLoop(String inMsg, String errMsg, String pattern) {
        Pattern p = Pattern.compile(pattern);
        return checkedInputWithLoop(inMsg, errMsg, p);
    }

    public String checkedInputWithLoop(String inMsg, String errMsg, Pattern p) {
        while (true) {
            String s = checkedInput(inMsg, p);

            if (!s.isEmpty()) {
                return s;
            }
            else {
                System.err.println(errMsg);
            }
        }
    }
    /* ------------------------------------------------------ */

    /* -------------------- INTEGER INPUT -------------------- */
    public int inputInt() {
        return inputInt("");
    }

    public int inputInt(String inMsg) {
        String s = input(inMsg);
        if (s.matches("[0-9]+|-[0-9]+") && checkInIntRange(s)) {
            return Integer.parseInt(s);
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    private boolean checkInIntRange(String s) {
        if (s.matches("[0-9]+")) {
            if (s.length() > 10) {
                return false;
            }
            else if (s.length() < 10) {
                return true;
            }
            else {
                String[] ss = s.split("");
                for (int i = 0; i < intMaxValue.length; i++ ) {
                    if (Byte.parseByte(ss[i]) > intMaxValue[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        else {
            if (s.length() > 11) {
                return false;
            }
            else if (s.length() < 11) {
                return true;
            }
            else {
                s = s.replace("-", "");
                String[] ss = s.split("");
                for (int i = 0; i < intMinValue.length; i++ ) {
                    if (Byte.parseByte(ss[i]) > intMinValue[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    public int checkedInputInt(String inMsg, String errMsg, Predicate<Integer>... pred) {
        boolean flag = true;

        while (true) {
            try {
                int i = inputInt(inMsg);

                for (Predicate<Integer> pre : pred) {
                    if (!pre.test(i)) {
                        flag = false;
                    }
                }
                if (flag) {
                    return i;
                }
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }
    /* ------------------------------------------------------- */
}
