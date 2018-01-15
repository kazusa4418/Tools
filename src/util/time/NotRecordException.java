package util.time;

class NotRecordException extends TimeControlException {
    NotRecordException() {
        super("レコードが見つかりませんでした。");
    }

    NotRecordException(String text) {
        super(text);
    }
}
