package store.domain;

public class OrderItem {

    private final String name;
    private int count;

    public OrderItem(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void validateCount(int totalQuantity) {
        if (count > totalQuantity) {
            throw new IllegalArgumentException("재고 수량을 초과하여 구매할 수 없습니다.");
        }
    }


    public int getCount() {
        return count;
    }

    public void decreaseCount(int quantity) {
        this.count = Math.max(0, count - quantity);
    }
}
