package store.exception;

public class NonExistProductException extends IllegalArgumentException {

    public NonExistProductException() {
        super("존재하지 않는 상품입니다.");
    }

    public NonExistProductException(String s) {
        super(s);
    }
}
