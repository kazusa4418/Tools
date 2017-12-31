package util.manager;

class NotRecordException extends TimeManagementException {
    NotRecordException() {
        super("レコードが見つかりませんでした。");
    }

    NotRecordException(String text) {
        super(text);
    }
}
