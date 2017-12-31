package util.manager;

class TimeManagementException extends RuntimeException {
    TimeManagementException() {
        super("Failed time management");
    }

    TimeManagementException(String text) {
        super(text);
    }
}
