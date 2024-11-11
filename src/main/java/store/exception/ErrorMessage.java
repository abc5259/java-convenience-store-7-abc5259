package store.exception;

public enum ErrorMessage {
    INVALID_RESOURCE_FORMAT("잘못된 리소스 포맷입니다."),
    INVALID_FORMAT_MESSAGE("올바르지 않은 형식으로 입력했습니다."),
    INVALID_INPUT_MESSAGE("잘못된 입력입니다.");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
