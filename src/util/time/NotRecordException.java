package util.time;

class NotRecordException extends TimeControlException {
    NotRecordException() {
        super("���R�[�h��������܂���ł����B");
    }

    NotRecordException(String text) {
        super(text);
    }
}
