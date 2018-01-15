package util.input;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class InputScanner {
    private final Scanner sc = new Scanner(System.in);
    private String inMsg = "input > ";

    public String input() {
        return input(inMsg);
    }

    public String input(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    public String checkedInput(Pattern... patterns) {
        return checkedInput(inMsg, patterns);
    }

    public String checkedInput(String inMsg, Pattern... patterns) {
        String s = input(inMsg);
        for (Pattern p : patterns) {
            Matcher m = p.matcher(s);
            if (m.matches())
                return s;
        }
        return "";
    }

    public void setInMsg(String msg) {
        inMsg = msg;
    }

    public void setErrMsg(String msg) {
        errMsg = msg;
    }

    public void setMsg(String inMsg, String errMsg) {
        this.inMsg = inMsg;
        this.errMsg = errMsg;
    }
}
