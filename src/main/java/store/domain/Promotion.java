package store.domain;

import java.time.LocalDate;

public class Promotion {

    private String name;
    private int buyCount;
    private int getCount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(String name, int buyCount, int getCount, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isApplicable(LocalDate currentDate) {
        return isInDate(currentDate);
    }

    private boolean isInDate(LocalDate targetDate) {
        return !targetDate.isBefore(startDate) && !targetDate.isAfter(endDate);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "name='" + name + '\'' +
                ", buyCount=" + buyCount +
                ", getCount=" + getCount +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
