package store.domain;

import java.time.LocalDate;

public class Promotion {

    private String name;
    private int buyCount;
    private int getCount;
    private LocalDate startTime;
    private LocalDate endTime;

    public boolean isApplicable(LocalDate currentDate, int count) {
        if (currentDate.isBefore(startTime) || currentDate.isAfter(endTime)) {
            return false;
        }

        return true;
    }
}
