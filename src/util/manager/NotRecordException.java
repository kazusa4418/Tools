package util.manager;

class NotRecordException extends TimeManagementException {
    NotRecordException() {
        super("���R�[�h��������܂���ł����B");
    }

    NotRecordException(String text) {
        super(text);
    }
}
