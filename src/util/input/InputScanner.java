package util.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class InputScanner {
    private BufferedReader reader;
    private String IOErrMsg = "IOError : try again.";

    private static final byte[] intMaxValue = { 2, 1, 4, 7, 4, 8, 3, 6, 4, 7 };
    private static final byte[] intMinValue = { 2, 1, 4, 7, 4, 8, 3, 6, 4, 8 };

    /* -------------------- コンストラクター -------------------- */
    public InputScanner() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public InputScanner(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    /* --------------------------------------------------------- */

    /* -------------------- STRING INPUT -------------------- */
    public String readStr() {
        return readStr("");
    }

    public String readStr(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return reader.readLine();
            }
            catch (IOException e) {
                System.err.println(IOErrMsg);
            }
        }
    }

    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readCheckedStr(String inMsg, String... patterns) {
        Stream<String> stream = Arrays.stream(patterns);
        Pattern[] p = stream.map(Pattern::compile).toArray(Pattern[]::new);

        return readCheckedStr(inMsg, p);

    }

    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readCheckedStr(String inMsg, Pattern... patterns) {
        String s = readStr(inMsg);

        boolean flag = checkPatternMatch(s, patterns);

        if (flag) {
            return s;
        }
        else {
            return "";
        }
    }

    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readStrUntilMatch(String inMsg, String errMsg, String... patterns) {
        Stream<String> stream = Arrays.stream(patterns);
        Pattern[] p = stream.map(Pattern::compile).toArray(Pattern[]::new);

        return readStrUntilMatch(inMsg, errMsg, p);
    }

    //TODO:可変長引数になっているせいでpatternsが指定されなかったときに競合する問題を修正する
    public String readStrUntilMatch(String inMsg, String errMsg, Pattern... patterns) {
        while (true) {
            String s = readStr(inMsg);

            boolean flag = checkPatternMatch(s, patterns);

            if (flag) {
                return s;
            }
            else {
                System.err.println(errMsg);
            }
        }
    }

    private boolean checkPatternMatch(String s, Pattern[] patterns) {
        for (Pattern p : patterns) {
            Matcher m = p.matcher(s);
            if (!m.matches()) {
                return false;
            }
        }
        return true;
    }
    /* ------------------------------------------------------ */

    /* -------------------- INTEGER INPUT -------------------- */
    public int readInt() {
        return readInt("");
    }

    public int readInt(String inMsg) {
        String s = readStr(inMsg);
        if (s.matches("[0-9]+|-[0-9]+") && checkInIntRange(s)) {
            return Integer.parseInt(s);
        }
        else {
            throw new IllegalDataFormatException();
        }
    }

    public int readInt(String inMsg, String errMsg) {
        while (true) {
            try {
                return readInt(inMsg);
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
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

    @SafeVarargs
    public final int readCheckedInt(String inMsg, Predicate<Integer>... pred) {
        boolean flag = true;

        int i = readInt(inMsg);

        for (Predicate<Integer> pre : pred) {
            if (!pre.test(i)) {
                flag = false;
            }
        }
        if (flag) {
            return i;
        }
        else {
            throw new IllegalDataFormatException();
        }
    }


    @SafeVarargs
    public final int readCheckedInt(String inMsg, String errMsg, Predicate<Integer>... pred) {
        boolean flag = true;

        while (true) {
            try {
                int i = readInt(inMsg);

                for (Predicate<Integer> pre : pred) {
                    if (!pre.test(i)) {
                        flag = false;
                    }
                }
                if (flag) {
                    return i;
                }
                else {
                    System.err.println(errMsg);
                }
            }
            catch (IllegalDataFormatException e) {
                System.err.println(errMsg);
            }
        }
    }
    /* ------------------------------------------------------- */

    public void setIOErrMsg(String msg) {
        IOErrMsg = msg;
    }
}
