package util.time;

public class NullRecordException extends TimeControlException {
    NullRecordException() {
        super("Record does not exist");
    }

    NullRecordException(String text) {
        super(text);
    }
}
