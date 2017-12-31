package util.manager;

class StreamNotOpenException extends RuntimeException {
    StreamNotOpenException() {
        super("Stream is not open.");
    }

    @SuppressWarnings("unused")
    StreamNotOpenException(String msg) {
        super(msg);
    }
}
