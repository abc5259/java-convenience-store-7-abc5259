package store.domain;

public enum Answer {
    YES, NO;

    public boolean toBoolean() {
        return this == YES;
    }
}
