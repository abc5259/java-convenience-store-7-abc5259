package store.exception;

public class NotEnoughQuantityException extends IllegalArgumentException {
    
    public NotEnoughQuantityException() {
        super("재고 수량을 초과하여 구매할 수 없습니다.");
    }

    public NotEnoughQuantityException(String s) {
        super(s);
    }
}
