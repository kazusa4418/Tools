package util.time;

class TimeControlException extends RuntimeException {
    TimeControlException() {
        super("Failed time management");
    }

    TimeControlException(String text) {
        super(text);
    }
}
