package util.manager;

public class NullRecordException extends TimeManagementException {
    NullRecordException() {
        super("Record does not exist");
    }

    NullRecordException(String text) {
        super(text);
    }
}
