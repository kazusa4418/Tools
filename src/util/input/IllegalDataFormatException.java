package util.input;

public class IllegalDataFormatException extends RuntimeException  {
    public IllegalDataFormatException() {
        super("Illegal format was specified");
    }

    public IllegalDataFormatException(String errMsg) {
        super(errMsg);
    }
}
