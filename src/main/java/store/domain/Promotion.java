package store.domain;

import java.time.LocalDate;

public class Promotion {

    private String name;
    private int buyCount;
    private int getCount;
    private LocalDate startTime;
    private LocalDate endTime;

    public Promotion(String name, int buyCount, int getCount, LocalDate startTime, LocalDate endTime) {
        this.name = name;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean isApplicable(LocalDate currentDate, int count) {
        if (currentDate.isBefore(startTime) || currentDate.isAfter(endTime)) {
            return false;
        }

        return true;
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
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
